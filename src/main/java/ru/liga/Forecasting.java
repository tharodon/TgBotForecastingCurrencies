package ru.liga;

import ru.liga.parser.DAOCurrency;
import ru.liga.parser.DateAndCurrencies;
import ru.liga.parser.InputParser;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Forecasting {
    private InputParser input;

    public Forecasting(InputParser input) {
        this.input = input;
    }

    private BigDecimal average(BigDecimal[] x){
        BigDecimal sum = BigDecimal.valueOf(0.0);

        for (int i = 0; i < CountDays.DAYS_OF_WEEK; i++){
            sum = x[i].add(sum);
        }
        return sum.divide(BigDecimal.valueOf(x.length), RoundingMode.HALF_UP);
    }

    public boolean isGraph(){
        return input.isGraph();
    }

    public List<Map<LocalDate, Double>> forecasting() throws Exception {
        DAOCurrency daoCurrency = new DAOCurrency();
        input.isAllOkay();
        String[] currencies = input.getCurrency();
        List<Map<LocalDate, Double>> result = new ArrayList<>();

        for (int circ = 0; circ < currencies.length; circ++) {
            Map<LocalDate, Double> curr = new LinkedHashMap<>();
            DateAndCurrencies dateAndCurrencies = daoCurrency.getInfo(currencies[circ]);
            List<LocalDate> dates = input.getDates();
            double[] answer = new double[0];
            switch (input.getAlgorithm()) {
                case ("ACTUAL"): {
                    answer = forecastActual(dateAndCurrencies.getInformation(), input.getDates());
                    break;
                }
                case ("LINEAR"): {
                    int[] predictors = new int[dates.size()];
                    for (int i = 0; i < dates.size(); i++) {
                        predictors[i] = (int) dateAndCurrencies.differenceDates(dates.get(i), dateAndCurrencies.getDates().get(0)) + 30;
                    }
                    answer = forecastLinearregression(dateAndCurrencies, predictors);
                    break;
                }
                case ("MYSTICAL"): {
                    answer = forecastMystical(dateAndCurrencies, input.getDates());
                    break;
                }
                default: break;
            }
            int j = 0;
            for (double o : answer){
                curr.put(dates.get(j++), o);
            }
            result.add(curr);
        }
        if (isGraph())
            new GraphDesigner().draw(result);
        return result;
    }

    private double average(List<Double> curr){
        double res = 0.0;
        for (int i = 0; i < curr.size(); i++){
            res += curr.get(i);
        }
        return res/curr.size();
    }

    public double[] forecastMystical(DateAndCurrencies info, List<LocalDate> forecast){
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

    public double[] forecastActual(Map<LocalDate, Double> dateAndCurrencies, List<LocalDate> forecast){
        Double[] currencies = new Double[2];
        double[] forecastReturn = new double[forecast.size()];
        for (int i = 0; i < forecast.size(); i++){
            forecastReturn[i] = dateAndCurrencies.get(forecast.get(i).minusYears(2))
                    + dateAndCurrencies.get(forecast.get(i).minusYears(3));
        }
        return forecastReturn;
    }

    public double[] forecastLinearregression(DateAndCurrencies dateAndCurrencies, int[] predictors){
        Double[] x = new Double[30];
        Double[] y = new Double[30];
        List<Double> curr = dateAndCurrencies.getCurrencySortedToData();
        for (int i = 0; i < 30; i++){
            y[i] = (double) i;
        }
        int j = 0;
        for(int i = curr.size() - 30; i < curr.size(); i++){
            x[j] = curr.get(i);
            j++;
        }
        LinearRegression linearRegression = new LinearRegression(y, x);
        double[] currenciesForecast = new double[predictors.length];
        for (int i = 0; i < predictors.length; i++){
            currenciesForecast[i] = linearRegression.predict(predictors[i]);
        }
        return currenciesForecast;
    }
}
