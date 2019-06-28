package com.ofppt.absys.Main.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

        private static SharedPreference Preference;
        private SharedPreferences sharedPreferences;

        public static SharedPreference getInstance(Context context) {
            if (Preference == null) {
                Preference = new SharedPreference(context);
            }
            return Preference;
        }

        private SharedPreference(Context context) {
            sharedPreferences = context.getSharedPreferences("AbsysSettings",Context.MODE_PRIVATE);
        }

        public void saveData(String key,String value) {
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor .putString(key, value);
            prefsEditor.apply();
        }

        public String getData(String key) {
            if (sharedPreferences!=  null) {
                return sharedPreferences.getString(key, "");
            }
            return "";
        }
    
}
