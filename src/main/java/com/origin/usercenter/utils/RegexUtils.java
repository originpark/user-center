package com.origin.usercenter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    private static final String REGEX_A1_ = "^[a-zA-Z0-9_]*$";

    public static boolean compile(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean matchA1_(String str) {
        return compile(REGEX_A1_, str);
    }
}
