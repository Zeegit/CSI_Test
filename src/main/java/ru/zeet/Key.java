package ru.zeet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Key {
    private String productCode;     // код товара
    private int number;             // номер цены
    private int depart;             // номер отдела

    public Key(String productCode, int number, int depart) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;

        Key key = (Key) o;

        if (getNumber() != key.getNumber()) return false;
        if (getDepart() != key.getDepart()) return false;
        return getProductCode() != null ? getProductCode().equals(key.getProductCode()) : key.getProductCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getProductCode() != null ? getProductCode().hashCode() : 0;
        result = 31 * result + getNumber();
        result = 31 * result + getDepart();
        return result;
    }
}
