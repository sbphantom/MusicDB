package musicdb;

/**
 * An Object representation of an Album which consists of a title, Artist, Genre, release Date, and ratings.
 * Ratings are stored as a linked list and rating is the head.
 *
 * @author Danny Onuorah
 */
public class Album {
    private final String title;
    private final Artist artist;
    private final Genre genre;
    private final Date released;

    private Rating ratings; // a linked list of ratings

    public static final int MAX_STARS = 5;

    /**
     * Constructs an Album with the specified attributes.
     *
     * @param title    The title of the album.
     * @param artist   The artist of the album.
     * @param genre    The genre of the album.
     * @param released The release date of the album.
     */
    public Album(String title, Artist artist, Genre genre, Date released) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.released = released;
    }

    /**
     * Gets the title of the album.
     *
     * @return The title of the album.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the artist of the album.
     *
     * @return The artist of the album.
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Gets the genre of the album.
     *
     * @return The genre of the album.
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Gets the release date of the album.
     *
     * @return The release date of the album.
     */
    public Date getReleased() {
        return released;
    }

    /**
     * Gets the ratings of the album.
     *
     * @return The ratings of the album.
     */
    public Rating getRatings() {
        return ratings;
    }

    /**
     * Adds a new rating to the album.
     *
     * @param star The rating value (an integer between 1 and MAX_STARS).
     */
    public void rate(int star) {
        if (ratings == null) {
            ratings = new Rating(star);
            return;
        }

        Rating current = ratings;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(new Rating(star));
    }

    /**
     * Computes the average rating of the album.
     *
     * @return The average rating of the album as a double.
     */
    public double avgRatings() {
        Rating current = ratings;
        if (current == null) {
            return 0;
        }

        double sum = 0;
        int length = 0;
        while (current != null) {
            sum += current.getStar();
            length += 1;
            current = current.getNext();
        }
        return sum / length;
    }

    /**
     * Generates a string representation of all the ratings.
     * Number in parentheses is the number of ratings received for the number of stars to the left.
     *
     * @return A string representation of the ratings.
     */
    private String generateRatingString() {
        StringBuilder ratingString = new StringBuilder("Rating: ");
        if (ratings == null) {
            ratingString.append("none");
        } else {
            int[] ratingArray = new int[MAX_STARS];
            Rating current = ratings;

            while (current != null) {
                ratingArray[current.getStar() - 1]++;
                current = current.getNext();
            }
            for (int i = 0; i < ratingArray.length; i++) {
                ratingString.append("*".repeat(i + 1)).append(String.format("(%d)", ratingArray[i]));
            }
            ratingString.append(String.format("(average rating: %.2f)", avgRatings()));
        }
        return ratingString.toString();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Album album) {
            return this.title.equalsIgnoreCase(album.title) && this.artist.equals(album.artist);
        }
        return false;
    }

    /**
     * Returns a string representation of the album.
     *
     * @return A string representation of the album.
     */
    @Override
    public String toString() {
        return String.format("[%s] Released %s [%s] [%s] %s", this.title, this.released, this.artist, this.genre, this.generateRatingString());
    }

    public static void main(String[] args) {
        Album album1 = new Album("Fearless", new Artist("Taylor Swift", new Date(1989, 12, 13)), Genre.POP, new Date(2008, 11, 8));

        System.out.println(album1);

        album1.rate(1);
        album1.rate(2);
        album1.rate(3);
        album1.rate(3);
        album1.rate(3);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(5);
        album1.rate(5);

        System.out.println(album1);
    }
}
