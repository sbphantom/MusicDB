/**
 * Node for the Rating linked list.
 * Each Rating object contains a star rating and a reference to the next Rating node.
 *
 * @author Danny Onuorah
 */
package musicdb;

public class Rating {
    private int star;
    private Rating next;

    /**
     * Constructs a Rating object with the specified star rating.
     *
     * @param star The star rating for this node.
     */
    public Rating(int star) {
        this.star = star;
    }

    /**
     * Gets the star rating of this node.
     *
     * @return The star rating of this node.
     */
    public int getStar() {
        return star;
    }

    /**
     * Gets the reference to the next Rating node.
     *
     * @return The reference to the next Rating node.
     */
    public Rating getNext() {
        return next;
    }

    /**
     * Sets the star rating of this node.
     *
     * @param star The star rating to be set.
     */
    public void setStar(int star) {
        this.star = star;
    }

    /**
     * Sets the reference to the next Rating node.
     *
     * @param next The next Rating node to be set.
     */
    public void setNext(Rating next) {
        this.next = next;
    }
}
