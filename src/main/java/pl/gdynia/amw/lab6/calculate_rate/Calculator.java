package pl.gdynia.amw.lab6.calculate_rate;

import pl.gdynia.amw.lab6.Currencies;
import pl.gdynia.amw.lab6.database.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    public static int decideHowToCalculate(String from, String to){

        if(from.equals(to)){
            return 1;
        }else if(from.equals(Currencies.baseCurrency)){
            return 2;
        }else if(to.equals(Currencies.baseCurrency)){
            return 3;
        }else{
            return 4;
        }
    }

    public static BigDecimal calculate(Iterable<CurrencyRate> result, String from) {
        CurrencyRate rateTo = result.iterator().next();
        if(from.equals(Currencies.baseCurrency)){
            return rateTo.getRate();
        }else{
            BigDecimal one = new BigDecimal("1");
            return one.divide(rateTo.getRate(),4, RoundingMode.CEILING);//BigDecimal.valueOf(1/rateTo.getRate().doubleValue());
        }
    }

    public static BigDecimal calculate(Iterable<CurrencyRate> result1, Iterable<CurrencyRate> result2) {
        CurrencyRate rateFrom = result1.iterator().next();
        CurrencyRate rateTo = result2.iterator().next();
        return rateTo.getRate().divide(rateFrom.getRate(),4, RoundingMode.CEILING); //BigDecimal.valueOf(rateTo.getRate().doubleValue()/rateFrom.getRate().doubleValue());
    }

    public static int count(Iterable<CurrencyRate> iterable){
        int count =0;
        for (CurrencyRate i: iterable) {
            count++;
        }
        return count;
    }
}
