package com.ofppt.absys.Main.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperUtils {
    public static String DateFormatter(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(date);
    }
}
