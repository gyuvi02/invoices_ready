package szamlak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import szamlak.adatok.AdatKezelo;
import szamlak.adatok.Beolvasott;
import szamlak.adatok.Oraallas;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class AdatAblakController {

    Oraallas utolso = AdatKezelo.getInstance().getRezsiAdatok().get(utolsoElemSzam());

    Beolvasott beolvasott = Beolvasott.getInstance();

    private int valasztottEv;
    private int valasztottHonap;
    private double doubleAktualisGaz = 0;
    private double doubleAktualisVillany = 0;
    private double csereGaz = 0.0;
    private double csereVillany = 0.0;

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
        if (beolvasott.getGazEgysegar() == null) {
            elsoBeolvasottFeltoltes();
        }
        Oraallas utolso = AdatKezelo.getInstance().getRezsiAdatok().get(utolsoElemSzam());
        evValaszto.getSelectionModel().select(String.valueOf(utolso.getEv())); //Ez es a kovetkezo beallitja, hogy az ev es a honap automatikusan a legutobbi utan alljon be
        if ( utolso.getHonap()<12) {
            evValaszto.getSelectionModel().select(String.valueOf(utolso.getEv()));
            valasztottEv = utolso.getEv();
            honapValaszto.getSelectionModel().select(utolso.getHonap());
            valasztottHonap = utolso.getHonap()+1;
        }else {
            evValaszto.getSelectionModel().select(String.valueOf(utolso.getEv()+1));
            valasztottEv = utolso.getEv()+1;
            honapValaszto.getSelectionModel().select(utolso.getHonap()-12);
            valasztottHonap = utolso.getHonap()-11;
        }
//        valasztottEv = utolso.getEv(); //itt beallitjuk, hogy a kivalasztott ev es honap valoban az legyen, mint ami megjelenik a legordulo menuben
//        valasztottHonap = utolso.getHonap()+1; //valamiert itt hozza kell adni egyet, hogy helyes legyen
        gazEgysegar.setText(beolvasott.getGazEgysegar());
        villanyEgysegar.setText(beolvasott.getVillanyEgysegar());
        gazAlapDij.setText(beolvasott.getGazAlapDij());
        kozosKoltseg.setText(beolvasott.getKozosKoltseg());
        lakber.setText(beolvasott.getLakber());
        csereLegordulo.getSelectionModel().selectFirst();
        aktualisGaz.setPromptText("Tizedes pontot használj!"); // Egy placeholder jelenik meg a mezoben, es automaitkusan eltunik valamilyen szoveg beirasakor
        aktualisVillany.setPromptText("Tizedes pontot használj!");
        if (beolvasott.getAktualisGaz() != null) {
            aktualisGaz.setText(beolvasott.getAktualisGaz());
        }
        if (beolvasott.getAktualisVillany() != null) {
            aktualisVillany.setText(beolvasott.getAktualisVillany());
        }
    }

    public void visszaFoablakra(ActionEvent event) throws Exception {
        elsoBeolvasottFeltoltes();
        Main.ablakBeallito("FoablakFXML");
        Main.getStage().setTitle("Számla nyilvántartó"); // vissza kell allitani az eredeti ablak cimet
    }

    public void oraCsere() throws Exception {
        Main.ablakBeallito("OraCsereloFXML");
        Main.getStage().setTitle("Óracsere rögzítése");
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
        beolvasottFeltoltes();
        if (tesztSzam()){
            errorDialogNemSzam();
            return;
        }else
        if ((valasztottEv < utolso.getEv()) || (valasztottEv == utolso.getEv() && valasztottHonap <= utolso.getHonap())) {
            errorDialogDatum();
            return;
        }
        doubleAktualisGaz = Double.parseDouble(beolvasott.getAktualisGaz()) + beolvasott.getCsereGaz()
            + beolvasott.getCsereGazLezaro();
        doubleAktualisVillany = Double.parseDouble(beolvasott.getAktualisVillany()) + beolvasott.getCsereVillany()
                + beolvasott.getCsereVillanyLezaro();
        if (csereLegordulo.getValue().equals("Igen")) {
            oraCsere();
            return;
        }
        else if (doubleAktualisGaz < utolso.getAktualisGazOraallas() || doubleAktualisVillany < utolso.getAktualisVillanyOraallas()) {
            errorDialog();
            return;
        } else if (Double.parseDouble(beolvasott.getGazEgysegar()) < 0 || Double.parseDouble(beolvasott.getGazAlapDij()) < 0
                || Double.parseDouble(beolvasott.getGazEgysegar()) < 0 || Double.parseDouble(beolvasott.getVillanyEgysegar()) < 0
                || Integer.parseInt(beolvasott.getKozosKoltseg()) < 0 || Integer.parseInt(beolvasott.getLakber()) < 0) {
            errorDialogNegativ();
            return;
        }
        Oraallas ujOraallas = new Oraallas(
                valasztottEv,
                valasztottHonap,
                utolso.getAktualisGazOraallas(),
                Double.parseDouble(beolvasott.getAktualisGaz()),
                Double.parseDouble(beolvasott.getGazEgysegar()),
                Double.parseDouble(beolvasott.getGazAlapDij()),
                utolso.getAktualisVillanyOraallas(),
                Double.parseDouble(beolvasott.getAktualisVillany()),
                Double.parseDouble(beolvasott.getVillanyEgysegar()),
                Integer.parseInt(beolvasott.getKozosKoltseg()),
                Integer.parseInt(beolvasott.getLakber()),
                beolvasott.getCsereGaz(),
                beolvasott.getCsereVillany());
            ujOraallas.hozzaadOraallas(ujOraallas);
            visszaFoablakra(event);
    }

    public void errorDialogNemSzam() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Hiba valamelyik mezővel");
        alert.setContentText("Nem lehet üres egyik mező sem, és csak számokat lehet beírni!" +
                "\nVagy tizedes vesszőt használtál pont helyett.");
        alert.getDialogPane().getScene().getStylesheets().add("alertCSS.css");
        alert.showAndWait();
    }

    public void errorDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Hiba az óraállásokkal!");
        alert.setContentText("Az óraállás nem lehet kevesebb, mint a korábban rögzített!" +
                "\nEsetleg mérőórát cseréltek?");
        alert.getDialogPane().getScene().getStylesheets().add("alertCSS.css");
        alert.showAndWait();
    }

    public void errorDialogNegativ() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Negatív értékek");
        alert.setContentText("Negatív értékeket nem adhatsz meg!");
        alert.getDialogPane().getScene().getStylesheets().add("alertCSS.css");
        alert.showAndWait();
    }

    public void errorDialogDatum() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("HIBA");
        alert.setHeaderText("Helytelen a dátum!");
        alert.setContentText("A dátum nem lehet korábbi vagy azonos, mint a legutóbbi!");
        alert.getDialogPane().getScene().getStylesheets().add("alertCSS.css");
        alert.showAndWait();
    }

    private int utolsoElemSzam() {
        return AdatKezelo.getInstance().getRezsiAdatok().size() - 1;
    }

    private boolean tesztSzam() {
        if (isParsable(beolvasott.getAktualisGaz()) && isParsable(beolvasott.getGazEgysegar()) && isParsable(beolvasott.getGazAlapDij())
            && isParsable(beolvasott.getAktualisVillany()) && isParsable(beolvasott.getVillanyEgysegar()) &&
                isParsable(beolvasott.getKozosKoltseg()) && isParsable(beolvasott.getLakber())) {
            return false;
        } else return true;
    }

    private void elsoBeolvasottFeltoltes() {
        beolvasott.setAktualisGaz("");
        beolvasott.setGazEgysegar(Double.toString(utolso.getEgysegarGaz()));
        beolvasott.setGazAlapDij(Double.toString(utolso.getGazAlapdij()));
        beolvasott.setAktualisVillany("");
        beolvasott.setVillanyEgysegar(Double.toString(utolso.getEgysegarVillany()));
        beolvasott.setKozosKoltseg(Integer.toString(utolso.getKozosKoltseg()));
        beolvasott.setLakber(Integer.toString(utolso.getLakber()));
        beolvasott.setCsereGaz(0.0);
        beolvasott.setCsereVillany(0.0);
        beolvasott.setCsereGazLezaro(0.0);
        beolvasott.setCsereVillanyLezaro(0.0);
    }

    private void beolvasottFeltoltes() {
        beolvasott.setAktualisGaz(aktualisGaz.getText());
        beolvasott.setGazEgysegar(gazEgysegar.getText());
        beolvasott.setGazAlapDij(gazAlapDij.getText());
        beolvasott.setAktualisVillany(aktualisVillany.getText());
        beolvasott.setVillanyEgysegar(villanyEgysegar.getText());
        beolvasott.setKozosKoltseg(kozosKoltseg.getText());
        beolvasott.setLakber(lakber.getText());

    }
    public Oraallas getOraallas() {
        return utolso;
    }
}
