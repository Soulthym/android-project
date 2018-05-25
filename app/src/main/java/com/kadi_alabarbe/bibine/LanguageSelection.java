package com.kadi_alabarbe.bibine;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageSelection {
    public static void changeLanguage(Resources res, String langue){
        Configuration config;
        config=new Configuration(res.getConfiguration());

        switch (langue){
            case "fr-rFR":
                config.setLocale(Locale.FRENCH);
                break;
            default:
                config.setLocale(Locale.ENGLISH);
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}