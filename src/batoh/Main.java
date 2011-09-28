package batoh;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class Main {

    /* nosnost batohu pro testovaci funkci */
    final static int NOSNOST = 32;
    /* maximalni pocet nactenych instanci */
    final static int MAX_INSTANCES = 2;

    /**
     * Polozky v baraku lze nacist bud z testovaci mnoziny - funkce loadTestItems,
     * nebo ze souboru - funkce loadItemsFromFile
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /* zapnu mereni casu */
        long startTime = System.currentTimeMillis();

        /* definuju si polozky v baraku */
        Barak barak = new Barak();

        /* definuju si batoh */
        Batoh batoh = new Batoh(NOSNOST);

        /* nacteme testovaci vstupy */
        // loadTestItems(barak);

        /* nacteme instance ze souboru */
        /* soubory jsou: 4, 10, 15, 20, 22, 25, 27, 30, 32, 35, 37, 40 */
        String[][] instanceProblemu = loadFile(4);

        /* projdeme vsechny instance a vypocitame je */
        List<BatohItem> polozkyVbatohu = null;
        for ( int i = 0; i < instanceProblemu.length && i < MAX_INSTANCES; i++ ) {
            /* naplnime barak polozkami */
            System.out.println("Startuji plneni baraku ze vstupni instance cislo " + instanceProblemu[i][0]);
            loadItemFromFile(barak, instanceProblemu[i]);
            /* nastavime nosnost batohu */
            batoh.setNosnost(Integer.parseInt(instanceProblemu[i][2]));
            /* ziskej polozky v batohu z baraku */
            polozkyVbatohu = greedyAlg(barak, batoh);
            /* vypiseme polozky */
            System.out.println("Vypis polozek v batohu instance " + instanceProblemu[i][0]);
            vypisPolozky(polozkyVbatohu);
            System.out.println("Podarilo se ukrast veci v hodnote " + batoh.getAktualniCena() +
                               " s vytizenim batohu " + batoh.getAktualniZatizeni() + "/" + batoh.getNosnost());
            /* vycistime batoh i barak pred dalsi instanci */
            barak.clear();
            batoh.clear();
        }

        /* konec mereni casu */
        long endTime = System.currentTimeMillis();
        startTime = endTime - startTime;
        System.out.println("Vypocet trval " + startTime + "ms");
    }

    /**
     * Implementace hladoveho algoritmu, ktery vraci polozky, ktere
     * se podarili ukrast
     * @param barak
     * @param batoh
     * @return List<BatohItem> - polozky batohu
     */
    public static List<BatohItem> greedyAlg(Barak barak, Batoh batoh) {
        Iterator iterator = barak.getPolozky().iterator();
        BatohItem item = null;
        /* prochazime vsechny polozky, dokud neni batoh plny */
        while( !batoh.isFull() && iterator.hasNext() ) {
            item = (BatohItem) iterator.next();
            System.out.println("Zkusim ukrast polozku {" + item.getHodnota() + "," + item.getVaha() + "," + item.getPomer() + "}, akt zatizeni je " + batoh.getAktualniZatizeni());
            if ( !batoh.addItem(item) ) {
                System.out.println("Polozka {" + item.getHodnota() + "," + item.getVaha() + "," + item.getPomer() + "} se uz nevejde do batohu.");
                // break; // zkousim pridavat az dokonce
            }
        }
        /* vratime ukradene polozky */
        return batoh.getPolozky();
    }

    /**
     * Vypise polozky batohu/baraku
     * @param polozky
     */
    public static void vypisPolozky(List<BatohItem> polozky) {
        Iterator iterator = polozky.iterator();
        BatohItem item = null;
        while ( iterator.hasNext() ) {
            item = (BatohItem) iterator.next();
            System.out.print("{" + item.getHodnota() + "," + item.getVaha() + "," + item.getPomer() + "},");
        }
        System.out.println("");
    }

    /**
     * Nacteme polozky ze souboru
     * @param i - cislo souboru
     * @return Strin[][] - rozparsovany polozky v baraku
     */
    private static String[][] loadFile(int i) {
        String soubor = System.getProperty("user.dir") + "\\data\\knap_" + i + ".inst.dat";
        System.out.println("Nacitam soubor: " + soubor + ", existuje: " + new File(soubor).exists());
        FileInputStream fstream = null;
        DataInputStream in = null;
        BufferedReader br = null;
        String[][] instance = new String[100][100];
        try {
            /* Zkopirovano z http://www.roseindia.net/java/beginners/java-read-file-line-by-line.shtml */
            fstream = new FileInputStream(soubor);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            String strLine = "";
            int idecko = 0;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                System.out.println("Ctu radek " + strLine + ", ktery bude na indexu " + idecko);
                String[] radek = strLine.split(" ");
                instance[idecko] = radek;
                idecko++;
            }
            // Close the input stream
            in.close();

        } catch (Exception e) {
            System.err.println("Chyba v nacitani souboru, error: " + e);
            return null;
        }
        return instance;
    }

    /**
     * Z nactene radky souboru naplni barak polozkama
     * @param barak
     * @param parametry
     */
    private static void loadItemFromFile(Barak barak, String[] parametry) {
        /* pocet zaznamu ke cteni */
        int pocetZaznamu = (parametry.length - 3) / 2;
        System.out.println("Pocet zaznamu k nacteni je " + pocetZaznamu);
        int j = pocetZaznamu - 1;
        for( int i = 0; i < pocetZaznamu; i++, j = j+2 ) {
            System.out.println("Pridavam polozku {" + parametry[j] + "," + parametry[j+1] + "}");
            barak.addItem(
                    new BatohItem(
                        Integer.parseInt(parametry[j+1]),
                        Integer.parseInt(parametry[j])
                        )
                    );
        }
    }

    /**
     * Nacteme testovaci vstup
     * Vysledek by mel byt 126, nebo 127 (v pripade lokalniho dohledavani)
     */
    public static void loadTestItems(Barak barak) {
        barak.addItem(new BatohItem(15, 17));
        barak.addItem(new BatohItem(26, 1));
        barak.addItem(new BatohItem(4, 29));
        barak.addItem(new BatohItem(18, 11));
        barak.addItem(new BatohItem(11, 15));
        barak.addItem(new BatohItem(22, 6));
        barak.addItem(new BatohItem(25, 6));
        barak.addItem(new BatohItem(17, 7));
        barak.addItem(new BatohItem(35, 8));
        barak.addItem(new BatohItem(1, 2));
        barak.addItem(new BatohItem(5, 32));
    }
}
