package ru.liga.forecasting.algorithm.actual;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.database.DAOCurrency;
import ru.liga.database.DAOCurrencyTest;
import ru.liga.parser.DateAndCurrencies;
import ru.liga.parser.InputParser;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class ActualAlgorithmTest extends TestCase {

    @Test
    public void testForecast() throws FileNotFoundException {
        List<LocalDate> forecast = Arrays.asList(LocalDate.of(2022, 4, 24));
        DateAndCurrencies dates = new DAOCurrency().getInfo("EUR");
        ActualAlgorithm actualAlgorithm = new ActualAlgorithm(dates.getInformation(), forecast);
        assertThat(actualAlgorithm.forecast()).isEqualTo(new double[]{152.8921});
    }

    @Test
    public void testForecastNotValidDate() throws FileNotFoundException {
        List<LocalDate> forecast = Arrays.asList(LocalDate.of(2044, 4, 24));
        DateAndCurrencies dates = new DAOCurrency().getInfo("EUR");
        assertThatThrownBy(() -> new ActualAlgorithm(dates.getInformation(), forecast).forecast()).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testForecastToWeek() throws FileNotFoundException {
        List<LocalDate> forecast = new ArrayList<>(7);
        for(int i = 1; i <= 7; i++){
            forecast.add(LocalDate.now().plusDays(i));
        }
        DateAndCurrencies dates = new DAOCurrency().getInfo("EUR");
        ActualAlgorithm actualAlgorithm = new ActualAlgorithm(dates.getInformation(), forecast);
        double[] example = new double[]{157.09, 159.49, 159.29, 159.62, 158.34, 157.07, 158.54};
        double[] res = actualAlgorithm.forecast();
        for (int i = 0; i < example.length; i++){
            assertEquals(res[i], example[i], 0.01);
        }
    }

    public void testForecastToMonth() throws FileNotFoundException {
        List<LocalDate> forecast = new ArrayList<>(7);
        for(int i = 1; i <= 30; i++){
            forecast.add(LocalDate.now().plusDays(i));
        }
        DateAndCurrencies dates = new DAOCurrency().getInfo("EUR");
        ActualAlgorithm actualAlgorithm = new ActualAlgorithm(dates.getInformation(), forecast);
        double[] example = new double[]{157.09, 159.49, 159.30, 159.63,
                158.35, 157.07, 158.55, 158.46, 155.52, 155.36, 156.13,
                156.13, 156.13, 155.95, 155.81, 156.06, 156.07, 156.00,
                155.37, 155.60, 154.06, 153.68, 153.19, 153.38, 153.24,
                152.82, 153.37, 153.93, 152.47, 153.35};
        double[] res = actualAlgorithm.forecast();
        for (int i = 0; i < res.length; i++){
            assertEquals(res[i], example[i], 0.01);
        }
    }

}