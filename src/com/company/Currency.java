package com.company;

public class Currency {
    private String currencyName;
    private Double rate;

    public Currency(String currencyName, Double rate) {
        this.currencyName = currencyName;
        this.rate = rate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
