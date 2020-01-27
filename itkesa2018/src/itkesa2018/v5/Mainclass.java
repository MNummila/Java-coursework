/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itkesa2018.v5;

/**
 *
 * @author n4121
 */
import java.util.Scanner;

/**
 *
 * @author n4121
 */
public class Mainclass {
    private static int valinta = 1,valinta1 = 1,valinta2 = 1;
    public static void main(String[] args){
        Character ukko = null;
        while(valinta != 0){
            System.out.println("*** TAISTELUSIMULAATTORI ***");
            System.out.println("1) Luo hahmo");
            System.out.println("2) Taistele hahmolla");
            System.out.println("0) Lopeta");
            System.out.print("Valintasi: ");
            Scanner scan = new Scanner(System.in);
            valinta = scan.nextInt();
            switch(valinta){
                case 1:
                    System.out.println("Valitse hahmosi: ");
                    System.out.println("1) Kuningas");
                    System.out.println("2) Ritari");
                    System.out.println("3) Kuningatar");
                    System.out.println("4) Peikko");
                    System.out.print("Valintasi: ");
                    Scanner scan1 = new Scanner(System.in);
                    valinta1 = scan1.nextInt();
                    switch(valinta1){
                        case 1:
                            ukko = new King();
                            PickWeapon(ukko);
                            break;
                        case 2:
                            ukko = new Knight();
                            PickWeapon(ukko);
                            break;
                        case 3:
                            ukko = new Queen();
                            PickWeapon(ukko);
                            break;
                        case 4:
                            ukko = new Troll();
                            PickWeapon(ukko);
                            break;
                    }
                    break;
                case 2:
                    ukko.fight();
                    break;
                case 0:
                    break;   
            }
        }
    }
    public static Character PickWeapon(Character ukko){
        System.out.println("Valitse aseesi: ");
        System.out.println("1) Veitsi");
        System.out.println("2) Kirves");
        System.out.println("3) Miekka");
        System.out.println("4) Nuija");
        System.out.print("Valintasi: ");
        Scanner scan2 = new Scanner(System.in);
        valinta2 = scan2.nextInt();
        switch(valinta2){
            case 1:
                ukko.weapon = new Knife();
                break;
            case 2:
                ukko.weapon = new Axe();
                break;
            case 3:
                ukko.weapon = new Sword();
                break;
            case 4:
                ukko.weapon = new Club();
                break;
        }
        return ukko;
    }
}
