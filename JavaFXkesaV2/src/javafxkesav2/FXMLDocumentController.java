/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.text.Font;

/**
 *
 * @author n4121
 */
public class FXMLDocumentController implements Initializable {
    BottleDispenser bd = BottleDispenser.getInstance();
    Bottle latestbottle;
    
    @FXML
    private TextArea textArea;
    @FXML
    private Button addMoneyButton;
    @FXML
    private Button buySodaButton;
    @FXML
    private Font x1;
    @FXML
    private Button moneyOutButton;
    @FXML
    private Slider moneySlider;
    @FXML
    private ComboBox<String> sodaNameBox;
    @FXML
    private ComboBox<String> sodaSizeBox;
    @FXML
    private Label showMoneyLabel;
    @FXML
    private Button showBottlesbutton;
    @FXML
    private Button printReceiptButtob;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showMoneyLabel.setText("Rahee konees: "+bd.checkMoney());
    }    
    
    @FXML
    private void addMoneyAction(ActionEvent event) {
        double d = moneySlider.getValue();
        bd.addMoney(d);
        textArea.setText("Klink! Lisätty rahaa laitteeseen "+d+" verran!");
        moneySlider.setValue(0);
        showMoneyLabel.setText("Rahee konees: "+bd.checkMoney());
    }

    @FXML
    private void buySodaAction(ActionEvent event) {
        String nimi = sodaNameBox.getValue();
        String koko = sodaSizeBox.getValue();
        
        System.out.println(nimi);
        System.out.println(koko);
        System.out.println(bd.pullot.size());
        if(bd.checkMoney()!=0){
            for (Bottle bot : bd.pullot) {
                if(bot.getName().equals(nimi)==true){
                    if(bot.getSize().equals(koko)==true){
                        textArea.setText("KACHUNK! "+bot.getName()+" tipahti masiinasta!");
                        bd.buyBottle(nimi, bot.getPrice());
                        latestbottle = bot;
                        bd.pullot.remove(bot);
                        break;
                    }
                    else {
                        textArea.setText("Tuotetta ei ole.");
                        
                    }
                }
                else{
                    textArea.setText("Tuotetta ei ole.");
                    
                }
            }
        }else{
            textArea.setText("Lisää rahaa koneeseen.");
        }
        double mone = bd.checkMoney();
        mone = (double)Math.round(mone * 100d) / 100d;
        showMoneyLabel.setText("Rahee konees: "+mone);      
    }

    @FXML
    private void moneyOutAction(ActionEvent event) {
        String s = bd.returnMoney();
        textArea.setText(s);
        showMoneyLabel.setText("Rahee konees: "+bd.checkMoney());
    }

    @FXML
    private double moneySliderAction(DragEvent event) {
         double d = moneySlider.getMajorTickUnit();
         return d;
    }

    @FXML
    private void showBottlesAction(ActionEvent event) {
        textArea.clear();
        for(Bottle bb : bd.pullot){
            textArea.appendText("Nimi: "+bb.getName()+"\tkoko: "+bb.getSize()+"\thinta: "+bb.getPrice()+"\n");   
        }
    }

    @FXML
    private void printReceiptAction(ActionEvent event) {
        try(BufferedWriter out = new BufferedWriter(new FileWriter("kuitti.txt"))) {
            out.write("Tämä on kuitti viimeisimmästä ostoksesta.\n");
            out.write("\nNimi:\t"+latestbottle.getName()+"\thinta:\t"+latestbottle.getPrice());
        }catch (IOException i){
        }
    }
    
}
