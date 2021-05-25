package com.company;

public class Currency {
    private String currencyname;
    private Double rate;

    public Currency(String currencyname, Double rate) {
        this.currencyname = currencyname;
        this.rate = rate;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
