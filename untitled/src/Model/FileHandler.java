package Model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public static void loadPath(String path, Model model) {
        model.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);

                model.addPoint(x, y);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}