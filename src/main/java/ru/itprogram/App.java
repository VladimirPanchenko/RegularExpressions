package ru.itprogram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itprogram.entity.Currency;
import ru.itprogram.exceptions.CurrencyNotFoundException;
import ru.itprogram.utils.CurrencyParser;
import ru.itprogram.utils.SimpleDateFormatView;

import java.io.IOException;
import java.text.DecimalFormat;

public class App {
    private static Logger logger;

    public static void main( String[] args ) {
        logger = LoggerFactory.getLogger(App.class);
        CurrencyParser currencyParser = new CurrencyParser();
        Currency currency = null;
        try {
            currencyParser.startParse(CurrencyParser.SITE_URL);
            currency = currencyParser.getCurrency("USD");
            System.out.println(currency);
            DecimalFormat decimalFormat = new DecimalFormat("Цена за валюту: 0.00руб");
            System.out.println(decimalFormat.format(currency.getCourse()));
        } catch (IOException | CurrencyNotFoundException e) {
            logger.error(e.getMessage());
        }
        SimpleDateFormatView.dataTimePrint();
    }
}
