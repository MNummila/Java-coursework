/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav4;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

/**
 *
 * @author n4121
 */
public class FXMLDocumentController implements Initializable {
    int listindex = 0;
    //int lastpage = 0;
    //int nextPage = 0;
    String currentpage;
    //String previouspage;
    //String nextpage;
    ArrayList<String> pagelist = new ArrayList<>();
    ObservableList<String> oblist = FXCollections.observableArrayList();
    //Iterator itr = pagelist.iterator();
    //ListIterator litr = pagelist.listIterator();
    
    private Label label;
    @FXML
    private Button loadButton;
    @FXML
    private Button drawButton;
    @FXML
    private ComboBox<String> urlComboBox;
    @FXML
    private Button refreshButton;
    @FXML
    private WebView web;
    @FXML
    private Button goToButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        web.getEngine().load("http://www.google.com");
        currentpage = "http://www.google.com";
        oblist.add(currentpage);
        urlComboBox.setItems(oblist);
        urlComboBox.getSelectionModel().selectFirst();
        pagelist.add(currentpage);
        
    }    

    @FXML
    private void loadButtonAction(ActionEvent event) {
        web.getEngine().executeScript("initialize()");
    }

    @FXML
    private void drawButtonAction(ActionEvent event) {
        web.getEngine().executeScript("document.shoutOut()");
    }

    @FXML
    private void refreshButtonAction(ActionEvent event) {
        web.getEngine().reload();
        
    }

    @FXML
    private void goToButtonAction(ActionEvent event) {
        if(pagelist.size() == 10){
            pagelist.remove(0);
            listindex--;
        }
        while(pagelist.size()-1>listindex){
            pagelist.remove(listindex+1);
        }
        listindex++;
        String urli = urlComboBox.getValue();
        if(urli.equalsIgnoreCase("index.html")){
            web.getEngine().load(getClass().getResource("index.html").toExternalForm());
        }
        else{
            if(urli.startsWith("http://")==false){
                urli = "http://"+urli;
            }
            web.getEngine().load(urli);
            currentpage = urli;
            oblist.add(currentpage);
        }
        pagelist.add(urli);
        System.out.println(pagelist);
        System.out.println("\t"+listindex);
    }

    @FXML
    private void prevButtonAction(ActionEvent event) {
        if(listindex > 0){
            listindex--;
            String urli = pagelist.get(listindex);
            web.getEngine().load(urli);
            System.out.print(urli);
            System.out.println("\t"+listindex);
        }
    }

    @FXML
    private void nextButtonAction(ActionEvent event) {
        if (pagelist.size()-2>=listindex){
            listindex++;
            String urli = pagelist.get(listindex);
            web.getEngine().load(urli);
            System.out.print(urli);
            System.out.println("\t"+listindex);
        }
        else {
            System.out.println("Viimeisin sivu");
        }
    }

    @FXML
    private void urlComboxAction(ActionEvent event) {

    }
}