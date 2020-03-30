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
public class Warrior extends Human{

    public Warrior(String name, int attackPoints, int defensePoints, int life) {
        super(name, attackPoints, defensePoints, life);
    }

    public Warrior() {
        System.out.println("He creat un <" + getClass().getName().substring(getClass().getName().indexOf(".")+1) + "> desde Warrior");
    }    
    
    
    //Si el colp no
    //és superior a 5 punts, aquest queda reduït a 0. El colp (hit) és la diferència entre l’atac que sofrix
    ///i la defensa del jugador.
    public void attack(Player p){
        if (p.getLife() > 0){
            p.hit(this.getAttackPoints());
            if (p.getLife()> 0)
                if ((p.getAttackPoints() - this.getDefensePoints()) > 5)
                    this.hit(p.getAttackPoints());
                else
                    this.hit(0);
        }
        else
            System.out.println("ATENCIÓ: " + p.getName() + " no pot ser atacat, ja està mort!");
    }
}
