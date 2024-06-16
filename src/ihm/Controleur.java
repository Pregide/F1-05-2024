package ihm;

import java.io.File;
import java.util.ArrayList;

import App.CSVUtil;
import App.Plateforme;
import App.exception.NoTravelFoundException;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import graph.TypeCout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controleur {
    private Plateforme p = null;
    private ObservableList<Lieu> villes;

    @FXML private ChoiceBox<Lieu> villeDepart;
    @FXML private ChoiceBox<Lieu> villeArrive;
    @FXML private CheckBox allModality;
    @FXML private CheckBox busCheckBox;
    @FXML private CheckBox trainCheckBox;
    @FXML private CheckBox avionCheckBox;
    @FXML private ChoiceBox<String> critere1;
    @FXML private ChoiceBox<String> critere2;
    @FXML private ChoiceBox<String> critere3;
    @FXML private TextField limite;
    @FXML private TextField nbTrajet;
    @FXML private Button annulation;
    @FXML private Button validation;

    @FXML private ListView<String> listChemin;

    @FXML
    private void initialize(){
        Importation();
        villes = FXCollections.observableArrayList(Plateforme.lieux);
        villeDepart.setItems(villes);
        villeArrive.setItems(villes);
        if(!villes.isEmpty()){
            villeDepart.setValue(Plateforme.lieux.get(0));
            villeArrive.setValue(Plateforme.lieux.get(0));
        }

        ObservableList<String> couts = FXCollections.observableArrayList("", "Pollution", "Temps", "Prix");
        critere1.setItems(couts);
        critere2.setItems(couts);
        critere3.setItems(couts);

        limite.setText("100");
        nbTrajet.setText("10");
    }
    
    void Importation(){
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Selectionné un fichier");
        File selectedFile = fileChoose.showOpenDialog(new Stage());
        if(selectedFile == null){
            p = new Plateforme(CSVUtil.importCSV());
        } else {
            p = new Plateforme(CSVUtil.importCSV(selectedFile));
        }
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

    @FXML
    void Annulation(ActionEvent event){
        allModality.setSelected(false);
        busCheckBox.setSelected(false);
        trainCheckBox.setSelected(false);
        avionCheckBox.setSelected(false);
        limite.setText("");
        nbTrajet.setText("");
    }

    @FXML
    void Validation(){
        listChemin.getItems().clear();
        changeCritere();
        int nb = 10;
        int lim = 1000;
        try{
            nb = Integer.parseInt(nbTrajet.getText());
            lim = Integer.parseInt(limite.getText());
        } catch (NumberFormatException e){}
        try {
            ObservableList<String> trajets = FXCollections.observableArrayList(p.trajet(villeDepart.getValue(), villeArrive.getValue(), getModality(), nb, lim));
            listChemin.getItems().addAll(trajets);
        } catch (NoTravelFoundException e){
            listChemin.getItems().add("No Travel Possible");
        }
    }

    public ArrayList<ModaliteTransport> getModality(){
        ArrayList<ModaliteTransport> moda = new ArrayList<>();
        if(busCheckBox.selectedProperty().get()) moda.add(ModaliteTransport.BUS);
        if(avionCheckBox.selectedProperty().get()) moda.add(ModaliteTransport.AVION);
        if(trainCheckBox.selectedProperty().get()) moda.add(ModaliteTransport.TRAIN);
        return moda;
    }

    public void changeCritere(){
        ArrayList<TypeCout> crit = new ArrayList<>();
        if(isCout(critere1.getValue()) && isCout(critere2.getValue()) && isCout(critere3.getValue())){
            crit.add(getCout(critere1.getValue()));
            crit.add(getCout(critere2.getValue()));
            crit.add(getCout(critere3.getValue()));
        } else {
            for(TypeCout cout : TypeCout.values()){
                crit.add(cout);
            }
        }
        p.changeCritère(crit);
    }

    public boolean isCout(String str) {
        try {
            return str.equals("Pollution") || str.equals("Temps") || str.equals("Prix");
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    public TypeCout getCout(String str){
        if(str.equals("Pollution")) return TypeCout.CO2;
        if(str.equals("Temps")) return TypeCout.TEMPS;
        return TypeCout.valueOf(str.toUpperCase());
    }
}
