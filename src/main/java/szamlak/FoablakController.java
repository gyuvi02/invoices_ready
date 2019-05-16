package szamlak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import szamlak.adatok.AdatKezelo;
import szamlak.adatok.Oraallas;

import java.io.IOException;
import java.util.Optional;

public class FoablakController {

    @FXML
    private TextArea szamlaReszletek;

    @FXML
    private ListView<Oraallas> szamlaLista;

    @FXML
    private Parent root;

    public void initialize() {
        szamlaLista.getItems().setAll(AdatKezelo.getInstance().getRezsiAdatok());
        szamlaLista.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        szamlaLista.getSelectionModel().selectLast();
        Oraallas elem = szamlaLista.getSelectionModel().getSelectedItem();
        szamlaReszletek.setText(Oraallas.reszletesSzoveg(elem));

    }

    @FXML
    public void listaKlikk() {
        Oraallas elem = szamlaLista.getSelectionModel().getSelectedItem();
        szamlaReszletek.setText(Oraallas.reszletesSzoveg(elem));
    }

    public void menuKilepes(ActionEvent event) {
        Stage ablak = (Stage) root.getScene().getWindow();
        ablak.close();
    }

    public void torlesGomb() {
        Oraallas elem = szamlaLista.getSelectionModel().getSelectedItem();
        elem.elemTorles(elem);
        initialize();
    }

    public void megnyitAdatAblak(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AdatAblakFXML.fxml"));
        Parent adatParent = loader.load();
        Scene adatScene = new Scene(adatParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(adatScene);
        window.setTitle("Rezsi adatok hozzáadása");
        window.getIcons().add(new Image("/money.jpg"));
    }

    public void torlesBiztos() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Teljesen biztos");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nem");

        alert.setTitle("Törlés megerősítése");
        alert.setHeaderText("Biztos, hogy törölni akarja ezeket az adatokat?");
        alert.setContentText("Biztos?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            torlesGomb();
        } else {
            return;
        }
    }

}
