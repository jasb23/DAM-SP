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
        if ((this instanceof Human) && (life > 100))
            System.err.println("ATENCIÓ: El nombre de vides dels Humans no pot ser major a 100.");
        else
        {
            this.name = name;
            this.attackPoints = attackPoints;
            this.defensePoints = defensePoints;
            this.life = life;
        }
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAttackPoints(int attackPoints) {
        if (attackPoints < 0) attackPoints = 0;        
        this.attackPoints = attackPoints;
    }

    public void setDefensePoints(int defensePoints) {
        if (defensePoints < 0) defensePoints = 0;
        this.defensePoints = defensePoints;
    }

    public void setLife(int life) {
        if (life < 0) life = 0;
        this.life = life;
    }
    
    
    
    //mètodes
    public void attack(Player p){
        if (p.getLife() > 0){
            p.hit(this.getAttackPoints());
            if (p.getLife()> 0)
            {
                if ((p instanceof Alien) && (life > 20))
                    p.setAttackPoints(p.getAttackPoints() + 3);
                this.hit(p.getAttackPoints());
            }
        }
        else
            System.out.println("ATENCIÓ: " + p.getName() + " no pot ser atacat, ja està mort!");
    }
    
    protected void hit(int attack){
        int vides = this.life;
        if (attack > 0)
            vides -= (attack - this.getDefensePoints());       
        
        if (vides < 0) vides = 0;
        
        int colp = (attack - this.getDefensePoints());
        if (colp < 0) colp = 0;
        
        System.out.println(this.getName() + " es colpejat amb " + attack + " punts i es defen amb " + this.getDefensePoints() + ". Vides: " + this.getLife() + " - " + colp + " = " + vides );
        this.life = vides;     
        if (this.life <= 0)
                  System.out.println(this.getName() + " ha mort !!");
    }
    
    @Override
    public String toString(){
        return this.getName() + " >> " + "PA:" + this.getAttackPoints()+ "  /  " + "PD:" + this.getDefensePoints() + "  /  " + "PV:" + this.getLife();
    }
}
