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
        for (Wagon w : list) {
            Shunter.hookWagonOnTrainRear(freightOne, w);
        }
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
        boolean output = (boolean) method.invoke(Shunter.class, freightOne,wagon);

        assertNotNull(output);
        assertTrue(output);
    }

}
