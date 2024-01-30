package musicdb;
import java.time.Month;
import java.util.GregorianCalendar;


public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    

    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return this.year - date.year;
        }
        if (this.month != date.month) {
            return this.month - date.month;
        }
        return this.day - date.day;
    }

    public boolean isValid(){ 
        int trueCalenderMonth = month - 1;
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setLenient(false);
        gcal.set(year, trueCalenderMonth, day);
        
        try {
            gcal.getTime(); 
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
            return false; 
        }
       
        return true; 
    } //check if the date is a valid calendar date
}
