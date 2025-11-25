package com.pots.common.utils;

import com.pots.common.constant.MessageConst;
import com.pots.common.constant.UrlConst;
import com.pots.common.model.ResultType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;

@Slf4j
public class MyFileUtils {
    
    private MyFileUtils() {
    }
    
    // ファイル名の拡張子の取得（"."を含む）
    public static String getExtension(String fileName) {
        if (fileName == null || !fileName.contains("."))
            return "";
        
        int index = fileName.lastIndexOf(".");
        // ファイル名に "." が中間にある場合
        if (index > 0 && index < fileName.length() - 1) {
            return fileName.substring(index);
        }
        // ファイル名に "." が最初また最後にある場合
        return "";
        
    }
    
    // 拡張子がないファイル名の取得
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null)
            return "";
        
        int index = fileName.lastIndexOf(".");
        // ファイル名に "." が中間また最後にある場合
        if (index > 0) {
            return fileName.substring(0,index);
        }
        // ファイル名に "." が最初にある場合
        return fileName;
    }
    
    // ファイルのurlを相対パスに変換
    public static File urlToFile(String url) {
        if (url == null)
            return null;
        String path = url.replace(UrlConst.SITE,"");
        // Fileコンストラクタを使用してシステムの区切り文字を自動的に処理します（"/" or "\"）
        return new File(UrlConst.UPLOAD_DIRECTORY,path);
    }
    
    // 臨時新規ディレクトリから、実のディレクトリに反映する
    public static boolean applyFiles() {
        Path tempDir = Paths.get(UrlConst.TEMP_NEW_DIRECTORY);
        Path permDir = Paths.get(UrlConst.UPLOAD_DIRECTORY);
        Path tempOldDir = Paths.get(UrlConst.TEMP_OLD_DIRECTORY);
        // 新規ファイルの移動
        if (Files.exists(tempDir)) {
            try (Stream<Path> paths = Files.walk(tempDir)) {
                paths.forEach(source -> {
                    try {
                        Path target = permDir.resolve(tempDir.relativize(source));
                        if (Files.isDirectory(source)) {
                            Files.createDirectories(target);
                        } else if (Files.isRegularFile(source)) {
                            Files.createDirectories(target.getParent());
                            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
            } catch (UncheckedIOException | IOException e) {
                log.error(MessageConst.FAILED_TO_APPLY_FILE_CHANGES, e);
                return false;
            }
        } else {
            try {
                Files.createDirectories(tempDir);
            } catch (IOException e) {
                log.warn(MessageConst.FAILED_TO_MAKE_DIRECTORIES);
                throw new OriginException(ResultType.FAILED_TO_MAKE_DIRECTORIES);
            }
        }
        // 旧ファイルの削除
        if (Files.exists(tempOldDir)) {
            try (Stream<Path> paths = Files.walk(tempOldDir)) {
                // ファイルだけ削除する
                paths.filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                Files.delete(file);
                            } catch (IOException e) {
                                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES, e);
                            }
                        });
            } catch (IOException e) {
                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES, e);
                return false;
            }
        }
        
        return true;
    }

    
    // ファイルを削除予定ディレクトリから、元のディレクトリに戻す、臨時新規ファイルを削除
    public static boolean restoreFiles() {
        Path tempOldDir = Paths.get(UrlConst.TEMP_OLD_DIRECTORY);
        Path permDir    = Paths.get(UrlConst.UPLOAD_DIRECTORY);
        Path tempNewDir = Paths.get(UrlConst.TEMP_NEW_DIRECTORY);
        // 削除予定ファイルの復旧
        if (Files.exists(tempOldDir)) {
            try (Stream<Path> paths = Files.walk(tempOldDir)) {
                paths.forEach(source -> {
                    try {
                        Path target = permDir.resolve(tempOldDir.relativize(source));
                        if (Files.isDirectory(source)) {
                            Files.createDirectories(target);
                        } else if (Files.isRegularFile(source)) {
                            Files.createDirectories(target.getParent());
                            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
            } catch (UncheckedIOException | IOException e) {
                log.error(MessageConst.FAILED_TO_RESTORE_FILES, e);
                return false;
            }
        }
        // 新規ファイルの破棄
        if (Files.exists(tempNewDir)) {
            try (Stream<Path> paths = Files.walk(tempNewDir)) {
                paths.filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                Files.delete(file);
                            } catch (IOException e) {
                                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES + " : {}", file, e);
                            }
                        });
            } catch (IOException e) {
                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES, e);
                return false;
            }
        }
        return true;
    }
    
    // 臨時ディレクトリと削除予定ディレクトリにあるファイルを全部削除
    public static boolean clearAllTempFiles() {
        Path tempDir    = Paths.get(UrlConst.TEMP_NEW_DIRECTORY);
        Path tempOldDir = Paths.get(UrlConst.TEMP_OLD_DIRECTORY);
        if (Files.exists(tempDir)) {
            try (Stream<Path> stream = Files.walk(tempDir)) {
                // ファイルだけ削除する
                stream.filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES + " : {}",path,e);
                            }
                        });
            } catch (IOException e) {
                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES,e);
                return false;
            }
        }
        if (Files.exists(tempOldDir)) {
            try (Stream<Path> stream = Files.walk(tempOldDir)) {
                // ファイルだけ削除する
                stream.filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES + " : {}",path,e);
                            }
                        });
            } catch (IOException e) {
                log.error(MessageConst.FAILED_TO_DELETE_TEMP_FILES,e);
                return false;
            }
        }
        return true;
    }
    
    
}
