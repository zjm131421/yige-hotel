package com.yige.common.helper;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class StringHelpers {

    public static final String EMPTY = "";

    private static final String[] VOWELS = {"a","e","i","o","u"};
    private static final String[] FOR_ES_SUFFIX = {"s", "sh", "ch", "x", "z"};
    private static final String LAST_3_BYTE_UTF_CHAR = "\uFFFF";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static boolean startsWith(String str, String prefix) {
        return startsWith(str, prefix, false);
    }

    private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            return (str == null && prefix == null);
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }

    public static String trimStart(String str, char c) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        int start = 0;
        while ((start != strLen) && (str.charAt(start) == c)) {
            start++;
        }
        return str.substring(start);
    }

    public static String trimEnd(String str, char c) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }
        while ((end != 0) && str.charAt(end - 1) == c) {
            end--;
        }
        return str.substring(0, end);
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }

        return str.substring(start);
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    public static String limitLength(String str, int length) {
        if (DataHelpers.isEmptyString(str)) {
            return str;
        }
        return str.length() > length ? str.substring(0, length) : str;
    }

    private static String[] splitByCamelCase(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }

        char[] charArray = str.toCharArray();
        List<String> stringList = new ArrayList<>();
        int tokenStart = 0;
        int currentType = Character.getType(charArray[tokenStart]);
        for (int pos = tokenStart + 1; pos < charArray.length; pos++) {
            int type = Character.getType(charArray[pos]);
            if (type == currentType) {
                continue;
            }
            if (type != Character.LOWERCASE_LETTER && type != Character.UPPERCASE_LETTER) {
                currentType = Character.LOWERCASE_LETTER;
                continue;
            }
            if (type == Character.LOWERCASE_LETTER && currentType == Character.UPPERCASE_LETTER) {
                int newTokenStart = pos - 1;
                if (newTokenStart != tokenStart) {
                    stringList.add(new String(charArray, tokenStart, newTokenStart - tokenStart));
                    tokenStart = newTokenStart;
                }
            }
            else {
                stringList.add(new String(charArray, tokenStart, pos - tokenStart));
                tokenStart = pos;
            }
            currentType = type;
        }
        stringList.add(new String(charArray, tokenStart, charArray.length - tokenStart));
        return stringList.toArray(new String[stringList.size()]);
    }

    public static String toValid3ByteUTF8String(String s)  {
        final int length = s.length();
        StringBuilder b = new StringBuilder(length);
        for (int offset = 0; offset < length; ) {
            final int codePoint = s.codePointAt(offset);

            offset += Character.charCount(codePoint);

            if (codePoint > LAST_3_BYTE_UTF_CHAR.codePointAt(0)) {
                continue;
            }
            if (Character.isValidCodePoint(codePoint)) {
                b.appendCodePoint(codePoint);
            }
        }
        return b.toString();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
