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
public abstract class Player {

    public Player() {
        System.out.println("He creat un <" + getClass().getName().substring(getClass().getName().indexOf(".")+1) + "> desde Player");
    }        
}
