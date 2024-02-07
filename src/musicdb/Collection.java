/**
 * An array based data structure for storing Albums
 * Array increases in increments of fours when reaching capacity
 * @author Danny Onuorah, Adeola Asimolowo
 */


package musicdb;

public class Collection {
    private Album[] albums; //list of albums
    private int size; //number of albums in the list
    public static final int NOT_FOUND = -1;
    public static final int PARTITION_SIZE = 4;

    public Collection() {
        this.albums = new Album[PARTITION_SIZE];
    }

    /**
     * Searches for the album on the collection
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
     * Searches for the album in the collection
     * @param album target album to find
     * @return true if the array is found, else false
     */
    public boolean contains(Album album) {
        return find(album) != NOT_FOUND;
    }

    /**
     * Adds an album to the collection
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
     * @param album target album to remove
     * @return true if successfully removed, else false
     */
    public boolean remove(Album album) {
        if (size == 0 || !contains(album)) return false;
        else {
            for (int i = find(album); i < size; i++) {
                albums[i] = albums[i + 1];
            }
            size--;
            return true;
        }
    } //false if the album doesn’t exist

    /**
     * Adds a new rating to an album in the collection.
     * @param album the album rate
     * @param rating the rating given
     */
    public void rate(Album album, int rating) {
        int index = find(album);
        if (index >= 0) albums[index].rate(rating);
    }

    /**
     * Compares two albums based on a given param
     * @param a1 first album to compare to
     * @param a2 second album to compare against
     * @param compareBy what to compare by
     *                  options are "release", "genre", and "rating"
     *                  defaults to album title
     */
    private int compareAlbums(Album a1, Album a2, String compareBy) {
        return switch (compareBy) {
            case "release" -> a1.getReleased().compareTo(a2.getReleased());
            case "genre" -> a1.getGenre().compareTo(a2.getGenre());
            case "rating" -> (int) (a1.avgRatings() - a2.avgRatings());
            default -> a1.getTitle().compareTo(a2.getTitle());
        };
    }

    /**
     * Sorts the collection based on a given parameter
     * @param sortBy sorts collection by given string
     *               options are "release", "genre", and "rating"
     *               defaults to album title
     */
    private void sort(String sortBy) {
        for (int i = 1; i < size; ++i) {
            Album key = albums[i];
            int j = i - 1;

            while (j >= 0 && compareAlbums(albums[j], key, sortBy) > 0){
                albums[j + 1] = albums[j];
                j = j - 1;
            }
            albums[j + 1] = key;
        }
    }

    /**
     * Prints the collection by date
     */
    public void printByDate() {
        sort("release");
        System.out.println("* Collection sorted by Released Date/Title *\n");
        for (int i = 0; i < size; i++) {
            System.out.println(albums[i]);
        }
        System.out.println("* end of list *");
    }

    /**
     * Prints the collection by genre
     */
    public void printByGenre() {
        sort("genre");
        System.out.println("* Collection sorted by Genre/Artist *\n");
        for (int i = 0; i < size; i++) {
            System.out.println(albums[i]);
        }
        System.out.println("* end of list *");
    } //sort by genre, then artist

    /**
     * Prints the collection by rating
     */
    public void printByRating() {
        sort("rating");
        System.out.println("* Collection sorted by Rating/Title * \n");
        for (int i = 0; i < size; i++) {
            System.out.println(albums[i]);
        }
        System.out.println("* end of list *");
    }//sort by average rating, then title

    /**
     * Checks if the collection is empty 
     * @return True -> collection has no albumns
     * @return False -> collection containts at least 1 album.
     */
    public boolean isEmpty(){ 
        if(this.size == 0){
            return true; 
        }
        return false; 
    }

    /**
     * Locates album in array based on the two options
     * TARD - Locates album given a album title, Artist name, and release date
     * TABD - Locates album given a album title, Artist name, and Artist Born-date.
     * @param Title The album title
     * @param artistName The name of the artist
     * @param date Date of the album
     * @returns the target album
     */    
    public Album searchAlbum(String searchOption, String Title, String artistName, Date date ){

        switch (searchOption.toUpperCase()){

            case "TARD":
                Album targetAlbum = new Album(null, null, null, null);
                for(int i = 0; i < size; i++){
                    if(albums[i].getArtist().getArtistName().equalsIgnoreCase(artistName) &&
                            albums[i].getTitle().equalsIgnoreCase(Title) &&
                            albums[i].getReleased().equals(date)
                    ){
                        targetAlbum = albums[i];
                    }
                }
                return targetAlbum;
            case "TABD":
                Album targetAlbum2 = new Album(null, null, null, null);
                Artist targetArtist = new Artist(artistName, date);
                for(int i = 0; i < size; i++){
                    if(albums[i].getArtist().equals(targetArtist) &&
                       albums[i].getTitle().equalsIgnoreCase(Title)){
                        targetAlbum2 = albums[i];
                        return targetAlbum2;
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
