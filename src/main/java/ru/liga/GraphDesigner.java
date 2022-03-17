package ru.liga;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphDesigner {


    public void draw(List<Map<LocalDate, Double>> coordinates){
        Plot plt = Plot.create();

        for (int i = 0; i < coordinates.size(); i++){
            List<Double> x = new ArrayList<>();
            List<Double> y = new ArrayList<>();
            for (Map.Entry<LocalDate, Double> entry : coordinates.get(i).entrySet()){
                x.add(entry.getValue());
            }
            for (double j = 1.0; j <= coordinates.get(i).size(); j++){
                y.add(j);
            }
            plt.plot().add(y, x);
        }
        plt.xlabel("Currency");
        plt.ylabel("Date");
        plt.title("Forecasting");
        plt.savefig("src/main/resources/graf.png").dpi(200);
        try{
            plt.executeSilently();
        }catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
