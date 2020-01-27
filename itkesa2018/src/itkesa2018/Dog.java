/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itkesa2018;

/**
 *
 * @author n4121
 */
import java.util.Scanner;
public class Dog {
    private String name = "Doge";
    private final String shout = "Much wow!";
    
    
    public Dog(String x) {
                
        if(x.trim().isEmpty()==false){
            name = x;
        }
        System.out.println("Hei, nimeni on "+ name + "!");
    }
    public void speak(String x){
        Scanner scan = new Scanner(x);
        while (scan.hasNext()) {
            if (scan.hasNextInt()){
                System.out.println("Such integer: "+scan.next());
                continue;
            }
            if (scan.hasNextBoolean()){
                System.out.println("Such boolean: "+scan.next());
                continue;
            }
            System.out.println(scan.next());
        }
        }
    
}
