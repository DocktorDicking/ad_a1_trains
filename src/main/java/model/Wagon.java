package model;

/**
 * Base wagon class. Abstract; cannot be instantiated directly.
 */
public abstract class Wagon {
    private int wagonId;
    private Wagon previousWagon;
    private Wagon nextWagon;

    /**
     * Default wagon constructor. Automatically called by child classes.
     *
     * @param wagonId int
     */
    public Wagon(int wagonId) {
        this.wagonId = wagonId;
    }

    /**
     * Gets the number of wagons attached to this.
     *
     * @return int
     */
    public int getNumberOfWagonsAttached() { //This method is not used yet. Train haves it's own 'get numer of wagons' method.
        int numberOfWagons = 1;
        //Counts all wagons on the right
        Wagon currentWagon = this;
        while (currentWagon.hasNextWagon()) {
            numberOfWagons++;
            currentWagon = currentWagon.getNextWagon();
        }
        //Counts all wagons on the left
        currentWagon = this;
        while (currentWagon.hasPreviousWagon()) {
            numberOfWagons++;
            currentWagon = currentWagon.getPreviousWagon();
        }
        return numberOfWagons;
    }

    /**
     * Method returns the first wagon in the row.
     * Goes to all previous wagons to get to the first wagon.
     * <p>
     * Counter is optional, give -1 when counter is not needed.
     *
     * @return Wagon
     */
    public Wagon getFirstWagon() {
        Wagon currentWagon = this;
        while (currentWagon.hasPreviousWagon()) {
            currentWagon = currentWagon.getPreviousWagon();
        }
        return currentWagon;
    }

    /**
     * Method returns the last wagon in the row.
     * Goes to all next wagons to get to the last wagon.
     * <p>
     * Counter is optional, give -1 when counter is not needed.
     *
     * @return Wagon
     */
    public Wagon getLastWagonAttached() {
        Wagon currentWagon = this;
        while (currentWagon.hasNextWagon()) {
            currentWagon = currentWagon.getNextWagon();
        }
        return currentWagon;
    }

    /**
     * Will return number of "nextWagons"
     *
     * @param initialCounter int
     * @return int
     */
    public int countNextWagons(int initialCounter) {
        int counter = initialCounter;
        Wagon currentWagon = this;
        while (currentWagon.hasNextWagon()) {
            counter++;
            currentWagon = currentWagon.getNextWagon();
        }
        return counter;
    }

    /**
     * Will return number of "previousWagons"
     *
     * @param initialCounter int
     * @return int
     */
    public int countPreviousWagons(int initialCounter) { //Not used yet.
        int counter = initialCounter;
        Wagon currentWagon = this;
        while (currentWagon.hasPreviousWagon()) {
            counter++;
            currentWagon = currentWagon.getPreviousWagon();
        }
        return counter;
    }

    public boolean hasNextWagon() {
        return !(this.nextWagon == null);
    }

    public boolean hasPreviousWagon() {
        return !(this.previousWagon == null);
    }

    public void setNextWagon(Wagon nextWagon) {
        this.nextWagon = nextWagon;
    }

    public Wagon getPreviousWagon() {
        return previousWagon;
    }

    public void setPreviousWagon(Wagon previousWagon) {
        this.previousWagon = previousWagon;
    }

    public Wagon getNextWagon() {
        return this.nextWagon;
    }

    public int getWagonId() {
        return this.wagonId;
    }

    @Override
    public String toString() {
        return String.format("[Wagon %d]", this.wagonId);
    }
}
