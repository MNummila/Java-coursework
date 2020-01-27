/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesaharkkatyo;

/**
 *
 * @author n4121
 */
public class Item {

    private final int Itemid;
    private final String ItemName;
    private final int ItemSize;
    private final int ItemWeight;
    private final boolean ItemBreaks;

    public Item(int id, String name, int size, int weight, boolean breaks) {
        Itemid = id;
        ItemName = name;
        ItemSize = size;
        ItemWeight = weight;
        ItemBreaks = breaks;
    }

    public int getItemid() {
        return Itemid;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getItemSize() {
        return ItemSize;
    }

    public int getItemWeight() {
        return ItemWeight;
    }

    public boolean getItemBreaks() {
        return ItemBreaks;
    }
}
