import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

        CategoryAxis categoryAxis=new CategoryAxis();
        NumberAxis numberAxis=new NumberAxis();
        categoryAxis.setLabel("Mois");
        numberAxis.setLabel("Température (degrés)");

        areaBouton.setOnAction(event -> {

            AreaChart<String,Number> areaChart=new AreaChart<>(categoryAxis,numberAxis);
            areaChart.setTitle("Température moyenne selon le mois");
            areaChart.getData().add(analyseFile(primaryStage));
            borderPane.setCenter(areaChart);
        });
        barresBoutton.setOnAction(event -> {
            BarChart<String,Number> barChart=new BarChart<>(categoryAxis,numberAxis);
            barChart.setTitle("Température moyenne selon le mois");
            barChart.getData().add(analyseFile(primaryStage));
            borderPane.setCenter(barChart);
        });
        lignesBoutton.setOnAction(event -> {
            LineChart<String,Number> lineChart=new LineChart<>(categoryAxis,numberAxis);
            lineChart.setTitle("Température moyenne selon le mois");
            lineChart.getData().add(analyseFile(primaryStage));
            borderPane.setCenter(lineChart);
        });





        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);


        primaryStage.show();
    }

    public XYChart.Series analyseFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez sélectionner un fichier");
        File fichier = fileChooser.showOpenDialog(stage);
        ArrayList<String[]> listDonnee = new ArrayList<>();

        try {
            List<String> stringList = Files.readAllLines(Paths.get(fichier.getPath()));
            for (int i = 0; i < stringList.size(); i++) {
                listDonnee.add(stringList.get(i).split(", "));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        XYChart.Series series=new XYChart.Series();
        series.setName("Données 1");
        for (int i = 0; i < listDonnee.get(0).length; i++) {
            series.getData().add(new XYChart.Data<>(listDonnee.get(0)[i], Integer.parseInt(listDonnee.get(1)[i])));
        }

        return series;
    }
}
