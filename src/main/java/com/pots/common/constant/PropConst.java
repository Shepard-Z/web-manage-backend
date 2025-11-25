package com.pots.common.constant;

import java.util.Set;

public final class PropConst {
    
    private PropConst() {
        // インスタンス化防止
        throw new UnsupportedOperationException("This is a final class and cannot be instantiated");
    }
    
    public static final String      ACCEPT_LANGUAGE  = "Accept-Language";
    public static final String      DEFAULT_LANGUAGE = "zh-CN";
    public static final Set<String> ALLOWED_SET      = Set.of(DEFAULT_LANGUAGE,"en-US","ja-JP");
    
    public static final String REQUEST_ATTRIBUTE_ID   = "id";
    public static final String REQUEST_ATTRIBUTE_NAME = "name";
    public static final Long   SYSTEM_ID              = 999L;
    public static final String SYSTEM_NAME            = "origin";
    
    
    // 資源データの種類
    public static final class ResourceType {
        
        public static final String IMAGE = "image";
        public static final String LINK  = "link";
        public static final String OTHER = "other";
        
    }
    
    
}
