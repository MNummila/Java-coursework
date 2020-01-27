/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav5;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author n4121
 */
public class FXMLDocumentController implements Initializable {

    int i = 0;

    @FXML
    private AnchorPane anchorpane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void mouseClickAction(MouseEvent event) {
        double x1;
        double x2;
        double y1;
        double y2;

        Point p = new Point();
        anchorpane.getChildren().add(p.Point(event.getX(), event.getY()));
        System.out.println(ShapeHandler.getInstance().doublelist);
        if (ShapeHandler.getInstance().doublelist.size() > 3) {
            if (ShapeHandler.getInstance().doublelist.size() % 4 == 0 || ShapeHandler.getInstance().doublelist.size()>4) {
                x1 = ShapeHandler.getInstance().doublelist.get(0);
                y1 = ShapeHandler.getInstance().doublelist.get(1);
                x2 = ShapeHandler.getInstance().doublelist.get(2);
                y2 = ShapeHandler.getInstance().doublelist.get(3);
                ShapeHandler.getInstance().doublelist.clear();
                p.Lines(x1, y1, x2, y2);
            //    if(ShapeHandler.getInstance().linelist.size()<=1){
                    anchorpane.getChildren().add(ShapeHandler.getInstance().linelist.get(i));
                    anchorpane.getChildren().remove(anchorpane.getChildren().size()-2);
           /*     } ///////poista kommentit niin kartalla näkyy aina vain yksi.
                else{
                    anchorpane.getChildren().remove(ShapeHandler.getInstance().linelist.get(0));
                    ShapeHandler.getInstance().linelist.remove(0);
                    anchorpane.getChildren().add(ShapeHandler.getInstance().linelist.get(0));
                }
           */     System.out.println("VIIIVAAAA");
                i++;
            }
        }
   
    }
}
