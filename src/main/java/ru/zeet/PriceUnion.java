package ru.zeet;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Правила объединения цен:
• если товар еще не имеет цен, или имеющиеся цены не пересекаются в периодах действия с новыми,
    то новые цены просто добавляются к товару;
• если имеющаяся цена пересекается в периоде действия с новой ценой, то:
    • если значения цен одинаковы, период действия имеющейся цены увеличивается согласно периоду новой цены;
    • если значения цен отличаются, добавляется новая цена, а период действия старой цены уменьшается согласно периоду новой цены.
 */
public class PriceUnion {

    public static List<Price> unionList(List<Price> oldPrice, List<Price> newPrice) {
        PriceList oldPriceList = new PriceList();
        oldPriceList.addAll(oldPrice);

        PriceList newPriceList = new PriceList();
        newPriceList.addAll(newPrice);

        return union(oldPriceList, newPriceList).getPriceList();
    }

    /**
     * 1. Разбивка на все возможные иннтервалы
     * 2. Поиск сначала новой, затем строй цены в интервале
     * 3. Объеденение интервалов, если цены в интервалах равны и окончание первого == начало второго
     */
    public static PriceList union(PriceList oldPrice, PriceList newPrice) {
        PriceList unionPrice = new PriceList();

        // Список уникальных код товара-номер цены-номер отдела
        Set<Key> keys = Stream.of(oldPrice.getPriceList(), newPrice.getPriceList())
                .flatMap(Collection::stream)
                .map(Price::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<Price> rawPriceList = new ArrayList<>();

        // По всем уникальным ключам
        for (Key k : keys) {

            rawPriceList.clear();
            // Отсортированные интервалы
            List<Date> listDates = getIntervals(oldPrice.getPriceListByKey(k), newPrice.getPriceListByKey(k));

            for (int i = 0; i < listDates.size() - 1; i++) {
                Date dateFrom = listDates.get(i);
                Date dateTo = listDates.get(i + 1);

                Long price = newPrice.getPriceInPeriod(k, dateFrom, dateTo);
                if (price == null) {
                    price = oldPrice.getPriceInPeriod(k, dateFrom, dateTo);
                }

                if (price != null) {
                    rawPriceList.add(new Price(k, dateFrom, dateTo, price));
                }
            }

            // Объединение
            Price p = rawPriceList.get(0);
            for (int i = 1; i < rawPriceList.size(); i++) {
                if (p.getValue() != rawPriceList.get(i).getValue()) {               // Поменялась цена
                    unionPrice.add(p);
                    p = rawPriceList.get(i);
                } else if (p.getEnd().equals(rawPriceList.get(i).getBegin())) {     // Конец одного интервала == начало следующего
                    p.setEnd(rawPriceList.get(i).getEnd());                         // Расширение интервала
                } else {                                                            // Цена не изменилась, но даты не совпадают
                    unionPrice.add(p);                                              // Новый интервал
                    p = rawPriceList.get(i);
                }
            }
            // Последний элемент диапазона
            p.setEnd(rawPriceList.get(rawPriceList.size() - 1).getEnd());
            unionPrice.add(p);
        }
        return unionPrice;
    }

    /**
     * Список всех дат из прайсов (по ключу)
     */
    private static List<Date> getIntervals(PriceList p1, PriceList p2) {
        List<Date> dates = Stream.of(p1.getPriceList(), p2.getPriceList())
                .flatMap(Collection::stream)
                .map(Price::getDates)
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
        return dates;
    }


}

