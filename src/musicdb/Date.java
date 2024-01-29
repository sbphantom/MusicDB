package musicdb;
import java.time.Month;
import java.util.GregorianCalendar;


public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    

    @Override public int compareTo(Date date){
        if (this.year == date.year && this.month == date.month && this.day == date.day){
            return 0; 
        }
        
        if(this.year >= date.year){
            return 1; 
        }
        else if(this.year == date.year && this.month > date.month){
            return 1; 
        }
        else if(this.year == date.year && this.month == date.month && this.day > date.day){
            return 1; 
        }

        return -1; 

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
