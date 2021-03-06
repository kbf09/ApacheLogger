package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public readFile logFile;

    @FXML public PieChart listChart;
    @FXML public PieChart IPChart;
    @FXML public Label cnt;

    /*
    @FXML
    public void listSelected() {
        ObservableList<PieChart.Data> list =
         FXCollections.observableArrayList(
                new PieChart.Data("Sun", 20),
                new PieChart.Data("IBM", 12),
                new PieChart.Data("HP", 25),
                new PieChart.Data("Dell", 22),
                new PieChart.Data("ukeru", 30)
        );

        listChart.setData(list);
    }
    */

    @FXML
    public void fileOpen() {
        FileChooser fc = new FileChooser();
        String fileName = fc.showOpenDialog(null).toString();
        logFile = new readFile(fileName);

        IPChart.setData(logFile.getIP());

        cnt.setText("" + logFile.lineCnt);

    }


}

class readFile{
    public BufferedReader br;
    int lineCnt;
    
    public readFile(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            br = new BufferedReader(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PieChart.Data> getIP () {
        // return するList
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        try {
            String line;
            Matcher matcher;
            int lineCnt = 0;

            Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");

            HashMap<String, Integer> countMap = new HashMap<>();

            while (( line = br.readLine()) != null){
                String name;
                int sum;

                matcher = pattern.matcher(br.readLine());
                matcher.find();

                try {
                    name = matcher.group(1);
                } catch (Exception e) {
                    continue;
                }

                if (countMap.containsKey(name)) {

                    sum = countMap.get(name);
                    sum++;
                    countMap.put(name, sum);
                } else {
                    countMap.put(name, 0);
                }

                lineCnt++;
            }

            for(String key: countMap.keySet() ) {
                list.add(new PieChart.Data(key, countMap.get(key)));
            }
            this.lineCnt = lineCnt;
            return list;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
