package musicdb;

public class Collection {
    private Album[] albums; //list of albums
    private int size; //number of albums in the list
    private int find(Album album) //helper method
    private void grow() //helper method to increase the capacity by 4
    public boolean contains(Album album)
    public boolean add(Album album) //false if the album exists
    public boolean remove(Album album) //false if the album doesn’t exist
    public void rate(Album album, int rating)
    public void printByDate() //sort by release date, then title
    public void printByGenre() //sort by genre, then artist
    public void printByRating()//sort by average rating, then title
}
