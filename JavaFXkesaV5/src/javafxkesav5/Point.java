/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav5;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author n4121
 */
public class Point {

    String name;

    public Circle Point(double x, double y) {
        Circle c = new Circle();
        c.setCenterX(x);
        c.setCenterY(y);
        c.setRadius(10);
        c.setFill(Color.BLUE);

        System.out.println(x + "\t" + y);
        ShapeHandler.getInstance().dotlist.add(this);

        c.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Hei olen piste!");

                ShapeHandler.getInstance().doublelist.add(((Circle) event.getTarget()).getCenterX());
                ShapeHandler.getInstance().doublelist.add(((Circle) event.getTarget()).getCenterY());

                event.consume();
                System.out.println("consume");

            }
        });
        return c;
    }

    public void Lines(double startx, double starty, double endx, double endy) {
        Line line = new Line();
        line.setStartX(startx);
        line.setStartY(starty);
        line.setEndX(endx);
        line.setEndY(endy);
        line.setStrokeWidth(5);
        ShapeHandler.getInstance().linelist.add(line);
        System.out.println(ShapeHandler.getInstance().linelist);
        System.out.println("Viiva");

    }
}
