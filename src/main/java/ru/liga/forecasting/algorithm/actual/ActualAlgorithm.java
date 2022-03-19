package ru.liga.forecasting.algorithm.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.forecasting.algorithm.ForecastAlgorithm;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ActualAlgorithm implements ForecastAlgorithm {
    private Map<LocalDate, Double> dateAndCurrencies;
    private List<LocalDate> forecast;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ActualAlgorithm(Map<LocalDate, Double> dateAndCurrencies, List<LocalDate> forecast) {
        this.dateAndCurrencies = dateAndCurrencies;
        this.forecast = forecast;
    }

    public double[] forecast(){
        double[] forecastReturn = new double[forecast.size()];
        try{
            logger.debug("forecastActual was called");
            Double[] currencies = new Double[2];
            for (int i = 0; i < forecast.size(); i++) {
                forecastReturn[i] = dateAndCurrencies.get(forecast.get(i).minusYears(2))
                        + dateAndCurrencies.get(forecast.get(i).minusYears(3));
            }
        }catch (NullPointerException e){
            logger.debug("Выбрана некорректная дата прогноза");
            throw new IllegalArgumentException("Выбрана некорректная дата прогноза");
        }
        return forecastReturn;
    }
}

