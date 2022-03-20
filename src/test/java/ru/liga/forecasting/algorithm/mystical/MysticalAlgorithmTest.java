package ru.liga.forecasting.algorithm.mystical;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.database.DAOCurrency;
import ru.liga.forecasting.algorithm.linear.LinearAlgorithm;
import ru.liga.parser.DateAndCurrencies;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MysticalAlgorithmTest extends TestCase {

    @Test
    public void testForecastToDate() throws FileNotFoundException {
        List<LocalDate> dates = Arrays.asList(LocalDate.of(2022, 4, 24));
        DateAndCurrencies dateAndCurrencies = new DAOCurrency().getInfo("AMD");
        MysticalAlgorithm mysticalAlgorithm = new MysticalAlgorithm(dateAndCurrencies, dates);
        double[] res = mysticalAlgorithm.forecast();
        double[] example = {16.8};
        for (int i = 0; i < res.length; i++){
            assertEquals(res[i], example[i], 0.01);
        }
    }

    @Test
    public void testForecastToWeek() throws FileNotFoundException {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 1; i <= 7; i++){
            dates.add(LocalDate.now().plusDays(i));
        }
        DateAndCurrencies dateAndCurrencies = new DAOCurrency().getInfo("AMD");
        MysticalAlgorithm mysticalAlgorithm = new MysticalAlgorithm(dateAndCurrencies, dates);
        double[] res = mysticalAlgorithm.forecast();
        double[] example = {16.8, 16.93, 16.13, 14.56, 14.93, 15.15, 16.58};
        for (int i = 0; i < res.length; i++){
            assertEquals(res[i], example[i], 10);
        }
    }

    @Test
    public void testForecastToMonth() throws FileNotFoundException {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 1; i <= 30; i++){
            dates.add(LocalDate.now().plusDays(i));
        }
        DateAndCurrencies dateAndCurrencies = new DAOCurrency().getInfo("AMD");
        MysticalAlgorithm mysticalAlgorithm = new MysticalAlgorithm(dateAndCurrencies, dates);
        double[] res = mysticalAlgorithm.forecast();
        double[] example = {16.80, 16.93, 16.13, 14.56,
                14.93, 15.15, 16.58, 18.13, 18.47, 17.14,
                18.33, 19.69, 20.83, 19.27, 19.87, 18.31,
                17.91, 16.30, 17.11, 17.72, 16.00, 15.65,
                15.01, 14.03, 14.80, 13.97, 12.84, 13.89, 12.62, 13.15};
        for (int i = 0; i < res.length; i++){
            assertEquals(res[i], example[i], 10);
        }
    }
}