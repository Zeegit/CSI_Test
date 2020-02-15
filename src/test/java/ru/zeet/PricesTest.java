package ru.zeet;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricesTest {

    @Test
    public void dataFromTest() {
        List<Price> oldPriceList = new ArrayList<>();
        oldPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "31.01.2013 23:59:59", 11000));
        oldPriceList.add(new Price("122856", 2, 1, "10.01.2013 00:00:00", "20.01.2013 23:59:59", 99000));
        oldPriceList.add(new Price("6654", 1, 2, "01.01.2013 00:00:00", "31.01.2013 00:00:00", 5000));

        List<Price> newPriceList = new ArrayList<>();
        newPriceList.add(new Price("122856", 1, 1, "20.01.2013 00:00:00", "20.02.2013 23:59:59", 11000));
        newPriceList.add(new Price("122856", 2, 1, "15.01.2013 00:00:00", "25.01.2013 23:59:59", 92000));
        newPriceList.add(new Price("6654", 1, 2, "12.01.2013 00:00:00", "13.01.2013 00:00:00", 4000));

        List<Price> unionPriceList = PriceUnion.unionList(oldPriceList, newPriceList);

        List<Price> goodPriceList = new ArrayList<>();
        goodPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "20.02.2013 23:59:59", 11000));
        goodPriceList.add(new Price("122856", 2, 1, "10.01.2013 00:00:00", "15.01.2013 00:00:00", 99000));
        goodPriceList.add(new Price("122856", 2, 1, "15.01.2013 00:00:00", "25.01.2013 23:59:59", 92000));
        goodPriceList.add(new Price("6654", 1, 2, "01.01.2013 00:00:00", "12.01.2013 00:00:00", 5000));
        goodPriceList.add(new Price("6654", 1, 2, "12.01.2013 00:00:00", "13.01.2013 00:00:00", 4000));
        goodPriceList.add(new Price("6654", 1, 2, "13.01.2013 00:00:00", "31.01.2013 00:00:00", 5000));

        assertEquals(unionPriceList, goodPriceList);
    }

    @Test
    public void addNo() {
        // Без добавления нового
        List<Price> oldPriceList = new ArrayList<>();
        oldPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "31.01.2013 23:59:59", 11000));

        List<Price> newPriceList = new ArrayList<>();

        List<Price> unionPriceList = PriceUnion.unionList(oldPriceList, newPriceList);

        List<Price> goodPriceList = new ArrayList<>();
        goodPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "31.01.2013 23:59:59", 11000));
        assertEquals(unionPriceList, goodPriceList);
    }

    @Test
    public void twoInOne() {
        // Старый и новый объеденияются в один
        List<Price> oldPriceList = new ArrayList<>();
        oldPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "31.01.2013 23:59:59", 11000));

        List<Price> newPriceList = new ArrayList<>();
        newPriceList.add(new Price("122856", 1, 1, "31.01.2013 23:59:59", "28.02.2013 23:59:59", 11000));

        List<Price> unionPriceList = PriceUnion.unionList(oldPriceList, newPriceList);

        List<Price> goodPriceList = new ArrayList<>();
        goodPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "28.02.2013 23:59:59", 11000));

        assertEquals(unionPriceList, goodPriceList);
    }

    @Test
    public void sample1() {
        List<Price> oldPriceList = new ArrayList<>();
        oldPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "31.01.2013 23:59:59", 5000));

        List<Price> newPriceList = new ArrayList<>();
        newPriceList.add(new Price("122856", 1, 1, "10.01.2013 23:59:59", "15.01.2013 23:59:59", 6000));

        List<Price> unionPriceList = PriceUnion.unionList(oldPriceList, newPriceList);

        List<Price> goodPriceList = new ArrayList<>();
        goodPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "10.01.2013 23:59:59", 5000));
        goodPriceList.add(new Price("122856", 1, 1, "10.01.2013 23:59:59", "15.01.2013 23:59:59", 6000));
        goodPriceList.add(new Price("122856", 1, 1, "15.01.2013 23:59:59", "31.01.2013 23:59:59", 5000));

        assertEquals(unionPriceList, goodPriceList);
    }

    @Test
    public void sample2() {
        List<Price> oldPriceList = new ArrayList<>();
        oldPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "15.01.2013 00:00:00", 10000));
        oldPriceList.add(new Price("122856", 1, 1, "15.01.2013 00:00:00", "01.02.2013 00:00:00", 12000));

        List<Price> newPriceList = new ArrayList<>();
        newPriceList.add(new Price("122856", 1, 1, "10.01.2013 00:00:00", "20.01.2013 00:00:00", 11000));

        List<Price> unionPriceList = PriceUnion.unionList(oldPriceList, newPriceList);

        List<Price> goodPriceList = new ArrayList<>();
        goodPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "10.01.2013 00:00:00", 10000));
        goodPriceList.add(new Price("122856", 1, 1, "10.01.2013 00:00:00", "15.01.2013 00:00:00", 11000));
        goodPriceList.add(new Price("122856", 1, 1, "20.01.2013 00:00:00", "01.02.2013 00:00:00", 12000));

        assertEquals(unionPriceList, goodPriceList);
    }
}