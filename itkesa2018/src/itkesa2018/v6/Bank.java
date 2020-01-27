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
import java.util.HashMap;
/**
 *
 * @author n4121
 */
public class Bank {
    private Account account;
    //Set set = account.hmap.entrySet();
    //Iterator iterator = set.iterator();
    private HashMap<String,Account> hmap = new HashMap<>();
    public Bank(){
    }
    public void pickMoney(String number, int amount){
        Account a = hmap.get(number);
        a.moneyAmount -= amount;
        hmap.put(number, a);
        //int value = account.hmap.get(number);
        //account.hmap.put(number, value-amount);       
    }
    public void printAccount(){
        System.out.println("Kaikki tilit:");
        for(Account acc : hmap.values()){
            acc.print();
        }
        /*Set set = account.hmap.entrySet();
        Iterator iterator = set.iterator();
        
        while(iterator.hasNext()){
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("Tilinumero: "+mentry.getKey());
            System.out.println(" TilillÃ¤ rahaa: "+mentry.getValue());
        }*/
        
    }
    public void searchAccount(String number){
        hmap.get(number).print();

        /*Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mentry = (Map.Entry)iterator.next();
            if(number == mentry.getKey()){
                System.out.print("Tilinumero: "+mentry.getKey());
                System.out.print(" TilillÃ¤ rahaa: "+mentry.getValue());
            }
            
        }*/
        //Account acc = account.hmap.get(number);
        //int value = account.hmap.get(number);
        //System.out.println("Tilinumero: "+acc.getAccNumber()+" TilillÃ¤ rahaa: "+acc.getMoney());
    }
    public void addMoney(String number, int amount){
        Account a = hmap.get(number);
        a.moneyAmount += amount;
        hmap.put(number, a);
        //int value = account.hmap.get(number);
        //account.hmap.put(number, amount+value);
   
    }
    public void addAccountT(String number, int amount){
        account = new Tavallinentili(number,amount);
        hmap.put(number,account);
        System.out.println("Tili luotu.");
    }
    public void addAccountL(String number, int amount, int cred){
        account = new Luottotili(number,amount,cred);        
        hmap.put(number, account);
        System.out.println("Tili luotu.");
    }
    public void deleteAccount(String number){
        hmap.remove(number);
        System.out.println("Tili poistettu.");
    }
}
