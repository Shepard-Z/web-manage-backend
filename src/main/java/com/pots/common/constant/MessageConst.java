package com.pots.common.constant;

public final class MessageConst {
    
    private MessageConst() {
        // インスタンス化防止
        throw new UnsupportedOperationException("This is a final class and cannot be instantiated");
    }
    
    // admin
    public static final String REQUEST_PARSING_FAILED   = "request data parsing failed";
    public static final String ILLEGAL_FILE_FORM_NAME   = "illegal file form name";
    public static final String ILLEGAL_ITEM_STATUS      = "illegal item status";
    public static final String INVALID_TOKEN            = "invalid token !";
    public static final String EXPIRED_TOKEN            = "expired token";
    public static final String ILLEGAL_USER             = "illegal user !";
    public static final String FAILED_TO_VERIFY_USER_ID = "failed to verify user id";
    public static final String USER_NOT_FOUND           = "user not found";
    public static final String INCORRECT_PASSWORD       = "Incorrect password";
    
    
    public static final String PAGE_ITEM_INSERT_FAILED      = "page item insert failed";
    public static final String PAGE_ITEM_I18N_INSERT_FAILED = "page item i18n insert failed";
    public static final String PAGE_ITEM_RES_INSERT_FAILED  = "page item resource insert failed";
    public static final String PAGE_ITEM_I18N_UPDATE_FAILED = "page item i18n update failed";
    public static final String PAGE_ITEM_RES_UPDATE_FAILED  = "page item resource update failed";
    
    public static final String PAGE_ITEM_DELETE_FAILED      = "page item delete failed";
    public static final String PAGE_ITEM_I18N_DELETE_FAILED = "page item i18n delete failed";
    public static final String PAGE_ITEM_RES_DELETE_FAILED  = "page item resource delete failed";
    
    public static final String FAILED_TO_MAKE_DIRECTORIES   = "failed to make directories";
    public static final String FAILED_TO_SAVE_FILES         = "failed to save files";
    public static final String FAILED_TO_MOVE_OLD_FILE      = "failed to move old file";
    public static final String FAILED_TO_APPLY_FILE_CHANGES = "failed to apply file changes";
    public static final String NO_TEMP_DIR                  = "no temp dir";
    public static final String FAILED_TO_RESTORE_FILES      = "failed to restore files";
    
    public static final String FAILED_TO_DELETE_OLD_FILE    = "failed to delete old file";
    public static final String FAILED_TO_DELETE_TEMP_FILES  = "failed to delete temp files";
    public static final String FAILED_TO_DELETE_TARGET_FILE = "failed to delete target file :";
    public static final String FILE_COUNT_INCONSISTENT      = "The number of files is inconsistent";
    
    public static final String ILLEGAL_UPLOAD_CATEGORY = "illegal upload category in remove target";
    
}
