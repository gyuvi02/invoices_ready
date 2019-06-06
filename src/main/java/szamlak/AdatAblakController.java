package szamlak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import szamlak.adatok.AdatKezelo;
import szamlak.adatok.Oraallas;

public class AdatAblakController {

    int utolsoIndex = AdatKezelo.getInstance().getRezsiAdatok().size() - 1;
    Oraallas utolso = AdatKezelo.getInstance().getRezsiAdatok().get(utolsoIndex);

    private int valasztottEv;
    private int valasztottHonap;
    private double doubleAktualisGaz = utolso.getAktualisGazOraallas();
    private double doubleAktualisVillany = utolso.getAktualisVillanyOraallas();
    private double doubleGazEgysegar = utolso.getEgysegarGaz();
    private double doubleVillanyegysegar = utolso.getEgysegarVillany();
    private int intGazAlapdij = utolso.getGazAlapdij();
    private int intKozosKoltseg = utolso.getKozosKoltseg();
    private int intLakber = utolso.getLakber();

    @FXML
    private Button kilepo;

    @FXML
    private Parent root;

    @FXML
    private ComboBox<String> evValaszto;

    @FXML
    private ComboBox<String> honapValaszto;

    @FXML
    private ComboBox<String> csereLegordulo;

    @FXML
    private TextField aktualisGaz;

    @FXML
    private TextField gazEgysegar;

    @FXML
    private TextField gazAlapDij;

    @FXML
    private TextField aktualisVillany;

    @FXML
    private TextField villanyEgysegar;

    @FXML
    private TextField kozosKoltseg;

    @FXML
    private TextField lakber;

    public void initialize() {
        gazEgysegar.setText(Double.toString(doubleGazEgysegar));
        villanyEgysegar.setText(Double.toString(doubleVillanyegysegar));
        gazAlapDij.setText(Integer.toString(intGazAlapdij));
        kozosKoltseg.setText(Integer.toString(intKozosKoltseg));
        lakber.setText(Integer.toString(intLakber));
        csereLegordulo.getSelectionModel().selectFirst();
    }

    public void visszaFoablakra(ActionEvent event) throws Exception {
        Main.ablakBeallito("FoablakFXML");
        Main.getStage().setTitle("Számla nyilvántartó"); // vissza kell allitani az eredeti ablak cimet
    }

    public void oraCsere() throws Exception {
        Main.ablakBeallito("OraCsereloFXML");
        Main.getStage().setTitle("Óracsere rögzítése");
    }

    public void vanCsere (ActionEvent event) {
    }

    public void evBeiras() {
        valasztottEv = Integer.parseInt(evValaszto.getValue());
    }

    public void honapBeiras(ActionEvent event) {
        String honap = honapValaszto.getValue();
        switch (honap) {
            case "Január": valasztottHonap = 1; break;
            case "Február": valasztottHonap = 2; break;
            case "Március": valasztottHonap = 3; break;
            case "Április": valasztottHonap = 4; break;
            case "Május": valasztottHonap = 5; break;
            case "Június": valasztottHonap = 6; break;
            case "Július": valasztottHonap = 7; break;
            case "Augusztus": valasztottHonap = 8; break;
            case "Szeptember": valasztottHonap = 9; break;
            case "Október": valasztottHonap = 10; break;
            case "November": valasztottHonap = 11; break;
            case "December": valasztottHonap = 12;
        }
    }

    public void oraallasMentes(ActionEvent event) throws Exception {
        if ((valasztottEv < utolso.getEv()) || (valasztottEv == utolso.getEv() && valasztottHonap <= utolso.getHonap())) {
            errorDialogDatum();
            return;
        }
        if (aktualisGaz.getText().isEmpty()) {
            errorDialog();
            return;
        } else if (aktualisVillany.getText().isEmpty()) {
            errorDialog();
            return;
        }
        else {
            doubleAktualisGaz = Double.parseDouble(aktualisGaz.getText());
            doubleGazEgysegar = Double.parseDouble(gazEgysegar.getText());
            intGazAlapdij = Integer.parseInt(gazAlapDij.getText());
            doubleGazEgysegar = Double.parseDouble(gazEgysegar.getText());
            doubleAktualisVillany = Double.parseDouble(aktualisVillany.getText());
            doubleVillanyegysegar = Double.parseDouble(villanyEgysegar.getText());
            intKozosKoltseg = Integer.parseInt(kozosKoltseg.getText());
            intLakber = Integer.parseInt(lakber.getText());
        }
        if (doubleAktualisGaz < utolso.getAktualisGazOraallas()) {
            errorDialog();
        }else if (doubleAktualisVillany < utolso.getAktualisVillanyOraallas()) {
            errorDialog();
        } else if (csereLegordulo.getValue().equals("Igen")) {
            oraCsere();
        } else {
            Oraallas ujOraallas = new Oraallas(valasztottEv, valasztottHonap, utolso.getAktualisGazOraallas(), doubleAktualisGaz,
                    doubleGazEgysegar, intGazAlapdij, utolso.getAktualisVillanyOraallas(), doubleAktualisVillany, doubleVillanyegysegar,
                    intKozosKoltseg, intLakber);
            ujOraallas.hozzaadOraallas(ujOraallas);
            visszaFoablakra(event);
        }
    }

    public void errorDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Hiba az óraállásokkal!");
        alert.setContentText("Az óraállás nem lehet kevesebb, mint a korábban rögzített!");
        alert.initOwner(root.getScene().getWindow());
        alert.showAndWait();
    }

    public void errorDialogDatum() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Helytelen a dátum!");
        alert.setContentText("A dátum nem lehet korábbi vagy azonos, mint a legutóbbi!");
        alert.showAndWait();
    }

}
