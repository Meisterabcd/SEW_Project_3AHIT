package Model;
import java.io.*;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileHandler {

    public static void savePath(String path, List<Double> x, List<Double> y) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            for (int i = 0; i < x.size(); i++) {
                writer.write(x.get(i) + "," + y.get(i));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void loadPath(String path, Model model) throws IOException {
        model.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                model.addPoint(
                        Double.parseDouble(parts[0]),
                        Double.parseDouble(parts[1])
                );
            }
        }
    }
}