/**
 * Enum Class for various types of music genres
 *
 * @author Danny Onuorah
 */

package musicdb;

public enum Genre {
    POP,
    COUNTRY,
    CLASSICAL,
    JAZZ,
    UNKNOWN;

    /**
     * Returns a string representation of the genre with the first letter capitalized.
     *
     * @return The string representation of the genre.
     */
    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

}

