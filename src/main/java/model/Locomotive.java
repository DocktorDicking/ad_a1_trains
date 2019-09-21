package model;

/**
 * Engine class for train.
 */
public class Locomotive {
    private int locNumber;
    private int maxWagons;

    /**
     * Default constructor.
     *
     * @param locNumber int
     * @param maxWagons int
     */
    public Locomotive(int locNumber, int maxWagons) {
        this.locNumber = locNumber;
        this.maxWagons = maxWagons;
    }

    public int getMaxWagons() {
        return this.maxWagons;
    }

    @Override
    public String toString() {
        return String.format("{Loc %d}", locNumber);
    }
}
