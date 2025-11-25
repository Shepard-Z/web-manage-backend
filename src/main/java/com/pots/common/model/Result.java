package com.pots.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;

@Data
@AllArgsConstructor
@ConditionalOnEnabledResourceChain
public final class Result<T> {
    
    private int    status;
    private String message;
    private T      data;
    
    // オリジナル成功結果
    public static <T> Result<T> success(ResultType resultType,T data) {
        return new Result<>(resultType.status,resultType.message,data);
    }
    
    // デフォルト成功結果
    public static <T> Result<T> success(T data) {
        return Result.success(ResultType.SUCCESS,data);
    }
    public static <T> Result<T> success() {
        return Result.success(ResultType.SUCCESS,null);
    }
    // オリジナル失敗結果
    public static <T> Result<T> failed(ResultType resultType) {
        return new Result<>(resultType.status,resultType.message,null);
    }
    
    // オリジナルメッセージ付きの失敗結果
    public static <T> Result<T> failed(ResultType resultType,String message) {
        return new Result<>(resultType.status,message,null);
    }
    
    // デフォルト失敗結果
    public static <T> Result<T> failed() {
        return Result.failed(ResultType.FAILED);
    }
    
    
}

