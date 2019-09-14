package model;

public class Shunter {
    private static final int falsy = -1;

    /**
     * Checks if wagon can be attached to the train. The wagon can only be attached tot the train if all wagons
     * currently attached are the same type of wagon.
     * <p>
     * If the train does not have any wagons attached the method will return true.
     *
     * @param train Train
     * @param wagon Wagon
     * @return Boolean
     */
    private static boolean isSuitableWagon(Train train, Wagon wagon) {
        if (train != null && wagon != null) {
            if (train.hasNoWagons()) {
                return true;
            } else {
                Wagon currentWagon = train.getFirstWagon();
                do {
                    if (!(currentWagon.getClass() == wagon.getClass())) {
                        return false;
                    }
                    currentWagon = currentWagon.hasNextWagon() ? currentWagon.getNextWagon() : currentWagon;
                } while (currentWagon.hasNextWagon());
            }
        }
        return true;
    }

    private static boolean isSuitableWagon(Wagon one, Wagon two) {
        return (one.getClass() == two.getClass());
    }

    /**
     * The engine of a train has a maximum capacity. This method checks if there is space for one or more wagons.
     *
     * @param train Train
     * @param wagon Wagon
     * @return boolean
     */
    private static boolean hasPlaceForWagons(Train train, Wagon wagon) {
        if (train != null && wagon != null) {
            //Count al left wagons.
            Wagon currentWagon = wagon;
            int numberOfWagons = 1;
            while (currentWagon.hasPreviousWagon()) {
                currentWagon = currentWagon.getPreviousWagon();
                numberOfWagons++;
            }
            //Count all right wagons.
            currentWagon = wagon;
            while (currentWagon.hasNextWagon()) {
                currentWagon = currentWagon.getNextWagon();
                numberOfWagons++;
            }
            int requiredWagonSpace = numberOfWagons + train.getNumberOfWagons();
            return (requiredWagonSpace <= train.getEngine().getMaxWagons());
        }
        return false;
    }

    //TODO: This method is obsolete since the implementation above will return true if the needed space is available.
//    private static boolean hasPlaceForOneWagon(Train train, Wagon wagon) {
//        // the engine of a train has a maximum capacity, this method checks for one wagon
//        return true;
//    }

    /**
     * Checks if train can support new number of wagons and if wagons are the same type as the already linked wagons.
     * If all is true the new wagon(s) will be attached to the rear of the train.
     *
     * @param train Train
     * @param wagon Wagon
     * @return Boolean
     */
    public static boolean hookWagonOnTrainRear(Train train, Wagon wagon) {
        if (hasPlaceForWagons(train, wagon) && isSuitableWagon(train, wagon)) {
            if (train.hasNoWagons()) {
                train.setFirstWagon(wagon);
            } else {
                Wagon currentWagon = train.getFirstWagon().getLastWagon();
                currentWagon.setNextWagon(wagon);
                Wagon nextWagon = currentWagon.getNextWagon();
                nextWagon.setPreviousWagon(currentWagon);
                train.setFirstWagon(nextWagon.getFirstWagon());
            }
            train.resetNumberOfWagons();
            return true;
        }
        return false;
    }

    /**
     * Checks if train can support new number of wagons and if wagons are the same type as the already linked wagons.
     * If all is true the new wagon(s) will be attached to the front of the train.
     *
     * @param train Train
     * @param wagon Wagon
     * @return Boolean
     */
    public static boolean hookWagonOnTrainFront(Train train, Wagon wagon) {
        if (hasPlaceForWagons(train, wagon) && isSuitableWagon(train, wagon)) {
            if (train.hasNoWagons()) {
                train.setFirstWagon(wagon);
            } else {
                Wagon oldFirstWagon = train.getFirstWagon();
                train.setFirstWagon(wagon);
                train.getFirstWagon().setNextWagon(oldFirstWagon);
            }
            train.resetNumberOfWagons();
            return true;
        }
        return false;
    }

    /**
     * If wagons are compatible method will hook second wagon parameter to first wagon parameter.
     *
     * @param first  Wagon
     * @param second Wagon
     * @return Boolean
     */
    public static boolean hookWagonOnWagon(Wagon first, Wagon second) {
        if (isSuitableWagon(first, second)) {
            first.setNextWagon(second);
            return true;
        }
        return false;
    }

    /**
     * Checks if wagon is on train. Removes wagon from train and connects previous wagon and next wagon with
     * each other.
     *
     * @param train Train
     * @param wagon Wagon
     * @return Boolean
     */
    public static boolean detachAllFromTrain(Train train, Wagon wagon) {
        if (train.hasNoWagons() || wagon == null) {
            return false;
        }
        if (train.getPositionOfWagon(wagon.getWagonId()) != falsy) {
            if (train.getPositionOfWagon(wagon.getWagonId()) == 1) {
                train.setFirstWagon(null);
                return true;
            }
            //Get wagon on position x and remove reference to next wagon.
            Wagon targetWagon = train.getWagonOnPosition(train.getPositionOfWagon(wagon.getWagonId())).getPreviousWagon();
            targetWagon.setNextWagon(null);
            train.resetNumberOfWagons();
            return true;
        }
        return false;
    }

    /**
     * Checks if wagon is on train. Removes wagon from train and connects previous wagon and next wagon with
     * each other.
     *
     * @param train Train
     * @param wagon Wagon
     * @return Boolean
     */
    public static boolean detachOneWagon(Train train, Wagon wagon) {
        if (train == null || train.hasNoWagons() || wagon == null) {
            return false;
        }
        if (train.getPositionOfWagon(wagon.getWagonId()) != falsy) {
            Wagon targetWagon = train.getWagonOnPosition(train.getPositionOfWagon(wagon.getWagonId()));
            Wagon targetNext = targetWagon.getNextWagon();
            Wagon targetPrevious = targetWagon.getPreviousWagon();
            if (targetPrevious != null && targetNext != null) {
                targetPrevious.setNextWagon(targetNext);
                targetNext.setPreviousWagon(targetPrevious);
                train.resetNumberOfWagons();
                return true;
            }
            if (targetPrevious == null && targetNext != null) {
                train.setFirstWagon(targetNext);
                train.resetNumberOfWagons();
                return true;
            }
            if (targetPrevious != null && targetNext == null) {
                targetNext.setPreviousWagon(targetPrevious);
                train.resetNumberOfWagons();
                return true;
            }
        }
        return false;

    }

    /**
     * Moves wagons from "from" train to "to" train.
     * Moves given wagon, and all next wagons linked to it, to "to" train.
     *
     * @param from  Train
     * @param to    Train
     * @param wagon Wagon
     * @return Boolean
     */
    public static boolean moveAllFromTrain(Train from, Train to, Wagon wagon) {
        if (from != null && to != null && wagon != null) {
            if (from.getPositionOfWagon(wagon.getWagonId()) != falsy) {
                if (to.getFirstWagon().getClass() == wagon.getClass()) { //Check if wagons are same type of wagon
                    int wagonCounter = wagon.countNextWagons(1);
                    int totalWagons = to.getNumberOfWagons() + wagonCounter;

                    if (totalWagons <= to.getEngine().getMaxWagons()) {
                        Wagon targetWagon = to.getFirstWagon().getLastWagon();
                        targetWagon.setNextWagon(wagon);
                        to.resetNumberOfWagons();
                        return detachAllFromTrain(from, wagon);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Removes wagon from train "from" and attaches it to train "to".
     * Will return false if train/wagon does not meet the requirements.
     *
     * @param from  Train
     * @param to    Train
     * @param wagon Wagon
     * @return Boolean
     */
    public static boolean moveOneWagon(Train from, Train to, Wagon wagon) {
        final int oneWagon = 1;
        if (from != null && to != null && wagon != null) {
            if (from.getFirstWagon().getClass() == wagon.getClass()) {
                if (from.getPositionOfWagon(wagon.getWagonId()) != falsy) {
                    int counter = wagon.countNextWagons(1);
                    if (counter > oneWagon) {
                        detachOneWagon(from, wagon);
                        counter = oneWagon;
                    }

                    int totalWagons = counter + to.getNumberOfWagons();
                    if (totalWagons <= to.getEngine().getMaxWagons()) {
                        wagon.setPreviousWagon(null);
                        wagon.setNextWagon(null);
                        if (to.hasNoWagons()) {
                            to.setFirstWagon(wagon);
                            to.resetNumberOfWagons();
                            return detachOneWagon(from, wagon);
                        }
                        to.getFirstWagon().getLastWagon().setNextWagon(wagon);
                        to.resetNumberOfWagons();
                        return detachOneWagon(from, wagon);
                    }
                }
            }
        }
        return false;
    }
}
