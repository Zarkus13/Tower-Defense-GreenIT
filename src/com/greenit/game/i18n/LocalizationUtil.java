/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author oli
 */
public class LocalizationUtil {
    private static Locale locale;

    public static String getString(String key){
        return ResourceBundle.getBundle("com.greenit.game.i18n.greenit", getLocale()).getString(key);
    }
    
    public static Locale getLocale() {
        if(locale == null){
            locale = new Locale("en", "US");
        }
        return locale;
    }

    public static void setLocale(Locale locale) {
        LocalizationUtil.locale = locale;
    }
    
    
}
