package pl.gdynia.amw.lab6.load_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import pl.gdynia.amw.lab6.calculate_rate.Calculator;
import pl.gdynia.amw.lab6.database.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class XMLParseApp{
    @Autowired
    private CurrencyRateRepository currencyRateRepository;
    private ParseXML xmlService;

    public XMLParseApp(ParseXML xmlService) {
        this.xmlService = xmlService;
    }

    // cron = "sec min hours dayOfMonth month dayOfWeek"
    // ? -> any, "-" -> from-to, * -> all
    @Scheduled(cron = "00 00 17-23 ? * MON-FRI") //weekday at 17:00 and every hour until 24
    public void updateBD(){

        // search dates from xml
        xmlService.parseDates();
        List<String> dates = xmlService.getDates();

        //check if rates data are in db
        for(int i=0;i<dates.size();i++){
            Iterable<CurrencyRate> checkResultFromDB = currencyRateRepository.findByDate(LocalDate.parse(dates.get(i)));

            if(Calculator.count(checkResultFromDB)==0){//iterable jest pusty
                // load rates from XMLService
                xmlService.parseCurrencyByDate(dates.get(i));
                List<CurrencyRate> currencys = xmlService.getCurrencyRates();

                // save all currency
                for (CurrencyRate currency : currencys) {
                    currencyRateRepository.save(currency);
                }
            }else{
                break;
            }
        }
    }
}