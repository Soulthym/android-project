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
                config.locale=Locale.FRENCH;
                break;
            default:
                config.locale=Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}