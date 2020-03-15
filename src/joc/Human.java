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

    public Human(String name, int attackPoints, int defensePoints, int life) {
        super(name, attackPoints, defensePoints, life);
    }        
    
    //mètodes
    public void attack(Player p){
        p.hit(this.getAttackPoints());
        if (p.getLife()> 0)
                this.hit(p.getAttackPoints());
    }
}
