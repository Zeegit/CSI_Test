package ru.zeet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Price> oldPriceList = new ArrayList<>();
        oldPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "31.01.2013 23:59:59", 11000));
        oldPriceList.add(new Price("122856", 2, 1, "10.01.2013 00:00:00", "20.01.2013 23:59:59", 99000));
        oldPriceList.add(new Price("6654", 1, 2, "01.01.2013 00:00:00", "31.01.2013 00:00:00", 5000));

        List<Price> newPriceList = new ArrayList<>();
        newPriceList.add(new Price("122856", 1, 1, "20.01.2013 00:00:00", "20.02.2013 23:59:59", 11000));
        newPriceList.add(new Price("122856", 2, 1, "15.01.2013 00:00:00", "25.01.2013 23:59:59", 92000));
        newPriceList.add(new Price("6654", 1, 2, "12.01.2013 00:00:00", "13.01.2013 00:00:00", 4000));

        List<Price> unionPriceList = PriceUnion.unionList(oldPriceList, newPriceList);

     /*   List<Price> oldPriceList = new ArrayList<>();
        oldPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "10.01.2013 00:00:00", 10000));

        List<Price> newPriceList = new ArrayList<>();
        newPriceList.add(new Price("122856", 1, 1, "20.01.2013 00:00:00", "01.02.2013 00:00:00", 10000));

        List<Price> unionPriceList = PriceUnion.unionList(oldPriceList, newPriceList);

        List<Price> goodPriceList = new ArrayList<>();
        goodPriceList.add(new Price("122856", 1, 1, "01.01.2013 00:00:00", "10.01.2013 00:00:00", 10000));
        goodPriceList.add(new Price("122856", 1, 1, "20.01.2013 00:00:00", "01.02.2013 00:00:00", 10000));
*/

        // В виде таблицы
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        StringBuilder str = new StringBuilder();

        for (Price temp : unionPriceList) {
            str.append(String.format("%s\t%d\t%d\t%s\t%s\t%d\n",
                    temp.getProductCode(),
                    temp.getNumber(),
                    temp.getDepart(),
                    df.format(temp.getBegin()),
                    df.format(temp.getEnd()),
                    temp.getValue()));
        }
        System.out.println(str.toString());
    }
}
