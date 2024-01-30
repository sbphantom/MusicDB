package musicdb;

public class Album {
    private String title;
    private Artist artist;
    private Genre genre;
    private Date released;

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public Date getReleased() {
        return released;
    }

    public Rating getRatings() {
        return ratings;
    }

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

    public String generateRatingString(){
        StringBuilder ratingString = new StringBuilder("Rating: ");
        if (ratings == null){
            ratingString.append("none");
        }
        else{
            int[] ratingArray = new int[5];
            Rating current = ratings;

            while (current != null){
                ratingArray[current.getStar()-1]++;
                current = current.getNext();
            }
            for (int i = 0; i < ratingArray.length; i++){
                if (ratingArray[i] > 0){
                    ratingString.append("*".repeat(i + 1)).append(String.format("(%d)", ratingArray[i]));
                }
            }
            ratingString.append(String.format("(average rating: %.2f)", avgRatings()));
        }
        return ratingString.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Album album){
            return this.title.equalsIgnoreCase(album.title) && this.artist.equals(album.artist);
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("[%s] Released %s [%s] [%s] %s", this.title, this.released, this.artist, this.genre, this.generateRatingString());
    }

    public static void main(String[] args){
        Album album1 = new Album("Fearless", new Artist("Taylor Swift", new Date(1989, 12, 13)),Genre.POP, new Date(2008, 11, 8));

        System.out.println(album1);

        album1.rate(1);
        album1.rate(1);
        album1.rate(2);
        album1.rate(2);
        album1.rate(2);
        album1.rate(2);
        album1.rate(3);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(4);
        album1.rate(5);
        album1.rate(5);
        album1.rate(5);
        album1.rate(5);
        album1.rate(5);

        System.out.println(album1);
    }
}