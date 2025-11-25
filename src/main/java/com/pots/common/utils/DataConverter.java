package com.pots.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class DataConverter {
    
    // インスタンス化を禁止するための private コンストラクタ
    private DataConverter() {}
 
    public static <S, T> T toVO(S source, Class<T> targetClass) {
        if (source == null) return null;
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("コピー失敗: " + source.getClass().getSimpleName() + " → " + targetClass.getSimpleName(), e);
        }
    }

    public static <S, T> List<T> toVOList(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) return List.of();
        return sourceList.stream()
                .map(source -> toVO(source, targetClass))
                .collect(Collectors.toList());
    }
}
