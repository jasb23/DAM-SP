/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joc;

import java.util.ArrayList;;
import java.util.Objects;

/**
 *
 * @author jasb
 */
public class Team {
    private String name;
    private ArrayList<Player> players;

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Player p){
        // comprovar que no pertanya ja!
        boolean exist = false;
        for (Player p1:this.players)
            if (p1 == p)
                exist = true;
        if (!exist)
        {
            this.players.add(p); 
            p.add(this);
        }
    }
    
    public void remove(Player p) throws rolExceptions{

        boolean esta = false;
        for (Player p_aux : this.players){
            if (p_aux == p){
                esta = true;                
                break;
            }
        }
        
        if (esta)
        {
            this.players.remove(p); 
            if (Thread.currentThread().getStackTrace()[2].getMethodName().compareTo("remove") != 0)
                p.remove(this);
        }else
            throw new rolExceptions("No es posible llevar un jugador que no pertany a un equipo !!");
    }
        
    public void getMembers(){
        for (Player p:this.players)
            p.toString();
    }

    public ArrayList<Player> getPlayers() throws rolExceptions {
        for (Player p:this.players){
            int n=0;
            for (Player p2:this.players){
                if (p.equals(p2))
                    n += 1;
            }         
            if (n>1) 
                throw new rolExceptions("Un equip no pot tenir jugadors repetits");
        }
        return this.players;
    }
    
    
    @Override
    public String toString() {
        String txt="";
        for (Player p:this.players)
            txt = txt + "\n" + p.toString();
        
        if (txt == "")
                txt = "No te membres";
        return "\033[34m" + "\nEquip " + this.name + ": " + "\u001B[0m" + txt;
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
        final Team altre = (Team) obj;
        if (!Objects.equals(this.name, altre.name)) {
            return false;
        }
        for (Player p1:this.players)
        {
            boolean exist = false;
            for (Player p2:altre.players) 
                if (p1.equals(p2)){
                    exist = true;
                    break;    
                }
            
            if (!exist)
                return false;
        }
        return true;
    }
}
