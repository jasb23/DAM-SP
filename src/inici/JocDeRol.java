/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inici;

import joc.*;

/**
 *
 * @author damsp
 */
public class JocDeRol {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        provaFase();
    }
    
    public static void provaFase(){
        
        System.out.println("Vaig a crear un humanoide");
        Human humanoide = new Human();
        System.out.println("Vaig a crear un alienigena");
        Alien alienigena = new Alien();
        System.out.println("Vaig a crear un guerrer");
        Warrior guerrer = new Warrior();
    }
}
