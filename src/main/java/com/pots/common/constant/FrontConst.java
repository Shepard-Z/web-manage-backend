package com.pots.common.constant;

public final class FrontConst {
    
    private FrontConst() {
        // インスタンス化防止
        throw new UnsupportedOperationException("This is a final class and cannot be instantiated");
    }
    
    public static final String REMOVE_NO_PARENT = "-777";
    
    public static final class ItemStatus {
        
        private ItemStatus() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
        
        public static final String NEW    = "new";
        public static final String UPDATE = "update";
        public static final String SYNCED = "synced";
        public static final String REMOVE = "remove";
        
        
    }
    
    public static final class UploadCategory {
        
        private UploadCategory() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
        
        public static final String PAGE          = "page";
        public static final String PAGE_I18N     = "pageI18n";
        public static final String PAGE_RESOURCE = "pageResource";
        
        public static final String GAME      = "game";
        public static final String GAME_I18N = "gameI18n";
        
        public static final String CHARA          = "chara";
        public static final String CHARA_I18N     = "charaI18n";
        public static final String CHARA_RESOURCE = "charaResource";
        
        public static final String TEAM          = "team";
        public static final String TEAM_I18N     = "teamI18n";
        public static final String TEAM_RESOURCE = "teamResource";
        
    }
    
}
