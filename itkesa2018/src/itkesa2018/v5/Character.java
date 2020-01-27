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
public abstract class Character{
    public Weapon weapon;
    public Character(){  
    }
    public abstract void fight();   
} 
class Queen extends Character{
    public Queen(){
    }
        @Override            
    public void fight(){
        System.out.print(this.getClass().getSimpleName()+" tappelee aseella "+ this.weapon.useWeapon()+"\n");
    }  
}
class King extends Character{
    public King(){
    }
                @Override            
    public void fight(){
        System.out.print(this.getClass().getSimpleName()+" tappelee aseella "+ this.weapon.useWeapon()+"\n");
    }
}
class Knight extends Character{
    public Knight(){
    }
    @Override   
            public void fight(){
        System.out.print(this.getClass().getSimpleName()+" tappelee aseella "+ this.weapon.useWeapon()+"\n");
    }
}
class Troll extends Character{
    public Troll(){
    }
    @Override   
            public void fight(){
        System.out.print(this.getClass().getSimpleName()+" tappelee aseella "+ this.weapon.useWeapon()+"\n");
        
    }
}
abstract class Weapon{
    public Weapon(){  
    }
    public abstract String useWeapon();
}
    class Knife extends Weapon{
        public Knife(){
        }
        @Override
        public String useWeapon(){
            return "Knife";
        }
    }
    class Club extends Weapon{
        public Club(){
        }
        @Override
        public String useWeapon(){
            return "Club";
        }
    }
    class Sword extends Weapon{
        public Sword(){
        }
        @Override
        public String useWeapon(){
            return "Sword";
        }
    }
    class Axe extends Weapon{
        public Axe(){
        }
        @Override
        public String useWeapon(){
            return "Axe";
        }
    }
