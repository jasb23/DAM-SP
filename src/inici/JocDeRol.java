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
    
    private static void atacar(Player A, Player B){
        System.out.println("//  ABANS DE L'ATAC:");
        System.out.println("Atacant: " + A.toString());
        System.out.println("Atacat: " + B.toString());
        A.attack(B);
        System.out.println("//  DESPRÉS DE L'ATAC:");
        System.out.println("Atacant: " + A.toString());
        System.out.println("Atacat: " + B.toString());
        
    }
    public static void provaFase(){
        
        //System.out.println("Vaig a crear un humanoide");
        Human humanoid = new Human("John Smith", 13, 8, 39);
        //System.out.println("Vaig a crear un guerrer");
        Warrior guerrer = new Warrior("Brave",10, 25, 85);
        //System.out.println("Vaig a crear un alienigena");
        Alien alienigena = new Alien("Martian PK", 27, 2, 35);
        
        System.out.println("************ ATAC 1 *************");
        atacar(humanoid, alienigena);        
        System.out.println("");
        
        System.out.println("************ ATAC 2 *************");        
        atacar(guerrer, alienigena);        
        System.out.println("");
        
        System.out.println("************ ATAC 3 *************");        
        atacar(guerrer, humanoid);
        System.out.println("");        
        
        System.out.println("************ ATAC 4 *************");        
        atacar(guerrer, humanoid);
        System.out.println("");
    }
}
