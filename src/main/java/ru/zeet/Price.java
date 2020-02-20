package ru.zeet;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Getter
@Setter
public class Price {
    //private long id;                // идентификатор в БД
    //private String productCode;     // код товара
    //private int number;             // номер цены
    //private int depart;             // номер отдела

    private Date begin;             // начало действия
    private Date end;               // конец действия
    private long value;             // значение цены в копейках

    // Для фильтрации код товара+номер цены+номер отдела
    private Key key;

    public Price(String productCode, int number, int depart, String begin, String end, int value) {
        //this.productCode = productCode;
        //this.number = number;
        //this.depart = depart;

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try { this.begin = df.parse(begin); } catch (ParseException e) { throw new IllegalArgumentException(begin+" is not valid Date"); }
        try { this.end = df.parse(end); } catch (ParseException e) { throw new IllegalArgumentException(end+" is not valid Date"); }

        this.value = value;

        this.key = new Key(productCode, number, depart);
    }

    public Price(Key k, Date dateFrom, Date dateTo, Long price) {
        this.key = k;
        //this.productCode = k.getProductCode();
        //this.number = k.getNumber();
        //this.depart = k.getDepart();
        this.begin = dateFrom;
        this.end = dateTo;
        this.value = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;
        if (!getKey().equals(price.getKey())) return false;
        //if (getNumber() != price.getNumber()) return false;
        //if (getDepart() != price.getDepart()) return false;
        if (getValue() != price.getValue()) return false;
        //if (getProductCode() != null ? !getProductCode().equals(price.getProductCode()) : price.getProductCode() != null)
        //    return false;
        if (getBegin() != null ? !getBegin().equals(price.getBegin()) : price.getBegin() != null) return false;
        return getEnd() != null ? getEnd().equals(price.getEnd()) : price.getEnd() == null;
    }

    @Override
    public int hashCode() {
        int result = getKey().hashCode(); // getProductCode() != null ? getProductCode().hashCode() : 0;
        //result = 31 * result + getNumber();
        //result = 31 * result + getDepart();
        result = 31 * result + (getBegin() != null ? getBegin().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (int) (getValue() ^ (getValue() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return "Price{" +
                "productCode='" + getProductCode() + '\'' +
                ", number=" + getNumber() +
                ", depart=" + getDepart() +
                ", begin=" + df.format(begin) +
                ", end=" + df.format(end) +
                ", value=" + value +
                '}';
    }

    public List<Date> getDates() {
        return new ArrayList<>(Arrays.asList(begin, end));
    }

    public Object getProductCode() {
        return key.getProductCode();
    }

    public Object getNumber() {
        return key.getNumber();
    }

    public Object getDepart() {
        return key.getDepart();
    }
    /*@Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return String.format("%s\t%d\t%d\t%s\t%s\t%d\n",
                getProductCode(),
                getNumber(),
                getDepart(),
                df.format(getBegin()),
                df.format(getEnd()),
                getValue());
    }*/
}
