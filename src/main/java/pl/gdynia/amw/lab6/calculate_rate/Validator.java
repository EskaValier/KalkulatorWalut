package pl.gdynia.amw.lab6.calculate_rate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.gdynia.amw.lab6.Currencies;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validator {

    public static void validateParameters(String from, String to, String date) throws ResponseStatusException {
        if(!Currencies.currencies.contains(from) ||
                !Currencies.currencies.contains(to)){
            throw new ResponseStatusException( HttpStatus.NOT_FOUND );    //404
        }
        if(!validateLocalDate(date)){
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST );    //400
        }
        if(LocalDate.parse(date).isAfter(LocalDate.now()) ){
            throw new ResponseStatusException( HttpStatus.NOT_ACCEPTABLE  );    //406
        }

    }

    public static boolean validateLocalDate(String dateString) {
        try {
            LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
