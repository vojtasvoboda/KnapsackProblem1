package batoh;

import com.sun.org.apache.xalan.internal.xsltc.dom.BitArray;
import java.util.List;

/**
 * Implementace algoritmu hrubou silou
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class BruteForceAlgorithm implements IAlgorithm {

    private Batoh batoh;
    private Barak barak;
    private int bestCena = 0;
    private int bestCenaVaha = 0;
    private List<BatohItem> bestPolozky = null;

    public BruteForceAlgorithm(Barak barak, Batoh batoh) {
        this.barak = barak;
        this.batoh = batoh;
    }

    /**
     * Spustime algoritmus prochazeni stavoveho prostoru
     */
    public void computeStolenItems() {
        /* zjistime si kolik je celkem polozek v baraku */
        int celkemPolozek = this.barak.getItemsCount();
        /* kazda polozka v batohu je, nebo neni */
        int celkemMoznychStavu = (int) Math.pow(2, celkemPolozek);
        // System.out.println("Celkem moznych polozek je " + celkemPolozek + ", celkem moznych stavu je " + celkemMoznychStavu);
        /* prochazime vse, takze postupne zaciname vlozenim jednotlive polozky podle bitoveho vektoru */
        List<BatohItem> polozky = this.barak.getPolozky();
        BatohItem item = null;
        int aktualniCena = 0;
        int indexPolozky = 0;
        /* musime projit vsechny stavy stavoveho prostoru */
        for (int i = 0; i < celkemMoznychStavu; i++) {
            /* zjistime si pole bitu */
            String poleBitu = Integer.toBinaryString(i);
            // System.out.println("Pole bitu je " + poleBitu);
            /* vysypeme batoh */
            batoh.clear();
            /* projdeme cely vektor bitu = naplnime batoh */
            for (int j = 0; j < poleBitu.length(); j++) {
                /* index polozky */
                indexPolozky = poleBitu.length() - j - 1;
                /* pokud je ve vektoru jednicka, pridame polozku */
                if ( poleBitu.charAt(j) == '1' ) {
                    // System.out.println("Zkusime pridat polozku " + indexPolozky + " coz je v/c " + polozky.get(j).getHodnota() + "/" + polozky.get(j).getVaha());
                    /* pokud je uz batoh plny, tak break */
                    if ( this.batoh.isFull() ) break;
                    /* jinak pridame dalsi polozku */
                    item = polozky.get(indexPolozky);
                    this.batoh.addItem(item);
                }
            }
            /* mrkneme kolik se podarilo ukradnout a pokud je to nejlepsi vysledek, tak ulozime */
            aktualniCena = this.batoh.getAktualniCena();
            if (aktualniCena > this.bestCena ) {
                // System.out.println("Nasli jsme lepsi reseni s cenou " + aktualniCena + " a vahou " + this.batoh.getAktualniZatizeni());
                this.bestCena = aktualniCena;
                this.bestCenaVaha = this.batoh.getAktualniZatizeni();
                this.bestPolozky = this.batoh.getPolozky();
            }
        }
        /* konec algoritmu, takze musime do batohu dat nejlepsi vysledek */
        this.batoh.setPolozky(this.bestPolozky);
        this.batoh.setAktualniCena(this.bestCena);
        this.batoh.setAktualniZatizeni(this.bestCenaVaha);
    }

    /**
     * Vypise pole bitu
     * @param ba - bit array
     */
    public void writeBitArray(BitArray ba) {
        System.out.print("BitArray {");
        for (int i = 0; i < ba.size(); i++) {
            if ( ba.getBit(i) ) {
                System.out.print("1,");
            } else {
                System.out.print("0,");
            }
        }
        System.out.println("}");
    }

}
