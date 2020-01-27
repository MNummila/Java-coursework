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
import java.util.Scanner;

public class Mainclass {
    private static int valinta = 1;
    private static int number = 0;
    public static void main(String[] args){
        
        BottleDispenser b1 = BottleDispenser.getInstance();
        Bottle bb1 = new Bottle("Pepsi Max",0.5f,1.8f);
        Bottle bb2 = new Bottle("Pepsi Max",1.5f,2.2f);
        Bottle bb3 = new Bottle("Coca-Cola Zero",0.5f,2.0f);
        Bottle bb4 = new Bottle("Coca-Cola Zero",1.5f,2.5f);
        Bottle bb5 = new Bottle("Fanta Zero",0.5f,1.95f);
        Bottle bb6 = new Bottle("Fanta Zero",0.5f,1.95f);

        b1.pullot.add(bb1);
        b1.pullot.add(bb2);
        b1.pullot.add(bb3);
        b1.pullot.add(bb4);
        b1.pullot.add(bb5);
        b1.pullot.add(bb6);
        
        while(valinta != 0){
            System.out.println("");
            System.out.println("*** LIMSA-AUTOMAATTI ***");
            System.out.println("1) LisÃ¤Ã¤ rahaa koneeseen");
            System.out.println("2) Osta pullo");
            System.out.println("3) Ota rahat ulos");
            System.out.println("4) Listaa koneessa olevat pullot");
            System.out.println("0) Lopeta");
            System.out.print("Valintasi: ");
            Scanner scan = new Scanner(System.in);
            valinta = scan.nextInt();
        
            if (valinta == 1){
                b1.addMoney();
            }
            else if (valinta == 2){
                b1.printBottle();
                System.out.print("Valintasi: ");
                Scanner scan1 = new Scanner(System.in);
                number = scan1.nextInt();
                b1.buyBottle((b1.pullot.get(number-1)).getName(),(b1.pullot.get(number-1)).getPrice(),number-1);
                
            }
            else if (valinta == 3){
                b1.returnMoney();
            }
            else if (valinta == 4){
                b1.printBottle();
            }
            else if (valinta == 0){
                break;
            }
        }
    }
}
