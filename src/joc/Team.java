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
 * @author damsp
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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
    
    public void remove(Player p){
        if (this.players != null){
            this.players.remove(p);
            p.remove(this);
        }
    }
        
    public void getMembers(){
        for (Player p:this.players)
            p.toString();
    }
            
    @Override
    public String toString() {
        String txt="";
        for (Player p:this.players)
            txt = txt + "\n" + p.toString();
        
        if (txt == "")
                txt = "No te membres";
        return "\nEquip " + this.name + ": " + txt;
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
