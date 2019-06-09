package szamlak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import szamlak.adatok.AdatKezelo;
import szamlak.adatok.Beolvasott;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;


public class OraCsereloController {

    Beolvasott csereloBeolvasott = Beolvasott.getInstance();

    @FXML
    private ComboBox melyikora;

//    @FXML
//    private Button mentes;
//
//    @FXML
//    private Button megse;
//
    @FXML
    private TextField vegsoAllas;

    public void initialize() {
        vegsoAllas.setPromptText("Tizedes pontot használj!");
    }

    public void oraValasztas(ActionEvent event) {
    }

    public void elmentes(ActionEvent event) throws Exception {
        if (!(isParsable(vegsoAllas.getText()))) {
            errorDialogNemSzam();
            return;
        }
        if (melyikora.getValue().equals("Gázóra")) {
            double regiGaz = Double.parseDouble(vegsoAllas.getText()) -  AdatKezelo.getInstance().getRezsiAdatok().get(utolsoElemSzam()).getAktualisGazOraallas();
            if (regiGaz<0) {
                errorDialog();
                return;
            }
            csereloBeolvasott.setCsereGaz(regiGaz);
            csereloBeolvasott.kiir();
            visszaAdatablakra(event);
        }
    }

    public void visszaAdatablakra(ActionEvent event) throws Exception {
        Main.ablakBeallito("AdatablakFXML");
        Main.getStage().setTitle("Rezsi adatok hozzáadása");
    }

    public void errorDialogNemSzam() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Hiba valamelyik mezővel");
        alert.setContentText("Nem lehet üres a mező, és csak számokat lehet beírni!\nVagy tizedes vesszőt használtál pont helyett.");
        alert.showAndWait();
    }

    public void errorDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Hiba az óraállásokkal!");
        alert.setContentText("A leszerelt óra utolsó leolvasott értéke nem lehet kevesebb, mint a megelőző hónapban rögzített!\nEllenőrizd a beírt értéket");
        alert.showAndWait();
    }

    private int utolsoElemSzam() {
        return AdatKezelo.getInstance().getRezsiAdatok().size() - 1;
    }




}
