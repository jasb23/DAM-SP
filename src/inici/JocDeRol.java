/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inici;

import static io.Leer.leerEntero;
import static io.Leer.leerTexto;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import joc.*;

/**
 *
 * @author jasb
 */
public class JocDeRol {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Player> jugadors = new ArrayList<>();
        ArrayList<Team> equips = new ArrayList<>();
        ArrayList<Item> armes = new ArrayList<>();

        int opcio;
        do {
            opcio = -1;
            System.out.println("\n**** M E N Ú   P R I N C I P A L ****\n");
            System.out.println("1. Configuració");
            System.out.println("2. Jugar");
            System.out.println("3. Eixir");
            opcio = leerEntero("\nIntrodueix una opcio (1-3): ");
            
            // generaTest(jugadors, equips, armes);  // Cridant a aquest mètode es genera un joc de proves amb jugadors, equips i objectes, tots ells ja interrelaconats
            
            switch (opcio) {
                case 1:                    
                    configuracio(jugadors, equips, armes);
                    break;
                case 2:
                    boolean fin = jugar(jugadors, equips, armes);
                    if (fin) {
                        System.out.println("\nFi del joc");
                        return;
                    } else {
                        break;
                    }
                case 3:
                    System.out.println("\nFi del joc");
                    break;
                default:
                    System.err.println("\nOpcio incorrecta");
                    break;
            }
        } while (opcio != 3);

    }

    private static void configuracio(ArrayList<Player> jugadors, ArrayList<Team> equips, ArrayList<Item> armes) {
        int opcio;
        do {
            opcio = -1;
            System.out.println("\n\n**** M E N Ú   C O N F I G U R A C I Ó ****\n");
            System.out.println("1. Gestió jugadors");
            System.out.println("2. Gestió equips");
            System.out.println("3. Gestió objectes");
            System.out.println("4. Eixir");
            opcio = leerEntero("\nIntrodueix una opcio (1-4): ");

            switch (opcio) {
                case 1:
                    gestioJugadors(jugadors, equips, armes);
                    break;
                case 2:
                    gestioEquips(equips, jugadors);
                    break;
                case 3:
                    gestioObjectes(armes, jugadors);
                    break;
                case 4:
                    break;
                default:
                    System.err.println("\nOpcio incorrecta");
                    break;
            }
        } while (opcio != 4);
    }

    /*
     * Normes: 1) En ordre, cada jugador de la llista realitza un atac a altre
     * jugador aleatòriament 
     * 2) Quan un jugador mor, ja no participarà en el joc i es gravarà la seua posició en el ranking de la partida. 
     * 3) Quan sols quede un jugador viu el joc acabarà i es mostrarà la posición en el ranking de cada jugador.
     */
    private static boolean jugar(ArrayList<Player> jugadors, ArrayList<Team> equips, ArrayList<Item> armes) {
        if (jugadors.isEmpty()) {
            System.err.println("\nSense jugadors no es pot jugar. Primer has de configurar el joc");
            return false;
        }

        int nAtac = 0, nFase = 0;

        if (jugadors.size() == 1) {
            System.err.println("\nEs necessiten almenys 2 jugadors per a jugar.");
            return false;
        }

        int nVius = comprovarVius(jugadors);

        while (nVius > 1) {
            try {
                nFase += 1;
                System.out.println("\n\n************ RONDA D'ATAC Nº " + nFase + " *************");
                for (Player p : jugadors) {
                    nVius = comprovarVius(jugadors);
                    if ((p.getLife() > 0) && (nVius > 1)) {
                        nAtac += 1;
                        Player rival = obtenirRival(p, jugadors);
                        System.out.println("\n\n************ Atac " + nAtac + " *************");                        
                        atacar(p, rival);                                                    
                        System.out.println("");
                        Thread.sleep(350);
                    }
                }
                nVius = comprovarVius(jugadors);
            } catch (InterruptedException | rolExceptions ex ) {
                System.err.println(ex);
            }
        }

        /* Ordenar i mostrar ranking dels jugadors*/
        ArrayList<Player> ranking = ordenar(jugadors);        
        System.out.println("L'únic jugador viu i, per tant, guanyador del joc és " + ranking.get(0).getName());
        return true;
    }
    
    private static void gestioJugadors(ArrayList<Player> jugadors, ArrayList<Team> equips, ArrayList<Item> armes) {
        int opcio;

        do {
            opcio = -1;
            ArrayList<Object> lista = new ArrayList<>(jugadors);
            boolean ok = false;

            System.out.println("\n**** M E N Ú   C O N F I G U R A C I Ó ****\n");
            System.out.println("1. Crear jugador");
            System.out.println("2. Mostrar jugadors");
            System.out.println("3. Esborrar jugador");
            System.out.println("4. Assignar jugador a equips");
            System.out.println("5. Assignar objecte a jugador");
            System.out.println("6. Eixir");
            opcio = leerEntero("\nIntrodueix una opcio (1-6): ");

            switch (opcio) {
                case 1:
                    Player p = crearJugador(jugadors);

                    if (p != null) {
                        if (jugadors.isEmpty()) {
                            jugadors.add(p);
                        } else {
                            Iterator<Player> it = jugadors.iterator();
                            /*iterator para recorer la llista de jugadors*/
                            boolean exists = false;
                            while (it.hasNext()) {
                                Player tmp = it.next();
                                if (p.equals(tmp)) // comprovem si hi ha algun jugador amb el mateix nom. Si no l'afegim al ArrayList                        
                                {
                                    exists = true;
                                }
                            }
                            if (!exists) {
                                jugadors.add(p);
                            }
                        }
                    }
                    break;
                case 2:
                    mostrar(lista, 'J');
                    break;
                case 3:
                    String nom = leerTexto("Dis-me el nom del jugador a esborrar: ");
                    for (Player tmp : jugadors) {
                        if (tmp.getName().compareTo(nom) == 0) {
                            try {
                                /* hay que elimnar las relaciones entre el jugador eliminado y los equipos a los que pertenece, si tiene */
                                if (tmp.getTeams() != null) {
                                    int nEquips = tmp.getTeams().size();
                                    for (int i = 0; i < nEquips; i++) {
                                        tmp.remove(tmp.getTeams().get(0));
                                    }
                                }
                                
                                /* hay que elimnar las relaciones entre el jugador eliminado y sus objetos, si tiene */
                                if (tmp.getItems() != null) {
                                    int nItems = tmp.getItems().size();
                                    for (int i = 0; i < nItems; i++) {
                                        tmp.remove(tmp.getItems().get(0));
                                    }
                                }
                                
                                jugadors.remove(tmp);
                                ok = true;
                                break;
                            } catch (rolExceptions ex) {
                                System.err.println(ex);
                            }
                        }
                    }

                    if (ok) {
                        System.out.println("\nJugador esborrat correctament.");
                    } else {
                        System.err.println("\nJugador no existeix.");
                    }
                    break;
                case 4:
                    String nomJ = leerTexto("Dis-me el nom del jugador: ");
                    String nomE = leerTexto("Dis-me el nom de l'equip: ");

                    for (Team e : equips) {
                        if (e.getName().compareTo(nomE) == 0) {
                            for (Player j : jugadors) {
                                if (j.getName().compareTo(nomJ) == 0) {
                                    e.add(j);
                                    ok = true;
                                }
                            }
                        }
                    }

                    if (ok) {
                        System.out.println("\nEl jugador s'ha assignat a l'equip.");
                    } else {
                        System.err.println("\nO l'equip i/o el jugador no existeixen.");
                    }
                    break;
                case 5:
                    String nomO = leerTexto("Dis-me el nom de l'objecte: ");
                    String nomJg = leerTexto("Dis-me el nom del jugador: ");

                    for (Item o : armes) {
                        if (o.getName().compareTo(nomO) == 0) {
                            for (Player j : jugadors) {
                                if (j.getName().compareTo(nomJg) == 0) {
                                    j.add(o);
                                    ok = true;
                                }
                            }
                        }
                    }

                    if (ok) {
                        System.out.println("\nL'objecte s'ha assignat al jugador.");
                    } else {
                        System.err.println("\nO l'objecte i/o el jugador no existeixen.");
                    }
                    break;
                case 6:
                    break;
                default:
                    System.err.println("\nOpcio incorrecta");
                    break;
            }
        } while (opcio != 6);
    }

    private static void gestioEquips(ArrayList<Team> equips, ArrayList<Player> jugadors) {
        int opcio;
        do {
            opcio = -1;
            ArrayList<Object> lista = new ArrayList<>(equips);
            boolean ok = false;

            System.out.println("\n**** M E N Ú   C O N F I G U R A C I Ó ****\n");
            System.out.println("1. Crear equip");
            System.out.println("2. Mostrar equips");
            System.out.println("3. Esborrar equip");
            System.out.println("4. Assignar equip a jugador");
            System.out.println("5. Eixir");
            opcio = leerEntero("\nIntrodueix una opcio (1-5): ");

            switch (opcio) {
                case 1:
                    Team t = crearEquip(equips);

                    if (t != null) {

                        if (equips.isEmpty()) {
                            equips.add(t);
                        } else {
                            Iterator<Team> it = equips.iterator();
                            /*iterator para recorer la llista de equips*/
                            boolean exists = false;
                            while (it.hasNext()) {
                                Team tmp = it.next();
                                if (t.equals(tmp)) // comprovem si hi ha algun equip amnb el mateix nom. Si no l'afegim al ArrayList                                                    
                                {
                                    exists = true;
                                }
                            }
                            if (!exists) {
                                equips.add(t);
                            }
                        }
                    }
                    break;
                case 2:
                    mostrar(lista, 'E');
                    break;
                case 3:
                    String nom = leerTexto("Dis-me el nom de l'equip a esborrar: ");
                    for (Team tmp : equips) {
                        if (tmp.getName().compareTo(nom) == 0) {
                            try {
                                /* hay que elimnar las relaciones entre el equipo eliminado y los jugadores que pertenecen a él, si tiene */
                                if (tmp.getPlayers() != null) {
                                    int nJug = tmp.getPlayers().size();
                                    for (int i = 0; i < nJug; i++) {
                                        tmp.remove(tmp.getPlayers().get(0));
                                        /* siempre tendré que eliminar el primero, si hay, ya que los objetos actualizan los indices de la la lista de players */
                                    }
                                }
                                
                                equips.remove(tmp);
                                ok = true;
                                break;
                            } catch (rolExceptions ex) {
                                System.err.println(ex);
                            }
                        }
                    }
                    if (ok) {
                        System.out.println("\nEquip esborrat correctament.");
                    } else {
                        System.err.println("\nEquip no existeix.");
                    }
                    break;
                case 4:
                    String nomE = leerTexto("Dis-me el nom de l'equip: ");
                    String nomJ = leerTexto("Dis-me el nom del jugador: ");

                    for (Team e : equips) {
                        if (e.getName().compareTo(nomE) == 0) {
                            for (Player j : jugadors) {
                                if (j.getName().compareTo(nomJ) == 0) {
                                    j.add(e);
                                    ok = true;
                                }
                            }
                        }
                    }

                    if (ok) {
                        System.out.println("\nL'equip s'ha assignat al jugador.");
                    } else {
                        System.err.println("\nO l'equip i/o el jugador no existeixen.");
                    }
                    break;
                case 5:
                    break;
                default:
                    System.err.println("\nOpcio incorrecta");
                    break;
            }
        } while (opcio != 5);
    }

    private static void gestioObjectes(ArrayList<Item> armes, ArrayList<Player> jugadors) {
        int opcio;
        do {
            opcio = -1;
            ArrayList<Object> lista = new ArrayList<>(armes);
            boolean ok = false;

            System.out.println("\n**** M E N Ú   C O N F I G U R A C I Ó ****\n");
            System.out.println("1. Crear objecte");
            System.out.println("2. Mostrar objectes");
            System.out.println("3. Esborrar objecte");
            System.out.println("4. Assignar objecte a jugador");
            System.out.println("5. Eixir");
            opcio = leerEntero("\nIntrodueix una opcio (1-5): ");

            switch (opcio) {
                case 1:
                    Item a = crearObjecte(armes);

                    if (a != null) {

                        if (armes.isEmpty()) {
                            armes.add(a);
                        } else {
                            Iterator<Item> it = armes.iterator();
                            /*iterator para recorer la llista de items*/
                            boolean exists = false;
                            while (it.hasNext()) {
                                Item tmp = it.next();
                                if (a.equals(tmp)) // comprovem si hi ha algun item amb el mateix nom. Si no l'afegim al ArrayList
                                {
                                    exists = true;
                                }
                            }
                            if (!exists) {
                                armes.add(a);
                            }
                        }
                    }
                    break;
                case 2:
                    mostrar(lista, 'O');
                    break;
                case 3:
                    String nom = leerTexto("Dis-me el nom de l'objecte a esborrar: ");
                    for (Item tmp : armes) {
                        if (tmp.getName().compareTo(nom) == 0) {
                            /* hay que sacar de la lista de items el objeto a eliminar del jugador propietario, si tiene */
                            if (tmp.getPropietari() != null) {
                                System.out.println("El propietari es " + tmp.getPropietari().getName());

                                for (int i = 0; i < tmp.getPropietari().getItems().size(); i++) {
                                    if (tmp.getPropietari().getItems().get(i).getName().compareTo(tmp.getName()) == 0) {
                                        System.out.println("He encontrado el item a eliminar en la posición " + i + " =" + tmp.getPropietari().getItems().get(i).getName());
                                        tmp.getPropietari().remove(tmp.getPropietari().getItems().get(i));
                                        break;
                                    }
                                }
                            }
                            armes.remove(tmp);
                            ok = true;
                            break;
                        }
                    }

                    if (ok) {
                        System.out.println("\nObjecte esborrat correctament.");
                    } else {
                        System.err.println("\nObjecte no existeix.");
                    }
                    break;
                case 4:
                    String nomO = leerTexto("Dis-me el nom de l'objecte: ");
                    String nomJ = leerTexto("Dis-me el nom del jugador: ");

                    for (Item o : armes) {
                        if (o.getName().compareTo(nomO) == 0) {
                            for (Player j : jugadors) {
                                if (j.getName().compareTo(nomJ) == 0) {
                                    j.add(o);
                                    ok = true;
                                }
                            }
                        }
                    }

                    if (ok) {
                        System.out.println("\nL'objecte s'ha assignat al jugador.");
                    } else {
                        System.err.println("\nO l'objecte i/o el jugador no existeixen.");
                    }
                    break;
                case 5:
                    break;
                default:
                    System.err.println("\nOpcio incorrecta");
                    break;
            }
        } while (opcio != 5);
    }

    private static Player crearJugador(ArrayList<Player> jugadors) {
        Player p = null;

        String tipus, nom, sn;
        int patac = -1;
        int vida = -1;

        do {
            tipus = leerTexto("Quin tipus de jugador vols crear (A-Alien, H-Humà, W-Warrior): ");

            if ((tipus.compareTo("A") != 0) && (tipus.compareTo("H") != 0) && (tipus.compareTo("W") != 0)) {
                System.err.println("\nOpcio incorrecta");
            }

        } while ((tipus.compareTo("A") != 0) && (tipus.compareTo("H") != 0) && (tipus.compareTo("W") != 0));

        boolean existe = true;
        do {
            nom = leerTexto("Dis-me el nom del jugador: ");

            existe = false;
            for (Player aux : jugadors) {
                if (aux.getName().compareTo(nom) == 0) {
                    existe = true;
                    System.err.println("\nJa existeix un jugador amb el mateix nom. Tria'n un altre.");
                }
            }
        } while (existe);

        do {
            patac = leerEntero("Dis-me els punts d'atac (1-100): ");
            if ((patac < 1) || (patac > 100)) {
                System.err.println("\nValor incorrecte.");
            }

        } while ((patac < 1) || (patac > 100));

        do {
            sn = leerTexto("Vols personalitzar el valor de vida (S/N)? ");
            if ((sn.compareTo("s") == 0) || (sn.compareTo("S") == 0)) {
                do {
                    vida = leerEntero("Dis-me els punts de vida (1-100): ");
                    if ((vida < 1) || (vida > 100)) {
                        System.err.println("\nValor incorrecte.");
                    }
                } while ((vida < 1) || (vida > 100));
            } else if ((sn.compareTo("n") != 0) && (sn.compareTo("N") != 0)) {
                System.err.println("\nOpció incorrecta.");
            }
        } while ((sn.compareTo("s") != 0) && (sn.compareTo("S") != 0) && (sn.compareTo("n") != 0) && (sn.compareTo("N") != 0));

        switch (tipus) {
            case "A":
                p = new Alien(nom, patac, 100 - patac);
                System.out.println("\nJugador creat correctament!!");
                break;
            case "H":
                p = new Human(nom, patac, 100 - patac);
                System.out.println("\nJugador creat correctament!!");
                break;
            case "W":
                p = new Warrior(nom, patac, 100 - patac);
                System.out.println("\nJugador creat correctament!!");
                break;
            default:
                System.err.println("\nOpcio incorrecta");
                break;
        }
        if (vida > 0) {
            p.setLife(vida);
        }

        return p;
    }

    private static Team crearEquip(ArrayList<Team> equips) {
        Team t = null;
        String nom;

        boolean existe = true;
        do {
            nom = leerTexto("Dis-me el nom de l'equip): ");

            existe = false;
            for (Team aux : equips) {
                if (aux.getName().compareTo(nom) == 0) {
                    existe = true;
                    System.err.println("\nJa existeix un equip amb el mateix nom. Tria'n un altre.");
                }
            }
        } while (existe);

        t = new Team(nom);

        System.out.println("\nEquip creat correctament!!");

        return t;
    }

    private static Item crearObjecte(ArrayList<Item> armes) {
        Item i = null;
        String nom;
        int bonusAtac = 0;
        int bonusDef = 0;

        boolean existe = true;
        do {
            nom = leerTexto("Dis-me el nom de l'objecte: ");

            existe = false;
            for (Item aux : armes) {
                if (aux.getName().compareTo(nom) == 0) {
                    existe = true;
                    System.err.println("\nJa existeix un objecte amb el mateix nom. Tria'n un altre.");
                }
            }
        } while (existe);

        do {
            bonusAtac = leerEntero("Dis-me els punts de bonus d'atac [-10,10]: ");
            if ((bonusAtac < -10) || (bonusAtac > 10)) {
                System.err.println("\nValor incorrecte.");
            }
        } while ((bonusAtac < -10) || (bonusAtac > 10));

        do {
            bonusDef = leerEntero("Dis-me els punts de bonus de defensa [-10,10]: ");
            if ((bonusAtac < -10) || (bonusAtac > 10)) {
                System.err.println("\nValor incorrecte.");
            }
        } while ((bonusDef < -10) || (bonusDef > 10));

        i = new Item(nom, bonusAtac, bonusDef);

        System.out.println("\nObjecte creat correctament!!");

        return i;
    }

    private static void mostrar(ArrayList<Object> lista, char tipus) {
        Iterator<Object> it = lista.iterator();
        Object tmp = null;

        while (it.hasNext()) {
            switch (tipus) {
                case 'J':
                    tmp = (Player) it.next();
                    break;
                case 'E':
                    tmp = (Team) it.next();
                    break;
                case 'O':
                    tmp = (Item) it.next();
                    break;
                default:
                    System.out.println("\n No es pot mostrar la llista.");
                    break;
            }
            System.out.println("\n");
            System.out.println(tmp);
        }
    }    

    public static ArrayList<Player> ordenar(ArrayList<Player> jugadores) {

        int aux;
        int t = jugadores.size();
        ArrayList<Player> elementos = (ArrayList<Player>) jugadores.clone();
        Collections.sort(elementos);

        return elementos;
    }

    private static Player obtenirRival(Player atacant, ArrayList<Player> jugadors) {

        Player rival = null;
        boolean ok = false;

        do {
            int indxRival = (int) (Math.random() * jugadors.size());

            if ((jugadors.get(indxRival).getLife() > 0) && (jugadors.get(indxRival) != atacant)) {
                rival = jugadors.get(indxRival);
                ok = true;
            }
        } while (!ok);

        return rival;
    }

    private static int comprovarVius(ArrayList<Player> jugadors) {
        int vius = 0;

        for (Player p : jugadors) {
            if (p.getLife() > 0) {
                vius += 1;
            }
        }
        return vius;
    }
    
/**
 * 
 *   A partir d'aquest pun trobarem mètodes per a fer proves de la funcionalitas
 *   del joc.
 */
    
    public static void provaFase() {

        try {
            
            //System.out.println("Vaig a crear un humanoide");
            Human humanoid = new Human("John Smith", 13, 8);
            
            //System.out.println("Vaig a crear un guerrer");
            Warrior guerrer = new Warrior("Brave", 10, 25);
            
            //System.out.println("Vaig a crear un alienigena");
            Alien alienigena = new Alien("Martian PK", 27, 2);
            
            // Creació de les armes (items)
            Item espada = new Item("Espada", 10, 2);
            Item latigo = new Item("Látigo", 6, -5);
            Item gafas = new Item("Gafas laser", 5, -5);
            Item escudo = new Item("Escudo", -3, 12);
            Item molotov = new Item("Cóctel molotov", -7, 7);
            Item veneno = new Item("Veneno", 6, -6);
            
            // Dotar d'armes als jugadors
            /**
             * humanoid (John Smith) : latigo + escudo --> bonus ataque = 3, bonus
             * defensa = 7 guerrer (Brave) : espada + gafas + molotov --> bonus
             * ataque = 8 bonus defensa = 4 alienigena (Martian PK): veneno -->
             * bonus ataque = 6 bonus defensa = -6
             */
            guerrer.add(espada);
            guerrer.add(latigo);
            humanoid.add(latigo); /* No l'ha de poder asignar */
            guerrer.remove(gafas); /* No l'ha de poder llevar, no és el propietari*/
            guerrer.remove(latigo);
            humanoid.add(latigo);
            guerrer.add(gafas);
            guerrer.add(molotov);
            alienigena.add(veneno);
            humanoid.add(escudo);
            
            Team equipA = new Team("Flipats");
            Team equipB = new Team("Guais");
            Team equipC = new Team("Carabasses");
            
            humanoid.add(equipA);
            equipA.add(guerrer);
            equipB.add(humanoid);
            guerrer.add(equipB);
            humanoid.add(equipA);
            equipB.add(guerrer);
            
            System.out.println("Mostrar jugadores del equipo A");
            try {
                for (Player p : equipA.getPlayers()) {
                    System.out.println(p.getName());
                }
            } catch (rolExceptions ex) {
                System.err.println(ex);
            }
            
            System.out.println("***** LLISTA D'EQUIPS *****");
            System.out.println(equipA);
            System.out.println(equipB);
            System.out.println(equipC);
            
            System.out.println("\n***** LLISTA DE JUGADORS *****");
            System.out.println(humanoid);
            System.out.println(guerrer);
            System.out.println(alienigena);
            
            equipC.remove(humanoid);
            guerrer.remove(equipB);
            
            System.out.println("***** LLISTA D'EQUIPS *****");
            System.out.println(equipA);
            System.out.println(equipB);
            System.out.println(equipC);
            
            System.out.println("\n***** LLISTA DE JUGADORS *****");
            System.out.println(humanoid);
            System.out.println(guerrer);
            System.out.println(alienigena);
            
            iniciarBatalla(humanoid, guerrer, alienigena);
        } catch (rolExceptions ex) {
            System.err.println(ex);
        }
    }
    
    /* Mètode utilitzat per a crear jugadors, equipos i relacionar-los. */
    public static void generaTest(ArrayList<Player> jugadors, ArrayList<Team> equips, ArrayList<Item> armes) {
       
        Human messi = new Human("Messi", 25, 8);
        Human suarez = new Human("Suarez", 13, 2);

        
        Warrior benzema = new Warrior("Benzema", 68, 45);
        Warrior hazard = new Warrior("Hazard", 27, 15);
        Warrior poli = new Warrior("Poli", 100, 50);

       
        Alien parejo = new Alien("Parejo", 72, 28);
        Alien gaya = new Alien("Gaya", 28, 23);

        Team Valencia = new Team("Valencia");
        Team Barça = new Team("Barça");
        Team Madrid = new Team("Madrid");
        
        // Creació de les armes (items)
        Item espada = new Item("Espada", 10, 2);
        Item latigo = new Item("Látigo", 6, -5);
        Item gafas = new Item("Gafas laser", 5, -5);
        Item escudo = new Item("Escudo", -3, 12);
        Item molotov = new Item("Cóctel molotov", -7, 7);
        Item veneno = new Item("Veneno", 6, -6);

        messi.add(Barça);
        Barça.add(suarez);
        Valencia.add(parejo);
        gaya.add(Valencia);
        benzema.add(Madrid);
        Madrid.add(hazard);
        poli.add(Barça);
        poli.add(Valencia);
        poli.add(Madrid);
        
        messi.add(gafas);
        benzema.add(escudo);
        poli.add(veneno);
        parejo.add(molotov);
        benzema.add(latigo);
        gaya.add(espada);

        jugadors.add(messi);
        jugadors.add(suarez);
        jugadors.add(parejo);
        jugadors.add(gaya);
        jugadors.add(benzema);
        jugadors.add(hazard);
        jugadors.add(poli);

        equips.add(Barça);
        equips.add(Valencia);
        equips.add(Madrid);
        
        armes.add(veneno);
        armes.add(espada);
        armes.add(latigo);
        armes.add(gafas);
        armes.add(escudo);
        armes.add(molotov);
    }

    private static void atacar(Player A, Player B) throws rolExceptions{

        if (A.getLife() > 0) {
            try {
                System.out.println("//  ABANS DE L'ATAC:");
                System.out.println("Atacant: " + A.toString());
                System.out.println("Atacat: " + B.toString());
                A.attack(B);
                System.out.println("//  DESPRÉS DE L'ATAC:");
                System.out.println("Atacant: " + A.toString());
                System.out.println("Atacat: " + B.toString());
            } catch (rolExceptions ex) {
                System.err.println(ex);
            }
        }
    }

    private static void iniciarBatalla(Player humanoid, Player guerrer, Player alienigena){
        try {
            System.out.println("\n\n************ ATAC 1 *************");
            atacar(humanoid, alienigena);
            System.out.println("");
            
            System.out.println("************ ATAC 2 *************");
            atacar(guerrer, alienigena);
            System.out.println("");
            
            System.out.println("************ ATAC 3 *************");
            atacar(guerrer, humanoid);
            System.out.println("");
            
            System.out.println("************ ATAC 4 *************");
            atacar(guerrer, humanoid);
            System.out.println("");
        } catch (rolExceptions ex) {
            System.err.println(ex);
        }
    }

}
