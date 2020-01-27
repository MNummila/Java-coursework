/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesaharkkatyo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafxkesaharkkatyo.Middleclass.Createdpackage;

/**
 *
 * @author n4121
 */
public class FXMLDocumentController implements Initializable {

    Middleclass m = new Middleclass();

    @FXML
    private Button clearMapButton;
    @FXML
    private Button addToMapButton;
    @FXML
    private Button createPackage;
    @FXML
    private WebView webview;
    @FXML
    private Button sendPackageButton;
    @FXML
    private ChoiceBox<String> packageChoiceBox;
    @FXML
    private ChoiceBox<String> smartpostChoiceBox;
    @FXML
    private Button removePackageButton;
    @FXML
    private Label errorlabel;
    @FXML
    private TableView<SmartPost> tableview;
    @FXML
    private Button smartpostsButton;
    @FXML
    private Button packetsButton;
    @FXML
    private TableView<Createdpackage> tableviewPackages;
    @FXML
    private Button resetButton;
    @FXML
    private TableView<Item> tableviewtems;
    @FXML
    private Button itemsButton;
    @FXML
    private Label resetmessage;
    @FXML
    private TabPane tabi;
    @FXML
    private Label chooselabel;
    @FXML
    private Tab sendtab;
    @FXML
    private Tab logtab;
    @FXML
    private Tab startagaintab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webview.getEngine().load(getClass().getResource("index.html").toExternalForm());

        m.mainDocInitialazion();
        this.tableviewSmartpost();
        smartpostChoiceBox.setItems(m.getCityoblist());
        smartpostChoiceBox.getSelectionModel().selectFirst();
        packageChoiceBox.setItems(m.getCreatedPackageoblist());
        packageChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    /**
     * Poistaa reitit kartalta.
     */
    private void clearMapAction(ActionEvent event) {
        errorlabel.setText("");
        webview.getEngine().executeScript("document.deletePaths()");
    }

    @FXML
    /**
     * Lisää valitun kaupungin automaatit kartalle.
     */
    private void addToMapAction(ActionEvent event) {
        errorlabel.setText("");
        int u = 0;
        String postmat = smartpostChoiceBox.getValue();
        m.addMark(postmat);
        for (int i = 0; i < m.getAddToMapList().size() / 2; i++) {
            String address = m.getAddToMapList().get(u).toString();
            String otherinfo = m.getAddToMapList().get(u + 1).toString();
            webview.getEngine().executeScript("document.goToLocation('" + address
                    + "', '" + otherinfo + "', 'red')");
            u = u + 2;
        }
    }

    @FXML
    /**
     * Avaa uuden ikkunan jossa paketit luodaan.
     */
    private void createPackageAction(ActionEvent event) {
        errorlabel.setText("");
        try {
            Stage packages = new Stage();

            Parent page1 = FXMLLoader.load(getClass().getResource("FXMLpackages.fxml"));

            Scene scene1 = new Scene(page1);

            packages.setScene(scene1);
            scene1.getStylesheets().add(
                    JavaFXkesaHarkkatyo.class.getResource("cssStyle.css").toExternalForm());
            packages.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    /**
     * Lähettää valitun paketin.
     */
    private void sendPackageAction(ActionEvent event) {
        errorlabel.setText("");
        String pack = packageChoiceBox.getValue();
        if (pack != null) {
            int i = m.sendPackage(pack);
            String distance = webview.getEngine().executeScript("document.distance(" + m.getToScriptList() + ")").toString();
            float dist = m.stringToFloat(distance);

            if (i == 1 && dist > 150) {
                System.out.println("1. luokan paketin voi lähettää korkeintaan 150km päähän\n PAKETTI POISTETTU");
                m.createdpackdeletion(pack);
                errorlabel.setText("1. luokan paketin voi lähettää korkeintaan 150km päähän\n PAKETTI POISTETTU");
            } else {
                errorlabel.setText(m.packageSendingCheck(pack));
                System.out.println("piirretään slat: " + m.getToScriptList().get(0)
                        + " slng: " + m.getToScriptList().get(1)
                        + " elat: " + m.getToScriptList().get(2)
                        + " elng: " + m.getToScriptList().get(3)
                        + " luokka: " + i);
                webview.getEngine().executeScript("document.createPath("
                        + m.getToScriptList() + ", 'red', " + i + ")");
                packageChoiceBox.getSelectionModel().selectFirst();
                System.out.println(dist);
            }
        } else {
            errorlabel.setText("Pakettia ei ole valittu.");
        }
    }

    @FXML
    /**
     * Poistaa valitun paketin.
     */
    private void removePackageAction(ActionEvent event) {
        errorlabel.setText("");
        String pack = packageChoiceBox.getValue();
        if (pack != null) {
            m.createdpackdeletion(pack);
            packageChoiceBox.getSelectionModel().selectFirst();
        } else {
            errorlabel.setText("Pakettia ei ole valittu.");
        }
    }

    @FXML
    private void showSmartposts(ActionEvent event) {
        tableview.getColumns().clear();
        this.tableviewSmartpost();
    }

    @FXML
    private void showPackets(ActionEvent event) {
        tableviewPackages.getColumns().clear();
        this.tableviewCreatedpackage();
    }

    @FXML
    private void resetAction(ActionEvent event) {
        m.resetDB();
        webview.getEngine().reload();
        resetmessage.setText("Tietokanta resetoitu.");
    }

    @FXML
    private void showItems(ActionEvent event) {
        tableviewtems.getColumns().clear();
        this.tableviewItems();
    }
    
    private void tableviewSmartpost() {
        tableview.setVisible(true);
        tableviewPackages.setVisible(false);
        tableviewtems.setVisible(false);

        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(
                new PropertyValueFactory<SmartPost, Integer>("IdNumber"));

        TableColumn officeCol = new TableColumn("Toimisto");
        officeCol.setCellValueFactory(
                new PropertyValueFactory<SmartPost, String>("Postoffice"));

        TableColumn cityCol = new TableColumn("Kaupunki");
        cityCol.setCellValueFactory(
                new PropertyValueFactory<SmartPost, String>("City"));

        TableColumn addressCol = new TableColumn("Katuosoite");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<SmartPost, String>("Address"));

        TableColumn postcodeCol = new TableColumn("Postinumero");
        postcodeCol.setCellValueFactory(
                new PropertyValueFactory<SmartPost, String>("Postnumber"));

        TableColumn availabilityCol = new TableColumn("Avoinna");
        availabilityCol.setCellValueFactory(
                new PropertyValueFactory<SmartPost, String>("Availability"));

        TableColumn createdCol = new TableColumn("Luotu");
        createdCol.setCellValueFactory(
                new PropertyValueFactory<SmartPost, String>("Created"));

        tableview.setItems(m.getSmartpostData());
        tableview.getColumns().addAll(idCol, officeCol, availabilityCol,
                addressCol, cityCol, postcodeCol, createdCol);
    }

    private void tableviewCreatedpackage() {
        tableview.setVisible(false);
        tableviewPackages.setVisible(true);
        tableviewtems.setVisible(false);

        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, Integer>("Packageid"));

        TableColumn classCol = new TableColumn("Pakettiluokka");
        classCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, Integer>("Packageclass"));

        TableColumn itemCol = new TableColumn("Esine");
        itemCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, String>("Itemname"));

        TableColumn startCol = new TableColumn("Lähtöpaikka");
        startCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, String>("Startpoint"));

        TableColumn destCol = new TableColumn("Määränpää");
        destCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, String>("Destination"));

        TableColumn lenghtCol = new TableColumn("Matkan pituus km");
        lenghtCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, String>("Triplenght"));

        TableColumn locationCol = new TableColumn("Nykyinen sijainti");
        locationCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, String>("CurrentLocation"));

        TableColumn actionCol = new TableColumn("Tilanne");
        actionCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, String>("CurrentAction"));

        TableColumn timeCol = new TableColumn("Varastointi/Saapumisaika");
        timeCol.setCellValueFactory(
                new PropertyValueFactory<Createdpackage, String>("Time"));

        tableviewPackages.setItems(m.getCreatedPackageData());
        tableviewPackages.getColumns().addAll(idCol, classCol, itemCol,
                startCol, destCol, lenghtCol, locationCol, actionCol, timeCol);
    }

    private void tableviewItems() {
        tableviewtems.setVisible(true);
        tableview.setVisible(false);
        tableviewPackages.setVisible(false);

        TableColumn nameCol = new TableColumn("Esine");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("ItemName"));

        TableColumn sizeCol = new TableColumn("Koko dm3");
        sizeCol.setCellValueFactory(
                new PropertyValueFactory<Item, Integer>("ItemSize"));

        TableColumn weightCol = new TableColumn("Paino kg");
        weightCol.setCellValueFactory(
                new PropertyValueFactory<Item, Integer>("ItemWeight"));

        TableColumn breakCol = new TableColumn("Särkyvä");
        breakCol.setCellValueFactory(
                new PropertyValueFactory<Item, Boolean>("ItemBreaks"));

        tableviewtems.setItems(m.getItemData());
        tableviewtems.getColumns().addAll(nameCol, sizeCol, weightCol, breakCol);
    }

    @FXML
    private void startagainaction(Event event) {
        resetmessage.setText("Tyhjentää tietokannan ja alustaa sen uudelleen.");
    }
}