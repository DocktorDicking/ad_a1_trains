import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CustomTest {

    private ArrayList<FreightWagon> list;
    private Train freightOne;
    private Train freightTwo;

    @BeforeEach
    private void makeListOfPassengerWagons() {
        Random random = new Random();
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int weight = 150;
            list.add(new FreightWagon(i, weight));
        }
        makeTrains();
    }

    private void makeTrains() {
        Locomotive trainOne = new Locomotive(2453, 11);

        freightOne = new Train(trainOne, "Orgrimmar", "Thunderbluff");
        freightTwo = new Train(trainOne, "Undercity", "Orgrimmar");
        for (Wagon w : list) {
            Shunter.hookWagonOnTrainRear(freightOne, w);
        }
        FreightWagon wagon = new FreightWagon(100,100);
        Shunter.hookWagonOnTrainRear(freightTwo, wagon);
    }

    @Test
    public void getTotalMaxWeight() {
        int maxWeight = (10 * 150);
        assertEquals(maxWeight, freightOne.getTotalMaxWeight());
    }

    @Test
    public void isSuitableWagon() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Using reflection to gain access to private method outside of its class.
        Method method = Shunter.class.getDeclaredMethod("isSuitableWagon", Train.class, Wagon.class);
        method.setAccessible(true);
        FreightWagon wagon = new FreightWagon(22, 22);
        PassengerWagon passengerWagon =  new PassengerWagon(22,22);

        boolean output = (boolean) method.invoke(Shunter.class, freightOne,wagon);
        boolean outputP = (boolean) method.invoke(Shunter.class, freightOne, passengerWagon);

        assertTrue(output);
        assertFalse(outputP);
    }

    @Test
    public void hasPlaceForWagons() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Using reflection to gain access to private method outside of its class.
        Method method = Shunter.class.getDeclaredMethod("hasPlaceForWagons", Train.class, Wagon.class);
        method.setAccessible(true);
        FreightWagon freightWagon = new FreightWagon(22, 22);
        PassengerWagon passengerWagon =  new PassengerWagon(22,22);

        boolean output = (boolean) method.invoke(Shunter.class, freightOne,freightWagon);
        boolean output2 = (boolean) method.invoke(Shunter.class, freightTwo,freightWagon);
        assertTrue(output);
        assertTrue(output2);
    }





}
