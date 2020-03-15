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
        
        //System.out.println("Vaig a crear un humanoide");
        Human humanoide = new Human("John Smith", 13, 8, 39);
        //System.out.println("Vaig a crear un guerrer");
        Warrior guerrer = new Warrior("Brave",100, 30, 85);
        //System.out.println("Vaig a crear un alienigena");
        Alien alienigena = new Alien("Martian PK", 27, 2, 32);
        
        System.out.println("//  ABANS DE L'ATAC:");
        System.out.println("Atacant: " + humanoide.toString());
        System.out.println("Atacat: " + alienigena.toString());
        humanoide.attack(alienigena);
        System.out.println("//  DESPRÉS DE L'ATAC:");
        System.out.println("Atacant: " + humanoide.toString());
        System.out.println("Atacat: " + alienigena.toString());
        
    }
}
