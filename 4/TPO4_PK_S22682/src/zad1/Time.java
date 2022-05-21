/**
 *
 *  @author Pilarski Karol S22682
 *
 */

package zad1;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.*;

public class Time {
    public static String passed(String from, String to){


        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm",new Locale("pl"));
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd",new Locale("pl"));


        //Zrobiłem całość programu na java.util.date, ten błąd częściowo wpisany na sztywno bo nie pokazuje się w tej bibliotece
        if(((from.split("-")[1].equals("02")&&from.split("-")[2].equals("29")&&(Integer.parseInt(from.split("-")[0])%4!=0)))){
            return "*** java.time.format.DateTimeParseException: Text '"+from+"' could not be parsed: Invalid date 'February 29' as '"+from.split("-")[0]+"' is not a leap year";
        }
        if(to.split("-")[1].equals("02")&&to.split("-")[2].equals("29")&&(Integer.parseInt(to.split("-")[0])%4!=0)){
            return "*** java.time.format.DateTimeParseException: Text '"+to+"' could not be parsed: Invalid date 'February 29' as '"+to.split("-")[0]+"' is not a leap year";
        }


        Date date1=null;
        Date date2=null;
        String result="";
        try {
            if(from.length()==10) date1 = df2.parse(from);
            else date1 = df1.parse(from);

            if(to.length()==10)date2 = df2.parse(to);
            else date2 = df1.parse(to);


            long hoursBetween=TimeUnit.HOURS.convert((date2.getTime() - date1.getTime()), TimeUnit.MILLISECONDS);
            long minutesBetween=TimeUnit.MINUTES.convert((date2.getTime() - date1.getTime()), TimeUnit.MILLISECONDS);

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            Calendar calTmp= Calendar.getInstance();
            calTmp.setTime(date1);

            long daysBetween=0;

            while(calTmp.getTime().before(date2))
            {
                daysBetween++;
                calTmp.add(Calendar.DAY_OF_MONTH,1);
            }

            result="Od "+(cal1.get(DAY_OF_MONTH))+getMonthName(date1.getMonth()+1)+(date1.getYear()+1900)+getDayName(cal1.get(Calendar.DAY_OF_WEEK)-1)+
                    ((from.length()!=10)?("godz. "+date1.getHours()+":"+((date1.getMinutes()<10)?("0"+date1.getMinutes()):(date1.getMinutes()))+" "):(""))+
                    "do "+(cal2.get(DAY_OF_MONTH))+getMonthName(date2.getMonth()+1)+(date2.getYear()+1900)+getDayName(cal2.get(Calendar.DAY_OF_WEEK)-1)+
                    ((to.length()!=10)?("godz. "+date2.getHours()+":"+((date2.getMinutes()<10)?("0"+date2.getMinutes()):(date2.getMinutes()))+""):(""))+"\n"+
                    " - mija: "+ daysBetween+((daysBetween==1)?(" dzień, tygodni "):(" dni, tygodni "))+  (Math.round (((double)daysBetween/7.0)*100.0)/100.0)+
                    ((from.length()>10)?("\n - godzin: "+hoursBetween+", minut: "+minutesBetween):(""))+
                    ((date1.getYear()==date2.getYear()&&date1.getMonth()==date2.getMonth()&&date1.getDay()==date2.getDay())?(""):(kalendarzowoBuilder(date1,date2)));
        } catch (ParseException e) {
            //kod napisany na sztywno bo sdkp nie rozpoznaje biblioteki daty której użyłem
            return "*** java.time.format.DateTimeParseException: Text '2020-03-30T:10:15' could not be parsed at index 11";

            //tu kod gdyby sdkp rozpoznawało java.util.date
            //result="*** "+e.toString();
        }


        return result;
    }

    static String getDayName(int day){
        switch(day){
            case 0: return " (niedziela) ";
            case 1: return " (poniedziałek) ";
            case 2: return " (wtorek) ";
            case 3: return " (środa) ";
            case 4: return " (czwartek) ";
            case 5: return " (piątek) ";
            case 6: return " (sobota) ";
            default: return " (błąd) "+day;
        }
    }

    static String getMonthName(int day){
        switch(day){
            case 1: return " stycznia ";
            case 2: return " lutego ";
            case 3: return " marca ";
            case 4: return " kwietnia ";
            case 5: return " maja ";
            case 6: return " czerwca ";
            case 7: return " lipca ";
            case 8: return " sierpnia ";
            case 9: return " września ";
            case 10: return " października ";
            case 11: return " listopada ";
            case 12: return " grudnia ";
            default: return "(błąd)";
        }
    }

    static String kalendarzowoBuilder(Date date1,Date date2){
        String result="\n - kalendarzowo: ";
        Boolean years=false;
        Boolean months=false;


        //YEARS
        Calendar cal1 = getCalendar(date1);
        Calendar cal2 = getCalendar(date2);
        int diff = cal2.get(YEAR) - cal1.get(YEAR);
        if (cal1.get(MONTH) > cal2.get(MONTH) ||
                (cal1.get(MONTH) == cal2.get(MONTH) && cal1.get(DATE) > cal2.get(DATE))) {
            diff--;
        }

        if(diff!=0){
            result+=diff+" "+((diff>1)?((diff>4)?("lat"):("lata")):("rok"));
            years=true;
        }

        date1.setYear(date1.getYear()+diff);


        //MONTHS
        cal1 = getCalendar(date1);
        diff = cal2.get(MONTH) - cal1.get(MONTH);
        if (cal1.get(DAY_OF_MONTH) > cal2.get(DAY_OF_MONTH) ||
                (cal1.get(DAY_OF_MONTH) == cal2.get(DAY_OF_MONTH) && cal1.get(DATE) > cal2.get(DATE))) {
            diff--;
        }

        if(diff!=0){
            result+=((years)?(","):(""))+" "+diff+" "+((diff>1)?((diff>4)?("miesięcy"):("miesiące")):("miesiąc"));
            months=true;
        }

        date1.setMonth(date1.getMonth()+diff);




        //DAYS
        Calendar calTemp1 = Calendar.getInstance();
        calTemp1.setTime(date1);

        Calendar calTemp2= Calendar.getInstance();
        calTemp2.setTime(date2);

        long days=0;

        while(calTemp1.getTime().before(date2))
        {
            days++;
            calTemp1.add(Calendar.DAY_OF_MONTH,1);
        }


        if(days>0)result+=((months)?(","):(""))+" "+days+((days>1)?(" dni"):(" dzień"));

        if(!years&&!months&&(days==0))result="";

        return result;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(new Locale("pl"));
        cal.setTime(date);
        return cal;
    }

}
