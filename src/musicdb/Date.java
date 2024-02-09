/**
 * An Object representation of a Date.
 * Range of valid date values are from 1/1/1900
 * to the date of compiled time.
 * <p>
 * Provides methods to create Date objects, compare them, and check for validity.
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */

package musicdb;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date implements Comparable<Date> {
    public final int NUM_OF_MONTHS = 12;
    public final int MAX_DAYS = 31;
    public final int MIN_YEAR = 1900;

    private int year;
    private int month;
    private int day;


    /**
     * Constructs a Date object with the specified year, month, and day.
     *
     * @param year  The year number.
     * @param month The month number.
     * @param day   The day number.
     */
    public Date(int year, int month, int day) {
        if (isValid(year, month, day)) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    /**
     * Returns the current date as a Date object.
     *
     * @return A Date object representing the current date.
     */
    public static Date todayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar months are 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new Date(year, month, day);
    }

    /**
     * Compares this Date object with another Date object.
     *
     * @param date The Date object to be compared.
     * @return A negative integer, zero, or a positive integer as this Date is before, equal to, or after the specified Date.
     */
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

    /**
     * Returns a string representation of this Date object.
     *
     * @return A string representation of this Date object in the format "month/day/year".
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

    /**
     * Checks if this Date object is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date date) {
            return compareTo(date) == 0;
        }
        return false;
    }

    /**
     * Checks if the given year, month, and day form a valid date.
     *
     * @param year  The year to be checked.
     * @param month The month to be checked.
     * @param day   The day to be checked.
     * @return True if the date is valid; otherwise, false.
     */
    public boolean isValid(int year, int month, int day) {
        if (year < MIN_YEAR || month < 1 || day < 1 || month > NUM_OF_MONTHS || day > MAX_DAYS) {
            return false;
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setLenient(false);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        try {
            calendar.get(Calendar.YEAR); // This will trigger any potential exceptions
            calendar.get(Calendar.MONTH);
            calendar.get(Calendar.DAY_OF_MONTH);
            return true; // If no exception occurred, date is valid
        } catch (Exception e) {
            return false; // Exception means date is invalid
        }
    }

    /**
     * A main method for testing the Date class.
     *
     * @param args The command line arguments (unused).
     */
    public static void main(String[] args) {
        // Test cases for Date class
    }
}
