package com.edupay.utils;

public final class FileUtils {

    private FileUtils() {}

    public static boolean isValidMimeType(String mimeType) {
        return mimeType != null && (
            mimeType.equals("application/pdf") ||
            mimeType.equals("image/jpeg") ||
            mimeType.equals("image/png") ||
            mimeType.equals("text/csv")
        );
    }
}
