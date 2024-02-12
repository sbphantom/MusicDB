/**
 * An array based data structure for storing Albums
 * Array increases in increments of fours when reaching capacity
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */

package musicdb;

public class Collection {
    public static final int NOT_FOUND = -1;
    public static final int PARTITION_SIZE = 4;

    private Album[] albums; //list of albums
    private int size; //number of albums in the list

    public Collection() {
        this.albums = new Album[PARTITION_SIZE];
    }

    /**
     * Searches for the album on the collection
     *
     * @param album target album to find
     * @return its index in the array if found, else -1
     */
    private int find(Album album) {
        for (int i = 0; i < size; i++) {
            if (albums[i].equals(album)) {
                return i;
            }
        }
        return NOT_FOUND;
    } //helper method

    /**
     * Increases the max capacity of the collection by four
     */
    private void grow() {
        Album[] newArray = new Album[size + PARTITION_SIZE];
        for (int i = 0; i < size; i++) {
            newArray[i] = albums[i];
        }
        albums = newArray;

    } //helper method to increase the capacity by 4

    /**
     * Sorts the collection based on a given parameter
     *
     * @param sortBy sorts collection by given string
     *               options are "release", "genre", and "rating"
     *               defaults to album title
     */
    private void sort(String sortBy) {
        for (int i = 1; i < size; ++i) {
            Album key = albums[i];
            int j = i - 1;

            while (j >= 0 && compareAlbums(albums[j], key, sortBy) > 0) {
                albums[j + 1] = albums[j];
                j = j - 1;
            }
            albums[j + 1] = key;
        }
    }

    /**
     * Compares two albums based on a given param
     *
     * @param a1        first album to compare to
     * @param a2        second album to compare against
     * @param compareBy what to compare by
     *                  options are "release", "genre", and "rating"
     *                  defaults to album title
     */
    private double compareAlbums(Album a1, Album a2, String compareBy) {
        return switch (compareBy) {
            case "release" -> {
                if (a1.getReleased().compareTo(a2.getReleased()) == 0) {
                    yield a1.getTitle().compareTo(a2.getTitle());
                } else {
                    yield a1.getReleased().compareTo(a2.getReleased());
                }
            }
            case "genre" -> {
                if (a1.getGenre().toString().compareTo(a2.getGenre().toString()) == 0) {
                    yield a1.getArtist().compareTo(a2.getArtist());
                } else {
                    yield a1.getGenre().toString().compareTo(a2.getGenre().toString());
                }
            }
            case "rating" -> {
                if ((a2.avgRatings() - a1.avgRatings()) == 0) {
                    yield a1.getTitle().compareTo(a2.getTitle());
                } else {
                    yield (a2.avgRatings() - a1.avgRatings());
                }

            }
            default -> a1.getTitle().compareTo(a2.getTitle());
        };
    }

    /**
     * Searches for the album in the collection
     *
     * @param album target album to find
     * @return true if the array is found, else false
     */
    public boolean contains(Album album) {
        return find(album) != NOT_FOUND;
    }

    /**
     * Adds an album to the collection
     *
     * @param album target album to find
     * @return true if successfully added, else false
     */
    public boolean add(Album album) {
        if (contains(album)) return false;
        else {
            if (size % PARTITION_SIZE == 0) grow();
            albums[size] = album;
            size++;
            return true;
        }
    } //false if the album exists

    /**
     * Removes an album from the collection
     *
     * @param album target album to remove
     * @return true if successfully removed, else false
     */
    public boolean remove(Album album) {
        if (size == 0 || !contains(album)) return false;

        int index = find(album);
        if (index == size - 1) {
            albums[index] = null;
        } else {
            for (int i = index; i < size - 1; i++) {
                albums[i] = albums[i + 1];
            }
            albums[size - 1] = null;
        }
        size--;
        return true;
    }

    /**
     * Adds a new rating to an album in the collection.
     *
     * @param album  the album rate
     * @param rating the rating given
     */
    public void rate(Album album, int rating) {
        int index = find(album);
        if (index >= 0) albums[index].rate(rating);
    }

    /**
     * Prints the collection by date
     */
    public String printByDate() {
        sort("release");
        StringBuilder sb = new StringBuilder();
        sb.append("* Collection sorted by Released Date/Title *\n");

        for (int i = 0; i < size; i++) {
            sb.append(albums[i]).append("\n");
        }
        sb.append("* end of list *");
        return sb.toString();
    }

    /**
     * Prints the collection by genre
     */
    public String printByGenre() {
        sort("genre");
        StringBuilder sb = new StringBuilder();
        sb.append("* Collection sorted by Genre/Artist *\n");

        for (int i = 0; i < size; i++) {
            sb.append(albums[i]).append("\n");
        }
        sb.append("* end of list *");
        return sb.toString();
    }

    /**
     * Prints the collection by rating
     */
    public String printByRating() {
        sort("rating");
        StringBuilder sb = new StringBuilder();
        sb.append("* Collection sorted by Rating/Title *\n");

        for (int i = 0; i < size; i++) {
            sb.append(albums[i]).append("\n");
        }
        sb.append("* end of list *");
        return sb.toString();
    }

    /**
     * Checks if the collection is empty
     *
     * @return True if collection has no albums else false
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Locates album in array based on the two options
     * TARD - Locates album given an album title, Artist name, and release date
     * TABD - Locates album given an album title, Artist name, and Artist Born-date.
     *
     * @param Title      The album title
     * @param artistName The name of the artist
     * @param date       Date of the album
     * @return target album
     */
    public Album searchAlbum(String searchOption, String Title, String artistName, Date date) {
        Album targetAlbum = new Album(null, null, null, null);
        switch (searchOption.toUpperCase()) {
            case "TARD":
                for (int i = 0; i < size; i++) {
                    if (albums[i].getArtist().getArtistName().equalsIgnoreCase(artistName) &&
                            albums[i].getTitle().equalsIgnoreCase(Title) &&
                            albums[i].getReleased().equals(date)
                    ) {
                        targetAlbum = albums[i];
                    }
                }
                return targetAlbum;
            case "TABD":

                Artist targetArtist = new Artist(artistName, date);
                for (int i = 0; i < size; i++) {
                    if (albums[i].getArtist().equals(targetArtist) &&
                            albums[i].getTitle().equalsIgnoreCase(Title)) {
                        targetAlbum = albums[i];
                        return targetAlbum;
                    }
                }
        }
        return null;
    }

    public static void test() {
        Collection collection1 = new Collection();

        Album album1 = new Album("Fearless", new Artist("Taylor Swift", new Date(1989, 12, 13)), Genre.POP, new Date(2008, 11, 8));
        Album album2 = new Album("Midnights", new Artist("Taylor Swift", new Date(1989, 12, 13)), Genre.POP, new Date(2022, 3, 25));
        Album album3 = new Album("Losing Sleep", new Artist("Chris Young", new Date(1985, 6, 12)), Genre.UNKNOWN, new Date(2017, 10, 20));

        System.out.println(collection1.add(album1));
        System.out.println(collection1.add(album1));
        System.out.println(collection1.remove(album1));
        System.out.println(collection1.remove(album1));

        System.out.println(collection1.find(album1));
        System.out.println(collection1.find(new Album("Fearless", new Artist("Taylor Swift", new Date(1989, 12, 13)), Genre.POP, new Date(2008, 11, 8))));

        System.out.println(collection1.add(album1));

        System.out.println(collection1.find(album1));
        System.out.println(collection1.find(new Album("Fearless", new Artist("Taylor Swift", new Date(1989, 12, 13)), Genre.POP, new Date(2008, 11, 8))));

        System.out.println(collection1.contains(album1));
        System.out.println(collection1.contains(new Album("Fearless", new Artist("Taylor Swift", new Date(1989, 12, 13)), Genre.POP, new Date(2008, 11, 8))));

        collection1.rate(album1, 1);
        collection1.rate((new Album("Fearless", new Artist("Taylor Swift", new Date(1989, 12, 13)), Genre.POP, new Date(2008, 11, 8))), 5);

        System.out.println(album1.avgRatings());

        collection1.add(album2);
        collection1.add(album3);

        collection1.printByDate();

        System.out.println();
        System.out.println();

        collection1.printByGenre();

        System.out.println();
        System.out.println();

        collection1.rate(album2, 4);
        collection1.rate(album3, 5);

        collection1.printByRating();

        System.out.println(collection1.albums.length);
        System.out.println(collection1.size);
    }
}
