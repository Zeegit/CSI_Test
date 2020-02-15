package ru.zeet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceList {
    private List<Price> priceList = new ArrayList<>();

    public void add(Price price) {
        priceList.add(price);
    }

    public void addAll(List<Price> prices) {
        priceList.addAll(prices);
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public PriceList getPriceListByKey(Key k) {
        PriceList list = new PriceList();
        for (Price temp : priceList) {
            if (temp.getKey().equals(k)) {
                list.add(temp);
            }
        }
        return list;
    }

    public Long getPriceInPeriod(Key k, Date dateFrom, Date dateTo) {
        for (Price temp : priceList) {
            if (temp.getKey().equals(k) && dateInRange(temp, dateFrom, dateTo)) {
                return temp.getValue();
            }
        }
        return null;
    }

    private boolean dateInRange(Price temp, Date dateFrom, Date dateTo) {
        return (temp.getBegin().equals(dateFrom) || temp.getBegin().before(dateFrom)) && (temp.getEnd().equals(dateTo) || temp.getEnd().after(dateTo));
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        StringBuilder str = new StringBuilder();

        for (Price temp : priceList) {
            str.append(temp.toString());
            str.append("\n");
        }
        return str.toString();
    }
}
