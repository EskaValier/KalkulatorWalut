package pl.gdynia.amw.lab6;

import java.util.Set;

public enum Currencies {;
    //currency related to currencies on ECB
    public static final String baseCurrency = "EUR";

    //all available currencies in ECB
    public static final Set<String> currencies = Set.of(
            "EUR", "USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON",
            "SEK", "CHF", "ISK", "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD",
            "CNY", "HKD", "IDR", "ILS", "INR", "KRW", "MXN", "MYR", "NZD", "PHP",
            "SGD", "THB", "ZAR");
}
