/*
 * Tämä sivu sisältää uuden paketin luomisen.
 */
package javafxkesaharkkatyo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author n4121
 */
public class FXMLpackagesController implements Initializable {

    Middleclass m = new Middleclass();

    @FXML
    private ChoiceBox<String> itemsChoiceBox;
    @FXML
    private TextField itemnamefield;
    private TextField itemsizefield;
    @FXML
    private TextField itemweightfield;
    @FXML
    private ChoiceBox<Integer> packageclassChoiceBox;
    @FXML
    private Button classinfoButton;
    @FXML
    private Button confCreatePackageButton;
    @FXML
    private Font x1;
    @FXML
    private CheckBox breakableCheck;
    @FXML
    private Button addNewItemButton;
    @FXML
    private Button removeItemButton;
    @FXML
    private ChoiceBox<String> startLocationChoiceBox;
    @FXML
    private ChoiceBox<String> endLocationChoiceBox;
    @FXML
    private Label errorlabel;
    @FXML
    private WebView webview;
    @FXML
    private TextField itemsizefield1;
    @FXML
    private TextField itemsizefield2;
    @FXML
    private TextField itemsizefield3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webview.getEngine().load(getClass().getResource("index.html").toExternalForm());
        m.packageDockInitialazion();
        
        itemsChoiceBox.setItems(m.getItemsoblist());
        itemsChoiceBox.getSelectionModel().selectFirst();

        startLocationChoiceBox.setItems(m.getCreatedPostoblist());
        startLocationChoiceBox.getSelectionModel().selectFirst();
        endLocationChoiceBox.setItems(m.getCreatedPostoblist());
        endLocationChoiceBox.getSelectionModel().selectFirst();

        packageclassChoiceBox.setItems(m.getPackageClassoblist());
        packageclassChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    /**
     * Avaa sivun pakettiluokkien tiedoista.
     */
    private void classinfoAction(ActionEvent event) {
        errorlabel.setText("");
        try {
            Stage classinfo = new Stage();

            Parent page2 = FXMLLoader.load(getClass().getResource("FXMLclassinfo.fxml"));

            Scene scene2 = new Scene(page2);

            classinfo.setScene(scene2);
            classinfo.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLpackagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    /**
     * Luo paketin valituilla arvoilla kertoo ettei pakettia voitu tehdä.
     */
    private void confCreatePackageAction(ActionEvent event) {
        errorlabel.setText("");
        String distance = "";
        String item = itemsChoiceBox.getValue();
        int packclass = packageclassChoiceBox.getValue();
        String startpoint = startLocationChoiceBox.getValue();
        String endpoint = endLocationChoiceBox.getValue();
        if (startpoint != null && endpoint != null) {
            distance = webview.getEngine().executeScript(
                    "document.distance(" + m.getCoordinatesList(startpoint, endpoint) + ")").toString();
        }
        if (item != null) {
            switch (m.checkCompatibility(item, packclass, startpoint, endpoint)) {
                case 0:
                    m.packageCreation(item, packclass, startpoint, endpoint, distance);
                    Stage stage = (Stage) confCreatePackageButton.getScene().getWindow();
                    stage.close();
                    break;
                case 2:
                    System.out.println("pakettia ei voi tehdä");
                    errorlabel.setText("Pakettia ei voida tehdä, "
                            + "koska sillä ei ole lähtöpaikkaa tai määränpäätä.");
                    break;
                default:
                    System.out.println("pakettia ei voi tehdä");
                    errorlabel.setText("Pakettia ei voida tehdä. Esine on liian painava tai suuri.");
                    break;
            }
        } else {
            errorlabel.setText("Esinettä ei ole valittu.");
        }
    }

    @FXML
    /**
     * Lisää uuden esineen.
     */
    private void addNewItemAction(ActionEvent event) {
        errorlabel.setText("");
        String name = itemnamefield.getText();
        String size1 = itemsizefield1.getText();
        String size2 = itemsizefield2.getText();
        String size3 = itemsizefield3.getText();
        String weight = itemweightfield.getText();
        boolean breaks = breakableCheck.isSelected();
        if (name.isEmpty() || size1.isEmpty() || size2.isEmpty()
                || size3.isEmpty() || weight.isEmpty()) {
            errorlabel.setText("Esineelle ei ole annettu kaikkia arvoja.");
        } else {
            String errortext = m.addItem(name, size1, size2, size3, weight, breaks);
            itemsChoiceBox.getSelectionModel().selectFirst();
            errorlabel.setText(errortext);
        }
        itemnamefield.clear();
        itemsizefield1.clear();
        itemsizefield2.clear();
        itemsizefield3.clear();
        itemweightfield.clear();
    }

    @FXML
    /**
     * Poistaa esineen.
     */
    private void removeItemAction(ActionEvent event) {
        errorlabel.setText("");
        String item = itemsChoiceBox.getValue();
        m.deleteItem(item);
        itemsChoiceBox.getSelectionModel().selectFirst();
    }

}
