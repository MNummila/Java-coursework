/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav5;

import java.util.ArrayList;
import javafx.scene.shape.Line;

/**
 *
 * @author n4121
 */
public class ShapeHandler {

    public ArrayList<Point> dotlist = new ArrayList();
    public ArrayList<Line> linelist = new ArrayList();
    public ArrayList<Double> doublelist = new ArrayList();

    public void Shapehandler() {

    }
    static private ShapeHandler sh = null;

    static ShapeHandler getInstance() {
        if (sh == null) {
            sh = new ShapeHandler();
        }
        return sh;
    }
}
