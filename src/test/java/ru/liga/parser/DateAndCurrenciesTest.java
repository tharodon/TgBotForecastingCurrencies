package ru.liga.parser;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.*;

public class DateAndCurrenciesTest extends TestCase {

    public void testAvailable1(){
        Map<LocalDate, Double> datesAndCurr = new LinkedHashMap<>();
        DateAndCurrencies info = new DateAndCurrencies();
        datesAndCurr.put(LocalDate.now().plusDays(1), 87.0);
        info.available(87.0, LocalDate.now().plusDays(1));
        assertThat(info.getInformation()).isEqualTo(datesAndCurr);
    }

    public void testAvailable2(){
        Map<LocalDate, Double> datesAndCurr = new LinkedHashMap<>();
        DateAndCurrencies info = new DateAndCurrencies();
        for (int i = 10; i < 30; i++){
            datesAndCurr.put(LocalDate.now().plusDays(1 + i), 87.0 + i);
            info.available(87.0 + i, LocalDate.now().plusDays(1 + i));
        }
        assertThat(info.getInformation()).isEqualTo(datesAndCurr);
    }

    public void testGetCurrencySortedToData() {
        List<Double> example = Arrays.asList(10.0, 20.99, 50.89, 40.78);
        DateAndCurrencies info = new DateAndCurrencies();
        info.available(40.78, LocalDate.now().plusDays(5));
        info.available(50.89, LocalDate.now().plusDays(6));
        info.available(20.99, LocalDate.now().plusDays(7));
        info.available(10.0, LocalDate.now().plusDays(8));
        assertThat(info.getCurrencySortedToData()).isEqualTo(example);
    }

    public void testDifferenceDates1() {
        DateAndCurrencies dtc = new DateAndCurrencies();
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(1);
        assertThat(dtc.differenceDates(date1, date2)).isEqualTo(1);
    }

    public void testDifferenceDates2() {
        DateAndCurrencies dtc = new DateAndCurrencies();
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(10);
        assertThat(dtc.differenceDates(date1, date2)).isEqualTo(10);
    }

    public void testDifferenceDates3() {
        DateAndCurrencies dtc = new DateAndCurrencies();
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        assertThat(dtc.differenceDates(date1, date2)).isEqualTo(0);
    }
}