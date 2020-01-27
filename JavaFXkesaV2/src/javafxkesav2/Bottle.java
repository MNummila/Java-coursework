/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav2;

/**
 *
 * @author n4121
 */
public class Bottle {
    private String name = "Pepsi Max";
    private String manufacturer = "Pepsi";
    private float energy = 0.3f;
    private String size = "0.5";
    private float price = 1.80f;

    public Bottle(String x, String a, float b){
        name = x;
        //manufacturer = y;
        //energy = z;
        size = a;
        price = b;
    }
    public String getName(){
        return name;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public float getEnergy(){
        return energy;
    }
    public float getPrice(){
        return price;
    }
    public String getSize(){
        return size;
    }
    
}
