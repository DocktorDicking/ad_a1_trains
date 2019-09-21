package model;

/**
 * Wagon to transport passengers, passenger may be human.
 */
public class PassengerWagon extends Wagon {
    private int numberOfSeats;

    /**
     * Default passengerWagon constructor.
     *
     * @param wagonId       int
     * @param numberOfSeats int
     */
    public PassengerWagon(int wagonId, int numberOfSeats) {
        super(wagonId);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
