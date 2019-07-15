package com.ofppt.absys.Main.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperUtils {
    public static String DateFormatter(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date);
    }
}
