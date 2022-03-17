package ru.liga.parser;

import ru.liga.CountDays;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class InputParser {
    private final String input;

    public InputParser(String input) {
        this.input = input;
    }


    private boolean isHavingAlgotithm() throws Exception {
        String[] strings = input.split(" ");
        boolean ok = false;
        if (strings.length < 6)
            throw new Exception("Too few arguments");
        for (int i = 0; i < strings.length; i++){
            if (strings[i].equals("-alg")){
                if (strings[i + 1].isEmpty())
                    throw new Exception("Please, choose algorithm");
                switch (strings[i + 1].toUpperCase(Locale.ROOT)){
                    case("LINEAR") :
                    case("MYSTICAL") :
                    case("ACTUAL") :
                        ok = true; break;
                    default: throw new Exception("Please, choose algorithm");
                }
            }
        }
        return ok;
    }


    private boolean isHavingDate() throws Exception {
        String[] strings = input.split(" ");
        boolean ok = false;

        ok = isHavingAlgotithm();
        for (int i = 0; i < strings.length; i++){
            if (strings[i].equals("-period")){
                if (strings[i + 1].isEmpty())
                    throw new Exception("Please, choose period or date");
                switch (strings[i + 1].toLowerCase()){
                    case("week") :
                    case("month") :
                        ok = true; break;
                    default: throw new Exception("Please, choose period or date");
                }
            }else if (strings[i].equals("-date")){
                if (strings[i + 1].isEmpty() || strings[i + 1].split("\\.").length != 3){
                    throw new Exception("Incorrect date. Please, write date in format \'dd.MM.yyyy\'");
                }
            }
        }
        return ok;
    }

    public boolean isAllOkay() throws Exception {
        if (isHavingAlgotithm() && isHavingDate())
            return true;
        return false;
    }

    public String[] getCurrency() {

        String[] result = input.split(" ");
        List<String> currencies = new ArrayList<>();
        int j = 0;
        for (int i = 1; !(result[i].equals("-date") || result[i].equals("-period")); i++){
            currencies.add(result[i]);
        }
        result = new String[currencies.size()];
        for (int i = 0; i < currencies.size(); i++){
            result[i] = currencies.get(i).split(",")[0];
        }
        return result;
    }

    public String getAlgorithm(){
        String[] args = input.split(" ");
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-alg"))
                return args[i + 1].toUpperCase();
        }
        return null;
    }

    public List<LocalDate> getDates(){
        String[] args = input.split(" ");
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-date")){
                switch (args[i + 1]) {
                    case("tomorrow") : {
                        dates.add(LocalDate.now().plusDays(1));
                        break;
                    } default : {
                        LocalDate date = LocalDate.of(
                                Integer.parseInt(args[i + 1].split("\\.")[2]),
                                Integer.parseInt(args[i + 1].split("\\.")[1]),
                                Integer.parseInt(args[i + 1].split("\\.")[0]));
                        dates.add(date);
                        break;
                    }
                }
            }else if (args[i].equals("-period")){
                switch (args[i + 1]) {
                    case("month") : {
                        for (int amount_days = 1; amount_days <= CountDays.DAYS_OF_MONTH; amount_days++) {
                            dates.add(LocalDate.now().plusDays(amount_days));
                        }
                        break;
                    }
                    case("week") : {
                        for (int amount_days = 1; amount_days <= CountDays.DAYS_OF_WEEK; amount_days++) {
                            dates.add(LocalDate.now().plusDays(amount_days));
                        }
                        break;
                    }
                }
            }
        }
        return dates;
    }

    public boolean isGraph(){
        String[] args = input.split(" ");
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-output")){
                if (args[i + 1] != null && args[i + 1].equals("graph"))
                    return true;
                return false;
            }
        }
        return false;
    }

    public String getPeriod(){
        String[] args = input.split(" ");
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-period") || args[i].equals("-date"))
                return args[i + 1];
        }
        return null;
    }


}
