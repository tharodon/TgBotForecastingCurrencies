package ru.liga.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.parser.DateAndCurrencies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class DAOCurrency implements CurrencyRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public DAOCurrency() {
    }

    public DateAndCurrencies getInfo(String currency) throws FileNotFoundException {
        logger.debug("getInfo was called. Обращение к базе данных, получение истории курсов");
        DateAndCurrencies dateAndCurrencies = new DateAndCurrencies();
        try(FileReader file = new FileReader("src/main/resources/"
                + currency + "_F01_02_2005_T05_03_2022.csv");){
            Scanner scan = new Scanner(file);
            scan.nextLine();
            while (scan.hasNext()) {
                String str = scan.nextLine();
                    int year = Integer.parseInt(str.split(";")[1].split("\\.")[2]);
                    int month = Integer.parseInt(str.split(";")[1].split("\\.")[1]);
                    int day = Integer.parseInt(str.split(";")[1].split("\\.")[0]);
                    LocalDate l = LocalDate.of(year, month, day);
                str = str.replace(',', '.')
                        .split(";")[2]
                        .replace('\"', ' ')
                        .trim();
                dateAndCurrencies.available(Double.parseDouble(str), l);
            }
            scan.close();
        }catch (Exception e){
            logger.debug("Ошибка данных");
            throw new FileNotFoundException("Please, choose legal currency");
        }
        logger.debug("Информация об истории курсов получена");
        return dateAndCurrencies;
    }
}
