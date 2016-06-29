package br.com.porto.isabel.weather.formatter;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public String format(Date date, String pattern) {
        String formattedDate = "";
        if (date != null && pattern != null) {
            SimpleDateFormat format = new SimpleDateFormat(pattern, new Locale("En"));
            formattedDate = format.format(date);
        }

        return formattedDate;
    }
}
