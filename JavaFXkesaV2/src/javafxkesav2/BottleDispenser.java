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
import java.util.ArrayList;
        
public class BottleDispenser {
    public ArrayList<Bottle> pullot = new ArrayList();
        
    private int bottles;
    private double money;
        
    //tehtävän V8 T1 osio alkaa tästä
    private BottleDispenser() {
        bottles = 50;
        money = 0.0f;
        
        Bottle bb1 = new Bottle("Pepsi Max","0.5",1.8f);
        Bottle bb2 = new Bottle("Pepsi Max","1.5",2.2f);
        Bottle bb3 = new Bottle("Coca-Cola Zero","0.5",2.0f);
        Bottle bb4 = new Bottle("Coca-Cola Zero","1.5",2.5f);
        Bottle bb5 = new Bottle("Fanta Zero","0.5",1.95f);
        Bottle bb6 = new Bottle("Fanta Zero","0.5",1.95f);
        
        pullot.add(bb1);
        pullot.add(bb2);
        pullot.add(bb3);
        pullot.add(bb4);
        pullot.add(bb5);
        pullot.add(bb6);
    }
        
    static private BottleDispenser bo = null;
    static BottleDispenser getInstance(){
        if (bo == null){
            bo = new BottleDispenser();
        }
        return bo;
    }
    //tehtävän V8 T1 osio loppuu tähän
    public Bottle getbottle(int i){
        return pullot.get(i);
    }
    public void addBottle(Bottle i){
        pullot.add(i);
    }
    public void removeBottle(int i){
        pullot.remove(i);
    }
    public void printBottle(){
        for (int i = 0; i<pullot.size(); i++){
            System.out.println(i+1+". Nimi: " + (pullot.get(i)).getName()); 
            System.out.println("\tKoko: " + (pullot.get(i)).getSize() +"\tHinta: " + (pullot.get(i)).getPrice());
        }
    }
            

    public void addMoney(double d) {
        money += d;
        System.out.println("Klink! LisÃ¤Ã¤ rahaa laitteeseen!");
    }
    public String buyBottle(String x,float y) {
        if (money <= y){
            return "Syötä rahaa ensin!";
        }
        else if (bottles <= 0){
            return "Pullot loppu!";
        }
        else{
            bottles -= 1;
            money -= y;
            //pullot.remove(i);
            return "KACHUNK! "+x+" tipahti masiinasta!";
        }
    }
    public String returnMoney() {
        money = (double)Math.round(money * 100d) / 100d;
        System.out.printf("Klink klink. Sinne menivät rahat! Rahaa tuli ulos %.2f\n",money);
        double money2 = money;
        money = 0;
        return "Klink klink. Sinne menivät rahat! Rahaa tuli ulos "+money2;
        
    }
    public double checkMoney(){
        return money;
    }
}

