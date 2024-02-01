package musicdb;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        if (isValid(year, month, day)) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    public static Date todayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar months are 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        Date todaysDate = new Date(year, month, day); 
        return todaysDate; 
    }

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

    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Date date){
            return compareTo(date) == 0;
        }
        return false;
    }

    //2. The date of birth of an artist is today or a future date.
    //3. The date of birth of an artist is before the year of 1900.
    //5. The release date is today or a future date.
    //6. The release date is before the year of 1900.
    /**
     * An Object representation of a Date
     * Range of valid date values are from 1/1/1900
     * to date of compiled time.
     * @param year the year number
     * @param month the month number
     * @param day the day number
     */
    public  boolean isValid(int year, int month, int day) {
        if (year < MIN_YEAR || month < 1 || day < 1 || month > NUM_OF_MONTHS || day > MAX_DAYS) {
            return false;
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setLenient(false);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Months are 0-based
        calendar.set(Calendar.DAY_OF_MONTH, day);
        

        try {
            calendar.get(Calendar.YEAR); // This will trigger any potential exceptions
            calendar.get(Calendar.MONTH);
            calendar.get(Calendar.DAY_OF_MONTH);
            return true; // If no exception occurred, date is valid
        } catch (Exception e) {
            return false; // Exception means date is invalid
        }
    } //check if the date is a valid calendar date

    public static void main(String[] args) {
        // Dates
        System.out.println(new Date(2024, 1, 30).toString().equals( "1/30/2024"));
        System.out.println(new Date(2024, 1, 31).toString().equals( "1/31/2024"));
        System.out.println(new Date(2024, 2, 1).toString().equals( "2/1/2024"));
        System.out.println(new Date(2024, 2, 12).toString().equals( "2/12/2024"));
        System.out.println(new Date(2024, 2, 29).toString().equals( "2/29/2024"));
        System.out.println(new Date(2024, 3, 30).toString().equals( "3/30/2024"));

        System.out.println(new Date(2023, 2, 29).toString().equals( "0/0/0")); // invalid non-leap year
        System.out.println(new Date(2024, 2, 30).toString().equals( "0/0/0")); // invalid out of bound day
        System.out.println(new Date(2024, 4, 31).toString().equals( "0/0/0")); // invalid out of bound day
        System.out.println(new Date(Integer.MIN_VALUE, 1, 1).toString().equals( "0/0/0")); // invalid
        System.out.println(new Date(Integer.MAX_VALUE, 12, 31).toString().equals( "0/0/0")); // invalid
        System.out.println(new Date(2024, 0, 1).toString().equals( "0/0/0")); // invalid month
        System.out.println(new Date(2024, 13, 1).toString().equals( "0/0/0")); // invalid month
        System.out.println(new Date(2024, 1, 0).toString().equals( "0/0/0")); // invalid day
        System.out.println(new Date(2024, 1, 32).toString().equals( "0/0/0")); // invalid day

        // Comparisons
        Date date1 = new Date(2024, 2, 12);
        Date date2 = new Date(2024, 2, 13);
        System.out.println(date1.compareTo(date2) < 0);
        System.out.println(date1.compareTo(date1) == 0);
        System.out.println(date2.compareTo(date1) > 0);

    }
}