package com.ofppt.absys.Main.Utils;

import com.ofppt.absys.Main.Constants.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HelperUtils {
    public static String DateFormatter(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date);
    }
    public static void initPM_AM(){
        Calendar cal = Calendar.getInstance();
        Constants.AM_PM = cal.get(Calendar.AM_PM);
    }
}
