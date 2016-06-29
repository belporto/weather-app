package br.com.porto.isabel.weather.formatter;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DateFormatterTest {

    private String mExpectedFormattedData;
    private Date mData;
    private String mPattern;
    private DateFormatter mDateFormatter;

    @Before
    public void initialize() {
        mDateFormatter = new DateFormatter();
    }


    public DateFormatterTest(Date data, String pattern, String expectedFormattedData) {
        mData = data;
        mExpectedFormattedData = expectedFormattedData;
        mPattern = pattern;
    }

    @Parameterized.Parameters
    public static Collection formattedData() throws ParseException {
        return Arrays.asList(new Object[][]{
                {getData("29/06/2016 11:00"), "EEE" , "Wed"},
                {getData("30/06/2016 11:00"), "EEE" , "Thu"},
                {getData("31/06/2016 11:00"), "EEEEE" , "Friday"},
                {getData("29/06/2016 11:00"), "YYYY" , "2016"},
                {getData("29/06/2016 11:00"), "HH:mm" , "11:00"},
                {getData("29/06/2016 11:00"), "dd MM YYYY" , "29 06 2016"},
                {null, "dd MM YYYY" , ""},
                {getData("29/06/2016 11:00"), null , ""}
        });
    }

    private static Date getData(String dateString) throws ParseException {
        return new SimpleDateFormat().parse(dateString);
    }


    @Test
    public void testFormattedData() {
        assertEquals(mExpectedFormattedData, mDateFormatter.format(mData, mPattern));
    }
}
