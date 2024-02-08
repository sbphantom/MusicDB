/**
 * Enum Class for various types of music genres
 * @author Danny Onuorah
 */

package musicdb;

public enum Genre {
    POP,
    COUNTRY,
    CLASSICAL,
    JAZZ,
    UNKNOWN;

    @Override
    public String toString(){
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

}

