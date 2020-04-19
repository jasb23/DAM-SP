/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joc;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jasb
 */
public class Player implements Comparable{
    private String name;
    private int attackPoints, defensePoints;
    int life;
    private ArrayList<Team> teams;
    private ArrayList<Item> items;
    int ranking;
    public static int vius = 0;
    
    // constructores
    public Player() {
        this.teams = null;
        System.out.println("He creat un <" + getClass().getName().substring(getClass().getName().indexOf(".")+1) + "> desde Player");
    } 

    public Player(String name, int attackPoints, int defensePoints) {       
        if ((this instanceof Human) && (life > 100))
            System.err.println("ATENCIÓ: El nombre de vides dels Humans no pot ser major a 100.");
        else
        {
            this.name = name;
            this.attackPoints = attackPoints;
            this.defensePoints = defensePoints;
            this.life = 100;
            this.teams = new ArrayList<>();                       
            this.items = new ArrayList<>();    
            vius += 1;
            this.ranking = 1;
        }
    }

    public Player(String name, int attackPoints, int defensePoints, int life) {
        this.name = name;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.life = life;
        this.teams = new ArrayList<>();                       
        this.items = new ArrayList<>();    
        vius += 1;
        this.ranking = 1;
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

    public ArrayList<Team> getTeams() throws rolExceptions {
        for (Team t:this.teams){
            int n=0;
            for (Team t2:this.teams){
                if (t.equals(t2))
                    n += 1;
            }         
            if (n>1) 
                throw new rolExceptions("Un jugador no pot tenir equips repetits");
        }
        return teams;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }        
    
    public void add(Team t){
        // comprovar que no pertanya ja a l'equip!
        boolean exist = false;

        for (Team t1 : this.teams) {
            if (t1 == t) {
                exist = true;
            }
        }
        if (!exist) {
            this.teams.add(t);
            t.add(this);
        }
    }
    
    public void remove(Team t) throws rolExceptions{
        
       boolean esta = false;

        for (Team t_aux : this.teams) {
            if (t_aux == t) {
                esta = true;                
                break;
            }
        }
        
        if (esta) {
            this.teams.remove(t);  
            if (Thread.currentThread().getStackTrace()[2].getMethodName().compareTo("remove") != 0)
                t.remove(this);
        }else
            throw new rolExceptions("No es posible llevar un equip al qual no pertenany un jugador !!");
    } 
    
    public void add(Item arma){
        
        // comprovar que el item no tinga propietari!  Si el té no li asignarem l'arma    
        if (arma.getPropietari() == null){
            this.items.add(arma);
            arma.setPropietari(this);           
        }
        else
            System.err.println("\nL'objecte " + arma.getName() + " ja té propietari i és " +arma.getPropietari().getName());
    }
    
    public void remove(Item arma){
        //comprovar que el jugador és el propietari de l'arma abans de llevar-li-la
        if (arma.getPropietari() == this){
            this.items.remove(arma);
            arma.setPropietari(null);
        }
        else
            System.err.println("ATENCIÓ: El item: " + arma.getName() + " no pertany a " + this.getName());
    }
    
    //mètodes
    public void attack(Player p) throws rolExceptions{            
        
        
        if (this.getLife() <= 0)
            throw new rolExceptions("Un jugador mort no pot atacar !!");
        
        if (p.getLife() <= 0)
            throw new rolExceptions("Un jugador mort no pot ser atacat !!");
        
        if (this.equals(p))
            throw new rolExceptions("Un jugador no pot atacar-se a ell mateix !!");
        
        
        if (p.getLife()> 0)
        {
            int bonusAtac = 0;  
            for (Item arma: this.items)
                bonusAtac += arma.getAttatckBonus();          
        
            p.hit(this.getAttackPoints() + bonusAtac);
            if ((p instanceof Alien) && (life > 20))
                p.setAttackPoints(p.getAttackPoints() + 3);
                               
            int bonusAtac_p = 0;  
            for (Item arma: p.items)
                bonusAtac_p += arma.getAttatckBonus();        
                
            this.hit(p.getAttackPoints() + bonusAtac_p);
        }
    }
        
    void hit(int attack){  
                
        int bonusDefensa = 0;  
                
        for (Item arma: this.items)
            bonusDefensa += arma.getDefenseBonus();
        
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
            this.ranking = vius;    
            vius -= 1;
            System.out.println(this.getName() + " ha mort !!");
        }
    }
    
    @Override
    public String toString(){
        String textArmes="";        
     
        if (this.items.size() == 0)
            textArmes = " i no té items.";
        else{
            textArmes = " i té els items:";
            for (Item arma: this.items){
                String txt = "\n" + arma.toString();                
                textArmes = textArmes + txt;                
            }
        }
          
        try {
            return "\u001B[32m" + this.getName() + "\u001B[0m" + " >> " + "PA:" + this.getAttackPoints()+ "  /  " + "PD:" + this.getDefensePoints() + "  /  " + "PV:" + this.getLife() + " (pertany a " + this.getTeams().size() + " equips)" + textArmes;
        } catch (rolExceptions ex) {
            System.err.println(ex);
        }
        return null;
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
        final Player other = (Player) obj;
        /*
        if (this.attackPoints != other.attackPoints) {
            return false;
        }
        if (this.defensePoints != other.defensePoints) {
            return false;
        }
        if (this.life != other.life) {
            return false;
        }*/
        if (this.name.compareTo(other.name) != 0) {
            return false;
        }
        return true;
    }
   
    @Override
    public int compareTo(Object o) {
        return this.ranking - ((Player) o).getRanking();
    }
    
    
}
