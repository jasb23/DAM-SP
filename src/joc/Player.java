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
    private String name;
    int attackPoints, defensePoints, life;
    
    // constructores
    public Player() {
        System.out.println("He creat un <" + getClass().getName().substring(getClass().getName().indexOf(".")+1) + "> desde Player");
    } 

    public Player(String name, int attackPoints, int defensePoints, int life) {
        this.name = name;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.life = life;
    }
    // getters
    public String getName() {
        return name;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public int getLife() {
        return life;
    }
    
    //mètodes
    public void attack(Player p){
        p.hit(this.getAttackPoints());
        if (p.getLife()> 0)
                this.hit(p.getAttackPoints());
    }
    
    protected void hit(int attack){
        System.out.println(this.getName() + " es colpejat amb " + attack + " punts i es defen amb " + this.getDefensePoints() + ". Vides: " + this.getLife() + " - " + (attack - this.getDefensePoints()) + " = " + (this.getLife() - attack + this.getDefensePoints()) );
        this.life -= attack - this.getDefensePoints();
        if (this.life < 0) 
                this.life =0;
    }
    
    @Override
    public String toString(){
        return this.getName() + " >> " + "PA:" + this.getAttackPoints()+ "  /  " + "PD:" + this.getDefensePoints() + "  /  " + "PV:" + this.getLife();
    }
}
