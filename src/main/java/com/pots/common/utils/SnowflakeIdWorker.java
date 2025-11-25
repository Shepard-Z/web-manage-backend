package com.pots.common.utils;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@ConfigurationProperties(prefix = "snowflake")
@Data
@Slf4j
public final class SnowflakeIdWorker {
    
    // 基本パラメータ
    private long epoch; // カスタム紀元（2025年4月3日 00:00:00 ms）
    private long workerIdBits;// ワーカIDのビット数（最大8台サーバ）
    private long datacenterIdBits;// データセンターIDのビット数(最大4個データセンター)
    private long sequenceBits;// シーケンス番号のビット数（1msあたり16384 ID生成可能）
    private long sequenceMask;// シーケンスのマスク
    
    private       long       workerId; // ワーカID
    private       long       datacenterId; // データセンターID
    private final AtomicLong sequence      = new AtomicLong(0); // シーケンス番号（スレッドセーフ）
    private final AtomicLong lastTimestamp = new AtomicLong(-1L); // 最後のタイムスタンプ（スレッドセーフ）
    
    // Bean生成する時の検証（Beanの初期化後に自動的にメソッドを実行させる）
    @PostConstruct
    public void init() {
        sequenceMask = ~(-1L << sequenceBits);
        
        long maxWorkerId     = ~(-1L << workerIdBits);
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            log.error("Invalid workerId: {}. Must be 0 ~ {}", workerId, maxWorkerId);
            throw new IllegalArgumentException(String.format("Worker ID must be between 0 and %d",maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            log.error("Invalid datacenterId: {}. Must be 0 ~ {}", datacenterId, maxDatacenterId);
            throw new IllegalArgumentException(String.format("Datacenter ID must be between 0 and %d",maxDatacenterId));
        }
        log.info("----- SnowflakeIdWorker init success ! workerId={}, datacenterId={} ----- ", workerId, datacenterId);
        
    }
    
    // IDの生成
    public long nextId() {
        long nowTs = System.currentTimeMillis();
        
        // 現在のタイムスタンプが前回の生成時刻より小さい場合、システムクロックが戻ったことを意味し、例外をスロー
        long lastTs = lastTimestamp.get();
        if (nowTs < lastTs) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        
        if (nowTs == lastTs) {
            // 同一ミリ秒内であれば、シーケンス番号をインクリメント（Atomic 操作）
            long seq = (sequence.incrementAndGet()) & sequenceMask;
            if (seq == 0) {
                // シーケンス番号が溢れた場合、次のミリ秒まで待機
                nowTs = waitNextMillis(lastTs);
            }
        } else {
            // 異なるミリ秒の場合、シーケンス番号をリセット
            sequence.set(0);
        }
        
        // 最新のタイムスタンプを更新（CASで競合回避）
        lastTimestamp.set(nowTs);
        
        // 順番に左ビットシフト、IDを構成
        return ((nowTs - epoch) << (workerIdBits + datacenterIdBits + sequenceBits)) // タイムスタンプ部分
                | (datacenterId << (workerIdBits + sequenceBits)) // データセンター部分
                | (workerId << sequenceBits) // ワーカID部分
                | sequence.get(); // シーケンス番号部分
    }
    
    // 次のミリ秒まで待機
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
    
}
