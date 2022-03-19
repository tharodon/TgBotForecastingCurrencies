package ru.liga.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DateAndCurrencies {
    private final List<Double> currency;
    private final List<LocalDate> dates;
    private final Map<LocalDate, Double> information;
    private final List<LocalDate> fullMoons;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public DateAndCurrencies() {
        currency = new ArrayList<>();
        dates = new ArrayList<>();
        information = new LinkedHashMap<>();
        fullMoons = Arrays.asList(LocalDate.of(2022, 3, 2),
                LocalDate.of(2022, 2, 1),
                LocalDate.of(2022, 1, 2));

    }

    public Map<LocalDate, Double> getInformation() {
        for (int i = 0; i < dates.size(); i++){
            information.put(dates.get(i), currency.get(i));
        }
        return information;
    }

    public void available(Double cur, LocalDate date) {
        if (dates.isEmpty()){
            currency.add(cur);
            dates.add(date);
            return ;
        }
        long diff = Math.abs(ChronoUnit.DAYS.between(dates.get(dates.size() - 1), date));
        LocalDate prev = dates.get(dates.size() - 1);
        if (diff != 1) {
            for (long i = 1; i < diff; i++) {
                dates.add(prev.minusDays(i));
                currency.add(currency.get(currency.size() - 1));
            }
        }
        dates.add(date);
        currency.add(cur);
    }

    public List<LocalDate> getFullMoons() {
        return fullMoons;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public List<Double> getCurrency() {
        return currency;
    }

    public Double[] getCurrenciesArray(){
        return currency.toArray(new Double[0]);
    }

    public List<Double> getCurrencySortedToData() {
        logger.debug("getCurrencySortedToData was called.");
        List<Double> sortRes = new ArrayList<>();
        for (int i = currency.size() - 1; i >= 0; i--){
            sortRes.add(currency.get(i));
        }
        return sortRes;
    }
        @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < dates.size(); i++){
            out.append(i + 1).append(") ").append(dates.get(i).toString()).append(" :").append(currency.get(i).toString()).append("\n");
        }
        return out.toString();
    }

    public long differenceDates(LocalDate a, LocalDate b){
        long abs = ChronoUnit.DAYS.between(a, b);
        return Math.abs(abs);
    }
}
