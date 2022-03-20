package ru.liga.forecasting.algorithm.linear;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.database.DAOCurrency;
import ru.liga.forecasting.algorithm.actual.ActualAlgorithm;
import ru.liga.parser.DateAndCurrencies;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LinearAlgorithmTest extends TestCase {

    @Test
    public void testForecastToDate() throws FileNotFoundException {
        List<LocalDate> dates = Arrays.asList(LocalDate.of(2022, 4, 24));
        DateAndCurrencies dateAndCurrencies = new DAOCurrency().getInfo("TRY");
        LinearAlgorithm linearAlgorithm = new LinearAlgorithm(dateAndCurrencies, dates);
        double[] res = linearAlgorithm.forecast();
        double[] example = {100.05};
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
        DateAndCurrencies dateAndCurrencies = new DAOCurrency().getInfo("TRY");
        LinearAlgorithm linearAlgorithm = new LinearAlgorithm(dateAndCurrencies, dates);
        double[] res = linearAlgorithm.forecast();
        double[] example = {79.25, 79.86, 80.47, 81.08, 81.70, 82.31, 82.92};
        for (int i = 0; i < res.length; i++){
            assertEquals(res[i], example[i], 0.01);
        }
    }

    @Test
    public void testForecastToMonth() throws FileNotFoundException {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 1; i <= 30; i++){
            dates.add(LocalDate.now().plusDays(i));
        }
        DateAndCurrencies dateAndCurrencies = new DAOCurrency().getInfo("TRY");
        LinearAlgorithm linearAlgorithm = new LinearAlgorithm(dateAndCurrencies, dates);
        double[] res = linearAlgorithm.forecast();
        double[] example = {79.25, 79.86, 80.47, 81.08, 81.70,
                82.31, 82.92, 83.53, 84.14, 84.75, 85.37,
                85.98, 86.59, 87.20, 87.81, 88.42, 89.04,
                89.65, 90.26, 90.87, 91.48, 92.10, 92.71,
                93.32, 93.93, 94.54, 95.15, 95.77, 96.38, 96.99};
        for (int i = 0; i < res.length; i++){
            assertEquals(res[i], example[i], 0.01);
        }
    }


}