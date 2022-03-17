package ru.liga.parser;

import ru.liga.CurrencyRepository;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class DAOCurrency implements CurrencyRepository {


    public DAOCurrency() {
    }

    public DateAndCurrencies getInfo(String currency) throws IOException {
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
            System.out.println("Ошибка данных");
            throw new FileNotFoundException("Please, choose legal currency");
        }
        return dateAndCurrencies;
    }
}
