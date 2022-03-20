package ru.liga.parser;

import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParserTest extends TestCase {

    @Test
    public void testGetCurrency() {
        InputParser input = new InputParser("rate EUR -date 22.04.2022 -alg actual");
        String[] curr = {"EUR"};
        String[] res = input.getCurrency();
        for (int i = 0; i < curr.length; i++){
            assertEquals(res[i], curr[i]);
        }
    }

    @Test
    public void testGetCurrencies() {
        InputParser input = new InputParser("rate EUR, TRY, AMD, USD, BGN -date 22.04.2022 -alg actual");
        String[] curr = {"EUR", "TRY", "AMD", "USD", "BGN"};
        String[] res = input.getCurrency();
        for (int i = 0; i < curr.length; i++){
            assertEquals(res[i], curr[i]);
        }
    }

    @Test
    public void testGetAlgorithmActual() {
        InputParser input = new InputParser("rate EUR -date 22.04.2022 -alg actual");
        assertEquals(input.getAlgorithm(), "ACTUAL");
    }

    @Test
    public void testGetAlgorithmLinear() {
        InputParser input = new InputParser("rate EUR -date 22.04.2022 -alg linear");
        assertEquals(input.getAlgorithm(), "LINEAR");
    }

    @Test
    public void testGetAlgorithmMystical() {
        InputParser input = new InputParser("rate EUR -date 22.04.2022 -alg Mystical");
        assertEquals(input.getAlgorithm(), "MYSTICAL");
    }

    @Test
    public void testGetDatesTomorrow() {
        InputParser input = new InputParser("rate EUR -date tomorrow -alg mystical");
        List<LocalDate> dates = Arrays.asList(LocalDate.now().plusDays(1));
        assertEquals(input.getDates(), dates);
    }

    @Test
    public void testGetDatesToDate() {
        InputParser input = new InputParser("rate EUR -date 20.04.2022 -alg mystical");
        List<LocalDate> dates = Arrays.asList(LocalDate.of(2022, 4, 20));
        assertEquals(input.getDates(), dates);
    }

    @Test
    public void testGetDatesPeriodWeek() {
        InputParser input = new InputParser("rate EUR -period week -alg mystical");
        List<LocalDate> dates = Arrays.asList(LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(4),
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(6),
                LocalDate.now().plusDays(7));

        assertEquals(input.getDates(), dates);
    }

    @Test
    public void testGetDatesPeriodMonth() {
        InputParser input = new InputParser("rate EUR -period month -alg mystical");
        List<LocalDate> dates = new ArrayList<>(30);
        for (int i = 1; i <= 30; i++){
            dates.add(LocalDate.now().plusDays(i));
        }

        assertEquals(input.getDates(), dates);
    }
}