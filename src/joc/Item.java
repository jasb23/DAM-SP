/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joc;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author jasb
 */
public class Item {
    private String name;
    private int attackBonus;
    private int defenseBonus;
    private Player propietari; // per a cotrolar el jugadors que te l'arma

    public Item(String name, int attackBonus, int defenseBonus) {
        this.name = name;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;    
        this.propietari=null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttatckBonus() {
        return attackBonus;
    }

    public void setAttatckBonus(int attatckBonus) {
        this.attackBonus = attatckBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public void setDefenseBonus(int defenseBonus) {
        this.defenseBonus = defenseBonus;
    }

    public Player getPropietari() {
        return propietari;
    }

    public void setPropietari(Player propietari) {
        this.propietari = propietari;
    }

    @Override
    public String toString() {
        return "- " + "\033[35m" + name + " :" + "\u001B[0m" + " BA:" + this.attackBonus + " / BD:" + this.defenseBonus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}
