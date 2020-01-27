/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itkesa2018.v4;

/**
 *
 * @author n4121
 */
public class Car {
    public Car(){
        Body body = new Body();
        Chassis chassis = new Chassis();
        Engine engine = new Engine();
        Wheel wheel1 = new Wheel();
        Wheel wheel2 = new Wheel();
        Wheel wheel3 = new Wheel();
        Wheel wheel4 = new Wheel();
        
    }
    public void print(){
        System.out.println("Autoon kuuluu:");
        System.out.println("\tBody");
        System.out.println("\tChassis");
        System.out.println("\tEngine");
        System.out.println("\t4 Wheel");
    }
    public class Body{
        public Body(){
        System.out.println("Valmistetaan: Body");
        }
    }
    public class Wheel{
        public Wheel(){
        System.out.println("Valmistetaan: Wheel");
        }
    }
    public class Chassis{
        public Chassis(){
        System.out.println("Valmistetaan: Chassis");
        }
    }
    public class Engine{
        public Engine(){
        System.out.println("Valmistetaan: Engine");     
        }
    }
}

