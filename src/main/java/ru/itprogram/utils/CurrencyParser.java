package ru.itprogram.utils;

import ru.itprogram.entity.Currency;
import ru.itprogram.exceptions.CurrencyNotFoundException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyParser {
    public static final String SITE_URL = "https://www.cbr.ru/currency_base/daily/";
    private static final String ALL_FIND_PATTERN ="<tr>\\s*<td>.*?</tr>";
    private static final String DIGITAL_CODE_FIND_PATTERN="^\\s{0,}\\d{3}";
    private static final String LETTER_CODE_FIND_PATTERN="[A-Z][A-Z][A-Z]";
    private static final String ONE_UNIT_FIND_PATTERN="[A-Z]\\s{0,}[1][0]{0,}";
    private static final String CURRENCY_FIND_PATTERN="[А-Я].*[а-я]";
    private static final String COURSE_FIND_PATTERN="\\d{1,}[,]\\d{1,}";
    private static final String TRANSITION_TO_A_NEW_LINE="\n";
    private List<Currency> currencyList;

    public void startParse(String site) throws IOException {
        currencyList = new ArrayList<>();
        URL url = new URL(SITE_URL);
        InputStream in = new URL(site).openStream();
        StringBuilder storage = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            storage.append(line);
        }
        reader.close();
        in.close();
        parseNoFilter(storage);
    }

    private void parseNoFilter(StringBuilder stringBuilder) {
        Pattern pattern = Pattern.compile(ALL_FIND_PATTERN);
        Matcher matcher = pattern.matcher(stringBuilder);
        StringBuilder currenciesString = new StringBuilder();
        while (matcher.find()) {
            currenciesString.append(matcher.group()
                    .replaceAll("<tr>", "")
                    .replaceAll("</tr>", "")
                    .replaceAll("<td>", "")
                    .replaceAll("</td>", "") + "\n");
        }
        parseToObject(currenciesString);
    }

    private void parseToObject(StringBuilder stringBuilder) {
        Pattern patternDigitalCode = Pattern.compile(DIGITAL_CODE_FIND_PATTERN);
        Pattern patternLetterCode = Pattern.compile(LETTER_CODE_FIND_PATTERN);
        Pattern patternOneUnit = Pattern.compile(ONE_UNIT_FIND_PATTERN);
        Pattern patternCurrency = Pattern.compile(CURRENCY_FIND_PATTERN);
        Pattern patternCourse = Pattern.compile(COURSE_FIND_PATTERN);

        for (String s : stringBuilder.toString().split(TRANSITION_TO_A_NEW_LINE)) {
            Currency currency = new Currency();
            Matcher matcherDigitalCode = patternDigitalCode.matcher(s);
            if (matcherDigitalCode.find()) {
                currency.setDigitalCode(
                        Integer.parseInt(matcherDigitalCode.group().replaceAll(" ", "")));
            }
            Matcher matcherLetterCode = patternLetterCode.matcher(s);
            if (matcherLetterCode.find()) {
                currency.setLetterCode(matcherLetterCode.group());
            }
            Matcher matcherOneUnit = patternOneUnit.matcher(s);
            if (matcherOneUnit.find()) {
                currency.setOneUnit(Integer.parseInt(matcherOneUnit.group()
                        .replaceAll(" ", "")
                        .replaceAll("[A-Z]", "")));
            }
            Matcher matcherCurrency = patternCurrency.matcher(s);
            if (matcherCurrency.find()) {
                currency.setCurrency(matcherCurrency.group());
            }
            Matcher matcherCourse = patternCourse.matcher(s);
            if (matcherCourse.find()) {
                currency.setCourse(Double.parseDouble(matcherCourse.group().replaceAll(",", ".")));
            }
            currencyList.add(currency);
        }
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public Currency getCurrency(String letterCode)
            throws CurrencyNotFoundException, UnsupportedEncodingException {
        Currency currency = null;
        for (Currency findCurrency : currencyList) {
            if (findCurrency.getLetterCode().equals(letterCode)) {
                currency = findCurrency;
            }
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");
        if (currency == null) {
            throw new CurrencyNotFoundException(
                    new String(
                            resourceBundle
                                    .getString("currencynotfound")
                                    .getBytes("ISO-8859-1"), "UTF-8"));
        }
        return currency;
    }
}
