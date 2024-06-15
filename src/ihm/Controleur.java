package ihm;

import java.io.File;

import App.CSVUtil;
import App.Plateforme;
import fr.ulille.but.sae_s2_2024.Lieu;
import graph.TypeCout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controleur {
    private Plateforme p = null;

    @FXML private ChoiceBox<Lieu> villeDepart;
    @FXML private ChoiceBox<Lieu> villeArrive;

    @FXML private CheckBox allModality;
    @FXML private CheckBox busCheckBox;
    @FXML private CheckBox trainCheckBox;
    @FXML private CheckBox avionCheckBox;
    
    @FXML private ChoiceBox<TypeCout> critere1;
    @FXML private ChoiceBox<TypeCout> critere2;
    @FXML private ChoiceBox<TypeCout> critere3;

    @FXML private Button fileButton;
    @FXML private Button annulation;
    @FXML private Button validation;

    @FXML
    private void initialize(){
        System.out.println("Initialize...");
        ObservableList<Lieu> villes = FXCollections.observableArrayList(Plateforme.lieux);
        villeDepart.setItems(villes);
        villeArrive.setItems(villes);
        if(!villes.isEmpty()){
            villeDepart.setValue(Plateforme.lieux.get(0));
            villeArrive.setValue(Plateforme.lieux.get(0));
        }
    }
    
    @FXML
    void Importation(){
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Selectionné un fichier");
        File selectedFile = fileChoose.showOpenDialog(new Stage());
        if(selectedFile == null){
            p = new Plateforme(CSVUtil.importCSV());
        } else {
            p = new Plateforme(CSVUtil.importCSV(selectedFile));
        }
        initialize();
    }

    @FXML
    void AllValidation(ActionEvent event){
        boolean etat = false;
        if(allModality.selectedProperty().get()){
            etat = true;
        }
        busCheckBox.setSelected(etat);
        trainCheckBox.setSelected(etat);
        avionCheckBox.setSelected(etat);
    }
}
