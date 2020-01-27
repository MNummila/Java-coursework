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

public class Mainclass {
    public static void main(String[] args) {
        String s1 = "";
        String s2 = "";
            
        
            System.out.print("Anna koiralle nimi: ");
            Scanner scan = new Scanner(System.in);
            s1 = scan.next();
        
            Dog d1 = new Dog(s1);
           
            
        while (s2.trim().isEmpty()==true){    
            System.out.print("MitÃ¤ koira sanoo: ");
            Scanner scan2 = new Scanner(System.in);
            s2 = scan2.nextLine();
            
            d1.speak(s2);
        }
            
    }
}