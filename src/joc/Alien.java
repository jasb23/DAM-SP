/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joc;

/**
 *
 * @author jasb
 */
public class Alien extends Player{

    public Alien() {
        System.out.println("He creat un <" + getClass().getName().substring(getClass().getName().indexOf(".")+1) + ">");
    }

    public Alien(String name, int attackPoints, int defensePoints) {
        super(name, attackPoints, defensePoints);
    }
    
    public Alien(String name, int attackPoints, int defensePoints, int life) {
        super(name, attackPoints, defensePoints, life);
    }
    
    // Sobrecàrrega de mètodes
    @Override
    protected void hit(int attack){
        int bonusDefensa = 0;  
        for (Item arma: this.getItems())
            bonusDefensa += arma.getDefenseBonus();
        
        if (this.getLife() > 20)
                this.setDefensePoints(this.getDefensePoints() - 3);
         
        int defensa = this.getDefensePoints() + bonusDefensa;
        if (defensa < 0) defensa = 0;
        if (attack < 0) attack = 0;
        
        int vides = this.life;
         if (attack > 0)
        {
            if (attack >= defensa)
                vides -= (attack - defensa);       
            else
                vides -= 1;
        }
                
        
        if (vides < 0) vides = 0;
         
        int colp;
        if (attack >= defensa)
            colp = attack - defensa;
        else 
            colp = 1;
        
        if (attack == 0) colp = 0;
       
        
        System.out.println(this.getName() + " es colpejat amb " + attack + " punts i es defen amb " + defensa + ". Vides: " + this.getLife() + " - " + colp + " = " + vides );
        this.life = vides;     
        if (this.life <= 0){
            this.ranking = this.vius;    
            this.vius -= 1;
            System.out.println(this.getName() + " ha mort !!");
        }
    }
}
