/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author n4121
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField inputField;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;
    @FXML
    private TextArea textField;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SaveButtonAction(ActionEvent event) {
        String text = textField.getText();
        String filename = inputField.getText();
        try(BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            out.write(text);
        }catch (IOException i){
        }
        inputField.clear();
        textField.clear();
    }

    @FXML
    private void LoadButtonAction(ActionEvent event) {
        String filename = inputField.getText();
        String s;
        try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
            while(( s = in.readLine()) != null) {
                textField.appendText(s+"\n");
            }
            in.close();
        }catch(IOException i){
        }
    }
    
}
