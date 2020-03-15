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
public class Human extends Player{

    public Human() {
        System.out.println("He creat un <" + getClass().getName().substring(getClass().getName().indexOf(".")+1) + "> desde Human");
    }
    
    
}
