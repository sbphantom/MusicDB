package musicdb;

public class Collection {
    private Album[] albums; //list of albums
    private int size; //number of albums in the list
    private final int NOT_FOUND = -1;
    private final int PARTITION_SIZE = 4;

    public Collection() {
        this.albums = new Album[PARTITION_SIZE];
    }

    private int find(Album album) {
        for (int i = 0; i < size; i++) {
            if (albums[i].equals(album)) {
                return i;
            }
        }
        return NOT_FOUND;
    } //helper method

    private void grow() {
        Album[] newArray = new Album[size + PARTITION_SIZE];
        for (int i = 0; i < size; i++) {
            newArray[i] = albums[i];
        }
        albums = newArray;

    } //helper method to increase the capacity by 4

    public boolean contains(Album album) {
        return find(album) != NOT_FOUND;
    }

    public boolean add(Album album) {
        if (contains(album)) return false;
        else {
            if (size % PARTITION_SIZE == 0) grow();
            albums[size] = album;
            size++;
            return true;
        }
    } //false if the album exists

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

    public void rate(Album album, int rating) {
        int index = find(album);
        if (index >= 0) albums[index].rate(rating);
    }

    public void sort(Album[] arr, java.util.Comparator<Album> comparator) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            Album key = arr[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public void printByDate() {
        Album[] copyArray = new Album[size];
        for (int i = 0; i < size; i++) {
            copyArray[i] = albums[i];
        }
        java.util.Comparator<Album> sortByReleased = java.util.Comparator.comparing(Album::getReleased);
        sort(copyArray, sortByReleased);

        for (Album a : copyArray) {
            System.out.println(a);
        }
    }

    public void printByGenre() {
        Album[] copyArray = new Album[size];
        for (int i = 0; i < size; i++) {
            copyArray[i] = albums[i];
        }
        java.util.Comparator<Album> sortByGenre = java.util.Comparator.comparing(Album::getGenre);
        sort(copyArray, sortByGenre);

        for (Album a : copyArray) {
            System.out.println(a);
        }
    } //sort by genre, then artist

    public void printByRating() {
        Album[] copyArray = new Album[size];
        for (int i = 0; i < size; i++) {
            copyArray[i] = albums[i];
        }
        java.util.Comparator<Album> sortByRating = java.util.Comparator.comparing(Album::avgRatings);
        sort(copyArray, sortByRating);

        for (Album a : copyArray) {
            System.out.println(a);
        }
    }//sort by average rating, then title

    public static void main(String[] args) {
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

    }
}
