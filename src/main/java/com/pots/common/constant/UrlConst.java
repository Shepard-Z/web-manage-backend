package com.pots.common.constant;

import java.io.File;

public final class UrlConst {
    
    public static final String SITE = "http://localhost:7032/";
    
    public static final String NOW_DIRECTORY          = System.getProperty("user.dir");
    public static final String UPLOAD_DIRECTORY   = NOW_DIRECTORY + File.separator + "upload";
    public static final String TEMP_NEW_FOLDER   = "temp_new";
    public static final String TEMP_OLD_FOLDER   = "temp_old";
    public static final String TEMP_NEW_DIRECTORY = NOW_DIRECTORY + File.separator + TEMP_NEW_FOLDER;
    public static final String TEMP_OLD_DIRECTORY = NOW_DIRECTORY + File.separator + TEMP_OLD_FOLDER;
    public static final String PAGE_FOLDER        = "page";
    public static final String GAME_FOLDER        = "game";
    public static final String CHARA_FOLDER = "chara";
    public static final String TEAM_FOLDER  = "team";
    public static final String ADMIN_FOLDER = "admin";
    
    private UrlConst() {
        throw new UnsupportedOperationException("This is a constant class and cannot be instantiated");
    }
    
}
