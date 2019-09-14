package model;

public class Train {
    private Locomotive engine;
    private Wagon firstWagon;
    private String destination;
    private String origin;
    private int numberOfWagons;

    //TODO: add doc
    public Train(Locomotive engine, String origin, String destination) {
        this.engine = engine;
        this.destination = destination;
        this.origin = origin;
    }

    //TODO: add doc
    public Wagon getFirstWagon() {
        return this.firstWagon;
    }

    //TODO: add doc
    public void setFirstWagon(Wagon firstWagon) {
        this.firstWagon = firstWagon;
    }

    //TODO: add doc
    public void resetNumberOfWagons() {
        this.numberOfWagons = this.firstWagon.countNextWagons(1);
    }

    //TODO: add doc
    public int getNumberOfWagons() {
        return this.numberOfWagons;
    }


    /* three helper methods that are usefull in other methods */

    public boolean hasNoWagons() {
        return (this.firstWagon == null);
    }

    //TODO: add doc
    public boolean isPassengerTrain() {
        return firstWagon instanceof PassengerWagon;
    }

    //TODO: add doc
    public boolean isFreightTrain() {
        return firstWagon instanceof FreightWagon;
    }

    //TODO: add doc
    public int getPositionOfWagon(int wagonId) {
        // find a wagon on a train by id, return the position (first wagon had position 1)
        // if not found, than return -1
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
        return -1;
    }

    //TODO: add doc
    public Wagon getWagonOnPosition(int position) throws IndexOutOfBoundsException {
        /* find the wagon on a given position on the train
         position of wagons start at 1 (firstWagon of train)
         use exceptions to handle a position that does not exist */
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


        return null; //TODO: Does not look very clean.
    }

    //TODO: add doc
    public int getNumberOfSeats() {
        /* give the total number of seats on a passenger train
         for freight trains the result should be 0 */
        int numberOfSeats = 0;
        if (this.firstWagon instanceof PassengerWagon) {
            PassengerWagon currentWagon = (PassengerWagon) this.firstWagon;
            numberOfSeats = currentWagon.getNumberOfSeats();
            while (currentWagon.hasNextWagon()) {
                numberOfSeats += currentWagon.getNumberOfSeats();
                currentWagon = (PassengerWagon) currentWagon.getNextWagon();
            }
        } else if (this.firstWagon instanceof FreightWagon) {
            return numberOfSeats;
        }
        return numberOfSeats;
    }

    //TODO: add doc
    public int getTotalMaxWeight() {
        /* give the total maximum weight of a freight train
         for passenger trains the result should be 0 */
        int maxWeigt = 0;
        if (this.firstWagon instanceof FreightWagon) {
            FreightWagon currentWagon = (FreightWagon) this.firstWagon;
            while (currentWagon.hasNextWagon()) {
                maxWeigt += currentWagon.getMaxWeight();
                currentWagon = (FreightWagon) currentWagon.getNextWagon();
            }
        } else if (this.firstWagon instanceof PassengerWagon) {
            return maxWeigt;
        }
        return maxWeigt;
    }

    public Locomotive getEngine() {
        return engine;
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
