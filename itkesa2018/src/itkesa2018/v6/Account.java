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
public abstract class Account {
    protected String accountNumber;
    protected int moneyAmount;
    protected abstract void print();
    public Account(){
    }

    protected void setMoney(int money){
        moneyAmount = money;
    }
    protected int getMoney(){
        return moneyAmount;
    }
    protected String getAccNumber(){
        return accountNumber;
    }
}
class Tavallinentili extends Account{
    public Tavallinentili(String number,int money){
        accountNumber = number;
        moneyAmount = money;
    }
    @Override
    public void print(){
        System.out.println("Tilinumero: "+accountNumber+" TilillÃ¤ rahaa: "+moneyAmount);
    }
}
class Luottotili extends Account{
    private int credit;
    public Luottotili(String x,int y,int z){
        accountNumber = x;
        moneyAmount = y;
        credit = z;
    }
    @Override
    public void print(){
        System.out.println("Tilinumero: "+accountNumber+" TilillÃ¤ rahaa: "+moneyAmount+" Luottoraja: "+credit);
    }
    public void setCredit(int cred){
        credit = cred;
    }
    public int getCredit(){
        return credit;
    }
}
