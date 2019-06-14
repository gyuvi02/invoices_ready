package szamlak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import szamlak.adatok.AdatKezelo;
import szamlak.adatok.Oraallas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FoablakController {
    int utolsoIndex = utolsoElemSzam();
    Oraallas utolso = AdatKezelo.getInstance().getRezsiAdatok().get(utolsoIndex);

    @FXML
    private TextArea szamlaReszletek;

    @FXML
    private ListView<Oraallas> szamlaLista;

    @FXML
    private Parent root;

    public void initialize(){
        szamlaLista.getItems().setAll(AdatKezelo.getInstance().getRezsiAdatok());
        szamlaLista.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        szamlaLista.getSelectionModel().selectLast();
        szamlaLista.scrollTo(utolso);
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
        utolso = AdatKezelo.getInstance().getRezsiAdatok().get(utolsoElemSzam());
        initialize();
    }

    public void PDFkiiro() throws IOException{
        List<String> sorok = new ArrayList<>();
        Oraallas elem = szamlaLista.getSelectionModel().getSelectedItem();
        PDDocument document = new  PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
//        PDFont font = PDType0Font.load(document, new File("src/main/resources/cambriab.ttf"));
        PDFont font = PDType0Font.load(document, new File("C:/Windows/fonts/cambriab.ttf"));
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.newLineAtOffset(45, 700);
        contentStream.setFont(font,18);
        contentStream.setLeading(22.5f);//ez a soremeles, enelkul egymasra irja a sorokat
        contentStream.showText("Albérleti elszámolás " + elem.getEv() + ". " + elem.getHonap() + ". hónap");
        contentStream.newLine();
        contentStream.newLine();
        contentStream.setFont(font,14);

        try
        {
            Scanner sc = new Scanner(Oraallas.reszletesSzoveg(elem));
            while (sc.hasNextLine())
            {
                contentStream.newLine();
                contentStream.showText(sc.nextLine());
            }
            sc.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        contentStream.endText();
        contentStream.close();
        String mentes = "D:/Komlossy62_" + elem.getEv() + "_" + elem.getHonap() + ".pdf";
        document.save(new File(mentes));
        document.close();
    }

    public void megnyitAdatAblak(ActionEvent event) throws IOException {
        Main.ablakBeallito("AdatAblakFXML");
        Main.getStage().setTitle("Rezsi adatok hozzáadása"); //Amit nem allitok at, az ugyanaz marad, mint amit a startban beallitok
    }

    public void torlesBiztos() {
        Oraallas elem = szamlaLista.getSelectionModel().getSelectedItem();
        if (!(elem.getHonap() == utolso.getHonap())){
            Alert nemUtolsoAlert = new Alert(Alert.AlertType.WARNING);
            ((Button) nemUtolsoAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("Megértettem");
            nemUtolsoAlert.setTitle("Nem törölhető");
            nemUtolsoAlert.setHeaderText("Csak a legutolsó hónap törölhető!");
            nemUtolsoAlert.setContentText("Nem változtathatjuk meg visszamenőleg a régi adatokat!");
            nemUtolsoAlert.getDialogPane().getScene().getStylesheets().add("alertCSS.css");
            Optional<ButtonType> result = nemUtolsoAlert.showAndWait();

        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Teljesen biztos");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nem");

            alert.setTitle("Törlés megerősítése");
            alert.setHeaderText("Biztos, hogy törölni akarja ezeket az adatokat?");
            alert.setContentText("Biztos?");
            alert.getDialogPane().getScene().getStylesheets().add("alertCSS.css");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                torlesGomb();
            } else {
                return;
            }
        }
    }

    private int utolsoElemSzam() {
        return AdatKezelo.getInstance().getRezsiAdatok().size() - 1;
    }

}
