package szamlak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import szamlak.adatok.AdatKezelo;
import szamlak.adatok.Oraallas;

public class AdatAblakController {

    int utolsoIndex = AdatKezelo.getInstance().getRezsiAdatok().size() - 1;
    Oraallas utolso = AdatKezelo.getInstance().getRezsiAdatok().get(utolsoIndex);

    private int valasztottEv;
    private int valasztottHonap;
    private int intAktualisGaz = utolso.getAktualisGazOraallas();
    private int intAktualisVillany = utolso.getAktualisVillanyOraallas();
    private double doubleGazEgysegar = utolso.getEgysegarGaz();
    private double doubleVillanyegysegar = utolso.getEgysegarVillany();
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
    private TextField aktualisGaz;

    @FXML
    private TextField gazEgysegar;

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
        kozosKoltseg.setText(Integer.toString(intKozosKoltseg));
        lakber.setText(Integer.toString(intLakber));
    }


    public void visszaFoablakra(ActionEvent event) throws Exception {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/FoablakFXML.fxml"));
            Parent foablakParent = loader.load();
            Scene foablakScene = new Scene(foablakParent, 600, 500);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(foablakScene);
            window.setTitle("Számla nyilvántartó");
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
        }else {
            intAktualisGaz = Integer.parseInt(aktualisGaz.getText());
            doubleGazEgysegar = Double.parseDouble(gazEgysegar.getText());
            intAktualisVillany = Integer.parseInt(aktualisVillany.getText());
            doubleVillanyegysegar = Double.parseDouble(villanyEgysegar.getText());
            intKozosKoltseg = Integer.parseInt(kozosKoltseg.getText());
            intLakber = Integer.parseInt(lakber.getText());
        }
        if (intAktualisGaz < utolso.getAktualisGazOraallas()) {
            errorDialog();
        }else {
            Oraallas ujOraallas = new Oraallas(valasztottEv, valasztottHonap, utolso.getAktualisGazOraallas() , intAktualisGaz,
                    doubleGazEgysegar, utolso.getAktualisVillanyOraallas(), intAktualisVillany, doubleVillanyegysegar,
                    intKozosKoltseg, intLakber );
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
