/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {

    public static final int CHARGES_PER_DAY=10;
    
    public static final int MINIMUM_DAYS=7;
    
    public static long diffInDays(String dateStart) {

        String dateStop = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        dateStop = format.format(cal.getTime());
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            diff = diff / (24 * 60 * 60 * 1000);
            return diff;
        } catch (Exception e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("Diff In Days: "+diffInDays("2022-03-23 15:57:58"));
    }
}
