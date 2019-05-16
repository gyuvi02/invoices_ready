package szamlak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import szamlak.adatok.AdatKezelo;

public class Main extends Application {

    @Override
    public void start(Stage foAblak) throws Exception {
        FXMLLoader foablakLoader = new FXMLLoader(getClass().getResource("/fxml/FoablakFXML.fxml"));
        Parent foablakPane = foablakLoader.load();
        Scene foablakScene = new Scene(foablakPane, 600, 500);
        foAblak.setTitle("Számla nyilvántartó") ;
        foAblak.setScene(foablakScene);
        foAblak.getIcons().add(new Image("/money.jpg"));

        foAblak.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
            AdatKezelo.getInstance().kiirSzamlak();
    }

    @Override
    public void init() {
            AdatKezelo.getInstance().betoltSzamlak();

    }
}
