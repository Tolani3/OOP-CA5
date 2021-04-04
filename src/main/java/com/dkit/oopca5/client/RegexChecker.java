package com.dkit.oopca5.client;

/* This class should contain static methods to verify input in the application
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexChecker
{
    public static boolean checkRegister(String pass, String patern)
    {
        boolean result = false;
        try
        {
            String s = String.valueOf(pass);
            Pattern pattern = Pattern.compile(patern);
            Matcher matcher = pattern.matcher(s);
            boolean matchFound = matcher.find();
            if (matchFound)
            {
                result = true;
            }
            else
                {
                    result = false;
                }
        }
        catch (PatternSyntaxException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
