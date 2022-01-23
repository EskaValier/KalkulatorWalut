package pl.gdynia.amw.lab6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.gdynia.amw.lab6.calculate_rate.Calculator;
import pl.gdynia.amw.lab6.calculate_rate.Validator;
import pl.gdynia.amw.lab6.database.*;

import java.time.LocalDate;

@Controller // This means that this class is a Controller
@RequestMapping(path="/service/currency") // This means URL's start with /service (after Application path)
public class MainController {

    private CurrencyRateRepository currencyRateRepository;
    @Autowired // This means to get the bean called currencyRateRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    public void wireCurrencyRateRepository(CurrencyRateRepository currencyRateRepository){
        this.currencyRateRepository = currencyRateRepository;
    }

    @GetMapping(path="/exchange-rate") // Map ONLY POST Requests
    public @ResponseBody String searchRateForCurrencyAndDate (
            @RequestParam String currencyFrom,
            @RequestParam String currencyTo,
            @RequestParam String date) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return countRate(currencyFrom, currencyTo, date);
    }

    @GetMapping(path="/test")
    public @ResponseBody Iterable<CurrencyRate> getTestCurrencyRate() {
        // Test bd query
        String data = "2021-06-04", currency = "PLN";
        return currencyRateRepository.findByDateAndCurrTo(LocalDate.parse(data),currency);
    }

    private String countRate(String currencyFrom, String currencyTo, String date){
        Validator.validateParameters(currencyFrom, currencyTo, date);
        LocalDate localDate = LocalDate.parse(date);

        // search if there is no rate for date, get day before
        boolean run = true;
        while(run){
            Iterable<CurrencyRate> checkResultFromDB = currencyRateRepository.findByDate(localDate);

            if(Calculator.count(checkResultFromDB)==0){//iterable jest pusty
                localDate = localDate.minusDays(1);
            }else{
                run = false;
            }
        }

        int status = Calculator.decideHowToCalculate(currencyFrom, currencyTo);

        Iterable<CurrencyRate> result1;
        Iterable<CurrencyRate> result2;
        switch (status){
            case 1:
                return "1";
            case 2:
                result1 = currencyRateRepository.findByDateAndCurrTo(localDate,currencyTo);
                return Calculator.calculate(result1,currencyFrom).toString();
            case 3:
                result2 = currencyRateRepository.findByDateAndCurrTo(localDate,currencyFrom);
                return Calculator.calculate(result2,currencyFrom).toString();
            case 4:
                result1 = currencyRateRepository.findByDateAndCurrTo(localDate,currencyFrom);
                result2 = currencyRateRepository.findByDateAndCurrTo(localDate,currencyTo);
                return Calculator.calculate(result1,result2).toString();
            default:
                return "0";
        }
    }
}