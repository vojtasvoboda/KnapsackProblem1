package batoh;

import com.sun.org.apache.xalan.internal.xsltc.dom.BitArray;
import java.util.BitSet;
import java.util.List;

/**
 * Implementace algoritmu hrubou silou
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class BruteForceAlgorithm implements IAlgorithm {

    private Batoh batoh;
    private Barak barak;

    public BruteForceAlgorithm(Barak barak, Batoh batoh) {
        this.barak = barak;
        this.batoh = batoh;
    }

    public void computeStolenItems() {
        /* zjistime si kolik je celkem polozek v baraku */
        int celkemPolozek = barak.getItemsCount();
        /* kazda polozka v batohu je, nebo neni */
        int celkemMoznychStavu = 2^celkemPolozek;
        /* prochazime vse, takze postupne zaciname vlozenim jednotlive polozky dle bitoveho vektoru */
        List<BatohItem> polozky = barak.getPolozky();
        BatohItem item = null;
        /* musime projit vsechny stavy stavoveho prostoru */
        for (int i = 0; i < celkemPolozek; i++) {
            /* zjistime si pole bitu */
            String pole = Integer.toBinaryString(i);
            System.out.println("Binarni vektor je " + pole);
            int delkaPole = pole.length();
            /* projdeme cely vektor */
            for (int j = 0; j < delkaPole; j++) {
                /* pokud je ve vektoru jednicka, pridame polozku */
                /* TODO !batoh.isFull() */
                System.out.println("Pole je " + pole + ", index zatim " + j);
                System.out.println("CharAt(" + j + ") je " + pole.charAt(j));
                /*
                if ( pole.charAt(i) == '1' ) {
                    System.out.println("Pridavame polozku c. " + i);
                }
                */
            }
        }
    }

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
