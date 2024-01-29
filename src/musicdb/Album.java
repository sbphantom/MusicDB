package musicdb;

public class Album {
    private String title;
    private Artist artist;
    private Genre genre;
    private Date released;
    private Rating ratings; //a linked list of ratings

    public Album(String title, Artist artist, Genre genre, Date released) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.released = released;
    }

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
    } //add a rating to the linked list

    public double avgRatings() {
        Rating current = ratings;
        double sum = 0;
        int length = 0;
        while (current != null) {
            sum += current.getStar();
            length += 1;
            current = current.getNext();
        }
        return sum / length;

    } //compute the average ratings

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Album album){
            return this.title.equalsIgnoreCase(album.title) && this.artist.equals(album.artist);
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("[%s] Released %s [%s] [%s] %s", this.title, this.released, this.artist, this.genre, this.ratings);
    }

}