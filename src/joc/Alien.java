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

    public Alien(String name, int attackPoints, int defensePoints, int life) {
        super(name, attackPoints, defensePoints, life);
    }
    
    // Sobrecàrrega de mètodes
    /*public void attack(Player p){        
        if (p.getLife() > 0){
            if (this.getLife() > 20) 
                this.setAttackPoints(this.getAttackPoints() + 3);
            
            p.hit(this.getAttackPoints());
            if (p.getLife()> 0)
                this.hit(p.getAttackPoints());
        }
        else
            System.out.println("ATENCIÓ: " + p.getName() + " no pot ser atacat, ja està mort!");
    }*/
    
    @Override
    protected void hit(int attack){
        if (this.getLife() > 20)
                this.setDefensePoints(this.getDefensePoints() - 3);
         
        int defensa = this.getDefensePoints();
        int vides = this.life - (attack - defensa);
        if (vides < 0) vides = 0;
        System.out.println(this.getName() + " es colpejat amb " + attack + " punts i es defen amb " + defensa + ". Vides: " + this.getLife() + " - " + (attack - defensa) + " = " + vides );
        this.life = vides;     
        if (this.life <= 0)
                  System.out.println(this.getName() + " ha mort !!");
    }
    
}
