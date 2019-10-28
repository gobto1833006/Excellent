import javafx.application.Application;
import javafx.scene.Scene;
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

public class Excell extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Xcell rafraiche haleine");

        MenuItem lignesBoutton=new MenuItem("Lignes");
        MenuItem pieChartBouton=new MenuItem("Régions");
        MenuItem barresBoutton=new MenuItem("Barres");
        MenuItem pNGBoutton=new MenuItem("PNG");
        MenuItem gifBoutton=new MenuItem("Gif");
        Menu importer=new Menu("Importer");
        Menu exporter=new Menu("Exporter");
        importer.getItems().addAll(lignesBoutton,pieChartBouton,barresBoutton);
        exporter.getItems().addAll(pNGBoutton,gifBoutton);
        MenuBar menuBar=new MenuBar();
        menuBar.getMenus().addAll(importer,exporter);

        BorderPane borderPane=new BorderPane();
        borderPane.setTop(menuBar);

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Veuillez sélectionner un fichier");
        File fichier=fileChooser.showOpenDialog(primaryStage);

        
        try {
            Files.readAllLines(Paths.get(fichier.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene=new Scene(borderPane);
        primaryStage.setScene(scene);



        primaryStage.show();
    }
}
