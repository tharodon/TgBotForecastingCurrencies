package ru.liga.database;

import ru.liga.parser.DateAndCurrencies;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface CurrencyRepository {
    DateAndCurrencies getInfo(String currency) throws IOException;
}
