package com.pots.common.model;


import com.pots.common.constant.MessageConst;

public enum ResultType {
    SUCCESS(1,"success"),
    FAILED(2,"failed"),
    
    // site
    NO_ACCEPT_LANGUAGE(3,"no accept-language"),
    NO_SUCH_LANG(4,"no such language"),
    NO_DATA(5,"no data"),
    DATA_NOT_FOUND(6,"data not found"),
    
    // admin
    REQUEST_PARSING_FAILED(7,MessageConst.REQUEST_PARSING_FAILED),
    ILLEGAL_FILE_FORM_NAME(8,MessageConst.ILLEGAL_FILE_FORM_NAME),
    ILLEGAL_ITEM_STATUS(9,MessageConst.ILLEGAL_ITEM_STATUS),
    ILLEGAL_UPLOAD_CATEGORY(10,MessageConst.ILLEGAL_UPLOAD_CATEGORY),
    USER_NOT_FOUND(11,MessageConst.USER_NOT_FOUND),
    INCORRECT_PASSWORD(12,MessageConst.INCORRECT_PASSWORD),
    
    
    // pageItem database
    PAGE_ITEM_INSERT_FAILED(21,MessageConst.PAGE_ITEM_INSERT_FAILED),
    PAGE_ITEM_I18N_INSERT_FAILED(22,MessageConst.PAGE_ITEM_I18N_INSERT_FAILED),
    PAGE_ITEM_RES_INSERT_FAILED(23,MessageConst.PAGE_ITEM_RES_INSERT_FAILED),
    PAGE_ITEM_I18N_UPDATE_FAILED(24,MessageConst.PAGE_ITEM_I18N_UPDATE_FAILED),
    PAGE_ITEM_RES_UPDATE_FAILED(25,MessageConst.PAGE_ITEM_RES_UPDATE_FAILED),
    PAGE_ITEM_DELETE_FAILED(26,MessageConst.PAGE_ITEM_DELETE_FAILED),
    PAGE_ITEM_I18N_DELETE_FAILED(27,MessageConst.PAGE_ITEM_I18N_DELETE_FAILED),
    PAGE_ITEM_RES_DELETE_FAILED(28,MessageConst.PAGE_ITEM_RES_DELETE_FAILED),
    
    
    // files
    FAILED_TO_MAKE_DIRECTORIES(31,MessageConst.FAILED_TO_MAKE_DIRECTORIES),
    FAILED_TO_SAVE_FILES(32,MessageConst.FAILED_TO_SAVE_FILES),
    FAILED_TO_DELETE_TARGET_FILE(33,MessageConst.FAILED_TO_DELETE_TARGET_FILE),
    FAILED_TO_DELETE_OLD_FILE(34,MessageConst.FAILED_TO_DELETE_OLD_FILE),
    FAILED_TO_MOVE_OLD_FILE(35,MessageConst.FAILED_TO_SAVE_FILES),
    FAILED_TO_APPLY_FILES(36,MessageConst.FAILED_TO_APPLY_FILE_CHANGES),
    FILE_COUNT_INCONSISTENT(37,MessageConst.FILE_COUNT_INCONSISTENT),
    
    // token
    TOKEN_EXPIRED(41,MessageConst.EXPIRED_TOKEN),
    TOKEN_INVALID(42,MessageConst.INVALID_TOKEN),
    FAILED_TO_VERIFY_USER_ID(43,MessageConst.FAILED_TO_VERIFY_USER_ID),
    ILLEGAL_USER(44,MessageConst.ILLEGAL_USER);
    
    
    public final int    status;
    public final String message;
    
    ResultType(int status,String message) {
        this.status = status;
        this.message = message;
    }
    
}
