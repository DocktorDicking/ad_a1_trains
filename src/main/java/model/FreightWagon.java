package model;

/**
 * Wagon to transport cargo/goods
 */
public class FreightWagon extends Wagon {
    private int maxWeight;

    /**
     * Default constructor for freight wagon.
     *
     * @param wagonId   int
     * @param maxWeight int
     */
    public FreightWagon(int wagonId, int maxWeight) {
        super(wagonId);
        this.maxWeight = maxWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }
}
