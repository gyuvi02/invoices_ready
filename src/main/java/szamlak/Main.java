package szamlak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import szamlak.adatok.AdatKezelo;

import java.io.IOException;

public class Main extends Application {

    private static Scene ablak;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage elsoStage) throws Exception {
        stage = elsoStage; //ez kell ahhoz, hogy mukodjon a getStage metodus
        ablak = new Scene(loadFXML("FoablakFXML"), 750, 500);
        stage.setTitle("Számla nyilvántartó") ;
        stage.setResizable(false);
        stage.setScene(ablak);
        stage.getIcons().add(new Image("/money.jpg"));
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static void ablakBeallito(String fxml) throws IOException {
        ablak.setRoot(loadFXML(fxml));
    }

    public static Stage getStage(){
        return stage; }//ebben a formaban meg lehet hivni pl. egy masik Controllerbol

    @Override
    public void stop() {
            AdatKezelo.getInstance().kiirSzamlak();
    }

    @Override
    public void init() {
            AdatKezelo.getInstance().betoltSzamlak();
    }
}
