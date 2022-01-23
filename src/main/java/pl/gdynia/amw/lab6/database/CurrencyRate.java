package pl.gdynia.amw.lab6.database;

import pl.gdynia.amw.lab6.Currencies;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity // This tells Hibernate to make a table out of this class
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private BigDecimal rate;
    private String currencyFrom;
    private String currencyTo;
    private LocalDate date;

    public CurrencyRate() {
        currencyFrom = Currencies.baseCurrency;
    }

    public CurrencyRate(Double rate, String currencyTo, LocalDate date) {
        this.rate = BigDecimal.valueOf(rate);
        currencyFrom = Currencies.baseCurrency;
        this.currencyTo = currencyTo;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }
    public BigDecimal getRate() {
        return rate;
    }
    public String getCurrencyFrom() {
        return currencyFrom;
    }
    public String getCurrencyTo() {
        return currencyTo;
    }
    public LocalDate getData() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setRate(double rate) {
        this.rate = BigDecimal.valueOf(rate);
    }
    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }
    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }
    public void setData(LocalDate data) {
        this.date = data;
    }
}