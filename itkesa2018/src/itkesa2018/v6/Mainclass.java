/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itkesa2018.v6;

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
    private static int valinta = 1;
    private static String number = null;
    private static int money ,limit;
    public static void main(String[] args){
        Bank bank = new Bank();
        while(valinta != 0){
            System.out.println("\n*** PANKKIJÃ„RJESTELMÃ„ ***");
            System.out.println("1) LisÃ¤Ã¤ tavallinen tili");
            System.out.println("2) LisÃ¤Ã¤ luotollinen tili");
            System.out.println("3) Tallenna tilille rahaa");
            System.out.println("4) Nosta tililtÃ¤");
            System.out.println("5) Poista tili");
            System.out.println("6) Tulosta tili");
            System.out.println("7) Tulosta kaikki tilit");
            System.out.println("0) Lopeta");
            System.out.print("Valintasi: ");
            Scanner scan = new Scanner(System.in);
            valinta = scan.nextInt();
            switch(valinta){
                case 1:
                    System.out.print("SyÃ¶tÃ¤ tilinumero: ");
                    number = scan.next();
                    System.out.print("SyÃ¶tÃ¤ rahamÃ¤Ã¤rÃ¤: ");
                    money = scan.nextInt();
                    bank.addAccountT(number, money);
                    break;
                case 2:
                    System.out.print("SyÃ¶tÃ¤ tilinumero: ");
                    number = scan.next();
                    System.out.print("SyÃ¶tÃ¤ rahamÃ¤Ã¤rÃ¤: ");
                    money = scan.nextInt();
                    System.out.print("SyÃ¶tÃ¤ luottoraja: ");
                    limit = scan.nextInt();
                    bank.addAccountL(number, money, limit);
                    break;
                case 3:
                    System.out.print("SyÃ¶tÃ¤ tilinumero: ");
                    number = scan.next();
                    System.out.print("SyÃ¶tÃ¤ rahamÃ¤Ã¤rÃ¤: ");
                    money = scan.nextInt();
                    bank.addMoney(number, money);
                    break;
                case 4:
                    System.out.print("SyÃ¶tÃ¤ tilinumero: ");
                    number = scan.next();
                    System.out.print("SyÃ¶tÃ¤ rahamÃ¤Ã¤rÃ¤: ");
                    money = scan.nextInt();
                    bank.pickMoney(number, money);
                    break;
                case 5:
                    System.out.print("SyÃ¶tÃ¤ poistettava tilinumero: ");
                    number = scan.next();
                    bank.deleteAccount(number);
                    break;
                case 6:
                    System.out.print("SyÃ¶tÃ¤ tulostettava tilinumero: ");
                    number = scan.next();
                    bank.searchAccount(number);
                   
                    break;
                case 7:
                    bank.printAccount();
                    break;
                case 0:
                    break;
                default: 
                    System.out.println("Valinta ei kelpaa.");
                    break;
            }
        }
    }
}
