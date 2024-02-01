package musicdb;

public class Artist implements Comparable<Artist> {
    private final String name;
    private final Date born;

    public Artist(String name, Date born) {
        this.name = name;
        this.born = born;
    }

    @Override
    public int compareTo(Artist artist){
        if(this.name.compareTo(artist.name) != 0){
            return this.name.compareTo(artist.name);
        }
        else return this.born.compareTo(artist.born);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Artist artist){
            return this.name.equalsIgnoreCase(artist.name) && this.born.equals(artist.born);
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("%s:%s", this.name, this.born);
    }

    public Date getArtistBorn(){
        return this.born; 
    } 
}