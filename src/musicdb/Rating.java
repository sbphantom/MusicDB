package musicdb;

public class Rating {
    private int star;
    private Rating next;

    public Rating(int star) {
        this.star = star;
    }

    public int getStar() {
        return star;
    }

    public Rating getNext() {
        return next;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public void setNext(Rating next) {
        this.next = next;
    }
}
