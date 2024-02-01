/**
 * An Object representation of an Artist
 * Artist must be born after 1900 to be considered valid
 * @author Danny Onuorah, Adeola Asimolowo
 */

package musicdb;

public class Artist implements Comparable<Artist> {
    private final String name;
    private final Date born;

    public Artist(String name, Date born) {
        this.name = name;
        this.born = born;
    }

    @Override
    public int compareTo(Artist artist) {
        if (this.name.compareTo(artist.name) != 0) {
            return this.name.compareTo(artist.name);
        } else return this.born.compareTo(artist.born);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Artist artist) {
            return this.name.equalsIgnoreCase(artist.name) && this.born.equals(artist.born);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.name, this.born);
    }

    public static void main(String[] args) {

        Artist artist1 = new Artist("Taylor Swift", new Date(1989, 12, 13));
        Artist artist2 = new Artist("Taylor Swift", new Date(1989, 12, 13));
        Artist artist3 = new Artist("Taylor Swift", new Date(1989, 12, 12));
        Artist artist4 = new Artist("Toylar Swift", new Date(1989, 12, 13));
        Artist artist5 = new Artist("Lebron James", new Date(1950, 0, 0));
        Artist artist6 = new Artist("Gojo Satoru", new Date(1990, 12, 24));
        Artist artist7 = new Artist("Aimer", new Date(1987, 6, 5));

        System.out.println(artist1.compareTo(artist2) == 0); // a1 equal to a2, return zero
        System.out.println(artist1.compareTo(artist3) > 0); // a1 born after a3, return positive
        System.out.println(artist1.compareTo(artist4) < 0); // a1 name before a4, return negative
        System.out.println(artist1.compareTo(artist5) > 0); // a1 name after a5, return positive
        System.out.println(artist5.compareTo(artist6) > 0); // a5 name after a6, return positive
        System.out.println(artist7.compareTo(artist5) < 0); // a7 name before a5, return negative
        System.out.println(artist6.compareTo(artist6) == 0); // a6 same as a6, return zero


    }
    }

    public Date getArtistBorn(){
        return this.born; 
    } 
}