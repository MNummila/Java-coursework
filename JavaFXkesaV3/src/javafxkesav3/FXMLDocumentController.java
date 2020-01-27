/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author n4121
 */
public class FXMLDocumentController implements Initializable {
    WebDoc wd = new WebDoc();
    
    @FXML
    private TextField showingDayField;
    @FXML
    private TextField startingTimeField;
    @FXML
    private TextField endingTimeField;
    @FXML
    private Button listFilmsButton;
    @FXML
    private TextField filmNameField;
    @FXML
    private Button searcNameButton;
    @FXML
    private ChoiceBox<String> chooseTheatreBox;
    @FXML
    private ListView<String> listViewArea;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> oblist;
        wd.parseTheatreData(wd.docBuilderi(wd.getXML("https://www.finnkino.fi/xml/TheatreAreas/")));
        oblist = wd.arrayToObservableListTheatre();
        chooseTheatreBox.setItems(oblist);
        chooseTheatreBox.getSelectionModel().selectFirst();
        
    }    

    @FXML
    private void listFilmsAction(ActionEvent event){
        String chosen = "";
        String urli;
        for(Theatre tre : wd.theatrelist){
            if(tre.getName().equals(chooseTheatreBox.getValue())){
                chosen = tre.getId();
                break;
            }
        }
        wd.filmlist.clear();
        String date = showingDayField.getText();
        urli = "http://www.finnkino.fi/xml/Schedule/?area="+chosen+"&dt="+date;
        wd.parseByTheatreAndDayData(wd.docBuilderi(wd.getXML(urli)));
        String start;
        String end;
        if(startingTimeField.getText().equals("")){
            start = "00:00";    
        }else{
            start = startingTimeField.getText();
        }
        if(endingTimeField.getText().equals("")){
            end = "24:00";
        }else{
            end = endingTimeField.getText();
        }
        wd.checkTimes(start, end);
        wd.combineStrings(0);
        ObservableList<String> oblist;
        oblist = wd.arrayToObservableListTimes();
        listViewArea.setItems(oblist);
    }

    @FXML
    private void searchNameAction(ActionEvent event) {
        String date = showingDayField.getText();
        String search = filmNameField.getText();
        String start;
        String end;
        if(startingTimeField.getText().equals("")){
            start = "00:00";    
        }else{
            start = startingTimeField.getText();
        }
        if(endingTimeField.getText().equals("")){
            end = "24:00";
        }else{
            end = endingTimeField.getText();
        }
        String chosen;
        String urli;
        wd.filmlist.clear();
        chooseTheatreBox.getSelectionModel().selectFirst();
        for (Theatre tr : wd.theatrelist){
            chosen  = tr.getId();
            urli = "http://www.finnkino.fi/xml/Schedule/?area="+chosen+"&dt="+date;
            wd.parseByTheatreAndDayData(wd.docBuilderi(wd.getXML(urli)));
        }
        wd.checkNames(search);
        wd.checkTimes(start, end);
        filmNameField.setText(wd.filmlist.get(0).getTitle());
        wd.combineStrings(1);
        ObservableList<String> oblist;
        oblist = wd.arrayToObservableListTimes();
        listViewArea.setItems(oblist);
    }
    
}
