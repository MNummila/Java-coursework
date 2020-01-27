/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itkesa2018.v2;

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
            

    public void addMoney() {
        money += 1.0;
        System.out.println("Klink! LisÃ¤Ã¤ rahaa laitteeseen!");
    }
    public void buyBottle(String x, float y,int i) {
        if (money <= y){
            System.out.println("SyÃ¶tÃ¤ rahaa ensin!");   
        }
        else if (bottles <= 0){
            System.out.println("Pullot loppu!");
        }
        else{
            bottles -= 1;
            money -= y;
            pullot.remove(i);
            System.out.println("KACHUNK! "+x+" tipahti masiinasta!");
        }
    }
    public void returnMoney() {
        money = (double)Math.round(money * 100d) / 100d;
        
        System.out.printf("Klink klink. Sinne menivÃ¤t rahat! Rahaa tuli ulos %.2fâ‚¬\n",money);
    }
    }

