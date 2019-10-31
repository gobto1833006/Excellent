import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Excell extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Xcell rafraiche haleine");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);

        Label bool = new Label("false");

        MenuItem lignesBoutton = new MenuItem("Lignes");
        MenuItem areaBouton = new MenuItem("Régions");
        MenuItem barresBoutton = new MenuItem("Barres");
        MenuItem pNGBoutton = new MenuItem("PNG");
        MenuItem gifBoutton = new MenuItem("Gif");
        Menu importer = new Menu("Importer");
        Menu exporter = new Menu("Exporter");
        importer.getItems().addAll(lignesBoutton, areaBouton, barresBoutton);
        exporter.getItems().addAll(pNGBoutton, gifBoutton);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(importer, exporter);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        CategoryAxis categoryAxis = new CategoryAxis();
        NumberAxis numberAxis = new NumberAxis();
        categoryAxis.setLabel("Mois");
        numberAxis.setLabel("Température (degrés)");

        areaBouton.setOnAction(event -> {

            AreaChart<String, Number> areaChart = new AreaChart<>(categoryAxis, numberAxis);
            areaChart.setTitle("Température moyenne selon le mois");
            XYChart.Series series = analyseFile(primaryStage);
            if (series != null) {
                areaChart.getData().add(series);
                borderPane.setCenter(areaChart);
                bool.setText("true");
            }
        });
        barresBoutton.setOnAction(event -> {
            BarChart<String, Number> barChart = new BarChart<>(categoryAxis, numberAxis);
            barChart.setTitle("Température moyenne selon le mois");
            XYChart.Series series = analyseFile(primaryStage);
            if (series != null) {
                barChart.getData().add(series);
                borderPane.setCenter(barChart);
                bool.setText("true");
            }
        });
        lignesBoutton.setOnAction(event -> {
            LineChart<String, Number> lineChart = new LineChart<>(categoryAxis, numberAxis);
            lineChart.setTitle("Température moyenne selon le mois");
            XYChart.Series series = analyseFile(primaryStage);
            if (series != null) {
                lineChart.getData().add(series);
                borderPane.setCenter(lineChart);
                bool.setText("true");
            }
        });


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);


        gifBoutton.setOnAction(event -> {
            if (bool.getText().equals("true")) {
                saveAsImage(primaryStage, "gif");
            }
        });

        pNGBoutton.setOnAction(event -> {
            if (bool.getText().equals("true")) {
                saveAsImage(primaryStage, "png");
            }
        });


        primaryStage.show();
    }

    public XYChart.Series analyseFile(Stage stage) {
        File fichier = gestionFichier(stage);
        if (fichier == null) {
            return null;
        }
        ArrayList<String[]> listDonnee = new ArrayList<>();

        try {
            List<String> stringList = Files.readAllLines(Paths.get(fichier.getPath()));
            for (int i = 0; i < 2; i++) {
                listDonnee.add(stringList.get(i).split(", "));
            }
        } catch (Exception e) {

        }
        XYChart.Series series = new XYChart.Series();
        series.setName("Données 1");


        try {


            for (int i = 0; i < listDonnee.get(0).length || i < listDonnee.get(1).length; i++) {
                    series.getData().add(new XYChart.Data<>(listDonnee.get(0)[i], Integer.parseInt(listDonnee.get(1)[i])));
            }
        }catch (Exception e){
            series =null;
        }


        return series;
    }

    public File gestionFichier(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez sélectionner un fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier data", "*.dat"));
        File fichier = fileChooser.showOpenDialog(stage);
        if (fichier == null) {
            return null;
        } else {
            return fichier;
        }
    }

    public void saveAsImage(Stage primaryStage, String fileType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("enregistrer sous");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Image", "*." + fileType));
        WritableImage image = primaryStage.getScene().snapshot(null);
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {


            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), fileType, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
