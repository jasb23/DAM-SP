/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joc;

/**
 *
 * @author damsp
 */
public class Alien extends Player{

    public Alien() {
        System.out.println("He creat un <" + getClass().getName().substring(getClass().getName().indexOf(".")+1) + ">");
    }
    
    
}
