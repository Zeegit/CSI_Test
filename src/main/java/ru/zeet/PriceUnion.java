package ru.zeet;


import java.util.*;

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
        Set<Key> keys = new LinkedHashSet<>();

        for (Price temp : oldPrice.getPriceList()) {
            keys.add(temp.getKey());
        }
        for (Price temp : newPrice.getPriceList()) {
            keys.add(temp.getKey());
        }

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
                if (p.getValue() != rawPriceList.get(i).getValue()) {
                    // Если конец предыдущего диапазона == началу следующего -> расширяем диапазон
                    if (p.getEnd() == rawPriceList.get(i).getBegin()) {
                        p.setEnd(rawPriceList.get(i).getBegin());
                    }
                    unionPrice.add(p);
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
    private static ArrayList<Date> getIntervals(PriceList p1, PriceList p2) {
        HashSet<Date> d = new HashSet<>();

        for (Price temp : p1.getPriceList()) {
            d.add(temp.getBegin());
            d.add(temp.getEnd());
        }
        for (Price temp : p2.getPriceList()) {
            d.add(temp.getBegin());
            d.add(temp.getEnd());
        }

        ArrayList<Date> dates = new ArrayList<>(d);
        dates.sort(Date::compareTo);

        return dates;
    }


}

