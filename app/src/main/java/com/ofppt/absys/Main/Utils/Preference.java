package com.ofppt.absys.Main.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

        private static Preference Preference;
        private SharedPreferences sharedPreferences;

        public static Preference getInstance(Context context) {
            if (Preference == null) {
                Preference = new Preference(context);
            }
            return Preference;
        }

        private Preference(Context context) {
            sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference",Context.MODE_PRIVATE);
        }

        public void saveData(String key,String value) {
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor .putString(key, value);
            prefsEditor.commit();
        }

        public String getData(String key) {
            if (sharedPreferences!= null) {
                return sharedPreferences.getString(key, "");
            }
            return "";
        }
    
}
