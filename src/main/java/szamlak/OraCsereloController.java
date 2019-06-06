package szamlak;

import javafx.event.ActionEvent;

public class OraCsereloController {

    public void visszaAdatablakra(ActionEvent event) throws Exception {
        Main.ablakBeallito("AdatablakFXML");
        Main.getStage().setTitle("Rezsi adatok hozzáadása");
    }

}
