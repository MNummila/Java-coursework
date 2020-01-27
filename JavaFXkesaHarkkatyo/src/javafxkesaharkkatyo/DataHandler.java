/*
 * Tämä luokka sisältää kaikki yleiset listat
 */
package javafxkesaharkkatyo;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author n4121
 */
public class DataHandler {

    public static ArrayList<Item> itemlist = new ArrayList();
    public static ObservableList<String> cityoblist = FXCollections.observableArrayList();
    public static ArrayList<SmartPost> smartlist = new ArrayList();
    public static ObservableList<String> itemoblist = FXCollections.observableArrayList();
    public static ObservableList<Integer> packageclassoblist = FXCollections.observableArrayList();
    public static ArrayList<String> addtomaplist = new ArrayList();
    public static ArrayList<String> toscriptlist = new ArrayList();
    public static ArrayList<Integer> idlist = new ArrayList();
    public static ObservableList<String> createdpostsoblist = FXCollections.observableArrayList();
}
