package com.felicite.SGAE30.enums;

import lombok.Getter;

@Getter
public enum TypePermit {
    A(100000.0),
    B(250000.0),
    C(350000.0),
    D(450000.0);

    private final Double defaultPrice;

    TypePermit(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }
}
