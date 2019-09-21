package model;

import java.util.Iterator;

import static model.Shunter.FALSY;

/**
 * Choo Choo ~!
 */
public class Train implements Iterable<Wagon> {
    private Locomotive engine;
    private Wagon firstWagon;
    private String destination;
    private String origin;
    private int numberOfWagons;

    /**
     * Iterator implementation. Used to 'iterate' the wagons attached to this train.
     *
     * @return Wagon
     */
    @Override
    public Iterator<Wagon> iterator() {
        return new Iterator<Wagon>() {
            Wagon currentWagon = firstWagon;

            @Override
            public boolean hasNext() {
                //Checks if there is a next element.
                return currentWagon.hasNextWagon();
            }

            @Override
            public Wagon next() {
                //Returns next element.
                return currentWagon = currentWagon.getNextWagon();
            }
        };
    }

    /**
     * Default train constructor
     *
     * @param engine      Locomotive
     * @param origin      String
     * @param destination String
     */
    public Train(Locomotive engine, String origin, String destination) {
        this.engine = engine;
        this.destination = destination;
        this.origin = origin;
    }

    /**
     * Counts all attached wagons and saves result in this.
     */
    public void resetNumberOfWagons() {
        int counter = 1;
        for (Iterator<Wagon> it = iterator(); it.hasNext(); ) {
            it.next();
            counter++;
        }
        this.numberOfWagons = counter;
    }

    /**
     * Returns position of a wagon.
     * Will return -1 if wagon cannot be found.
     *
     * @param wagonId int
     * @return int
     */
    public int getPositionOfWagon(int wagonId) {
        Wagon currentWagon;
        int currentPosition = 0;
        if (!this.hasNoWagons()) {
            //Check first wagon
            currentWagon = this.firstWagon;
            currentPosition = 1;
            if (currentWagon.getWagonId() == wagonId) {
                return currentPosition;
            }
            //Check remaining wagons, if they exist
            while (currentWagon.hasNextWagon()) {
                currentWagon = currentWagon.getNextWagon();
                currentPosition++;
                if (currentWagon.getWagonId() == wagonId) {
                    return currentPosition;
                }
            }
        }
        return FALSY;
    }

    /**
     * Returns wagon on position x.
     * Can be used with 'getPositionOfWagon'.
     *
     * @param position int
     * @return Wagon
     * @throws IndexOutOfBoundsException Exception
     */
    public Wagon getWagonOnPosition(int position) throws IndexOutOfBoundsException {
        Wagon currentWagon;
        int currentPosition = 0;
        if (!this.hasNoWagons()) {
            //Check if asked position is the first wagon
            currentWagon = this.firstWagon;
            currentPosition = 1;
            if (currentPosition == position) {
                return currentWagon;
            }
            //Check if one of the remainder wagons equals the asked position.
            while (currentWagon.hasNextWagon()) {
                currentWagon = currentWagon.getNextWagon();
                currentPosition++;
                if (currentPosition == position) {
                    return currentWagon;
                }
                if (!currentWagon.hasNextWagon()) {
                    throw new IndexOutOfBoundsException("No wagon found on position: " + position);
                }
            }
        } else {
            throw new IndexOutOfBoundsException("No wagons on train.");
        }
        return null; //Code never comes here, compiler needs a return here.
    }

    /**
     * Counts number of seats on the whole train.
     * Sums all seats of al wagons.
     *
     * Returns 0 when wagon is not passenger wagon.
     *
     * @return int
     */
    public int getNumberOfSeats() {
        int numberOfSeats = 0;
        if (this.firstWagon instanceof PassengerWagon) {
            //Need to cast wagon to a passengerWagon to be able to use its methods.
            PassengerWagon currentWagon = (PassengerWagon) this.firstWagon;
            numberOfSeats = currentWagon.getNumberOfSeats();

            //There are more ways to implement loop for iterator. This is one of them.
            for (Wagon wagon : this) {
                numberOfSeats += currentWagon.getNumberOfSeats();
                currentWagon = (PassengerWagon) wagon;
            }
        } else if (this.firstWagon instanceof FreightWagon) {
            return numberOfSeats;
        }
        return numberOfSeats;
    }

    /**
     * Returns the total weight of a freighter train.
     * Sums max weight of all wagons.
     * <p>
     * Returns 0 when train is passenger train.
     *
     * @return int
     */
    public int getTotalMaxWeight() {
        int maxWeigt = 0;
        if (this.firstWagon instanceof FreightWagon) {
            FreightWagon currentWagon = (FreightWagon) this.firstWagon;
            maxWeigt = currentWagon.getMaxWeight();
            while (currentWagon.hasNextWagon()) {
                currentWagon = (FreightWagon) currentWagon.getNextWagon();
                maxWeigt += currentWagon.getMaxWeight();
            }
        } else if (this.firstWagon instanceof PassengerWagon) {
            return maxWeigt;
        }
        return maxWeigt;
    }

    public Locomotive getEngine() {
        return engine;
    }

    public Wagon getFirstWagon() {
        return this.firstWagon;
    }

    public void setFirstWagon(Wagon firstWagon) {
        this.firstWagon = firstWagon;
    }

    public int getNumberOfWagons() {
        return this.numberOfWagons;
    }

    public boolean hasNoWagons() {
        return (this.firstWagon == null);
    }

    //Used 'getClass()' to check class.
    public boolean isPassengerTrain() {
        return firstWagon instanceof PassengerWagon;
    }

    //Used 'getClass()' to check class.
    public boolean isFreightTrain() {
        return firstWagon instanceof FreightWagon;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(engine.toString());
        Wagon next = this.getFirstWagon();
        while (next != null) {
            result.append(next.toString());
            next = next.getNextWagon();
        }
        result.append(String.format(" with %d wagons and %d seats from %s to %s", numberOfWagons, getNumberOfSeats(), origin, destination));
        return result.toString();
    }
}
