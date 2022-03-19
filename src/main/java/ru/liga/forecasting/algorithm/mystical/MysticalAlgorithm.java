package ru.liga.forecasting.algorithm.mystical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.forecasting.algorithm.ForecastAlgorithm;
import ru.liga.parser.DateAndCurrencies;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MysticalAlgorithm implements ForecastAlgorithm {
    private DateAndCurrencies info;
    private List<LocalDate> forecast;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MysticalAlgorithm(DateAndCurrencies info, List<LocalDate> forecast) {
        this.info = info;
        this.forecast = forecast;
    }

    public double[] forecast(){
        logger.debug("forecastMystical was called");
        Map<LocalDate, Double> currencies = info.getInformation();
        List<Double> fullMoonsCurrencies = new ArrayList<>();
        double[] forecastReturn = new double[forecast.size()];
        for (int i = 0; i < 3; i++){
            fullMoonsCurrencies.add(currencies.get(info.getFullMoons().get(i)));
        }
        int i = 0;
        forecastReturn[i] = average(fullMoonsCurrencies);
        while (++i < forecast.size()){
            double min = forecastReturn[i - 1] / -10;
            double max = forecastReturn[i - 1] / 10;
            double random = min + (Math.random() * (max - min));
            forecastReturn[i] = forecastReturn[i - 1] + random;
        }
        return forecastReturn;
    }

    private double average(List<Double> curr){
        double res = 0.0;
        for (int i = 0; i < curr.size(); i++){
            res += curr.get(i);
        }
        return res/curr.size();
    }
}
