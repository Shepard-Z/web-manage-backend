package com.pots.common.utils;

import com.pots.common.model.ResultType;
import lombok.Getter;

@Getter
public class OriginException extends RuntimeException {
    
    private final ResultType resultType;
    private final String     originMessage;
    
    // resultType だけでインスタンス化する場合、originMessage は resultTypeのデフォルトメッセージにする
    public OriginException(ResultType resultType) {
        this.resultType = resultType;
        this.originMessage = resultType.message;
    }
    
    // originMessage 付きでインスタンス化する場合、originMessage は resultTypeのデフォルトメッセージで合成する
    public OriginException(ResultType resultType,String originMessage) {
        this.resultType = resultType;
        this.originMessage = resultType.message + originMessage;
    }
    
}
