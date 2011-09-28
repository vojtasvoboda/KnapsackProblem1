package batoh;

import java.util.Iterator;

/**
 *
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class GreedyAlgorithm implements IAlgorithm {

    private Batoh batoh;
    private Barak barak;

    public GreedyAlgorithm(Barak barak, Batoh batoh) {
        this.barak = barak;
        this.batoh = batoh;
    }

    public void computeStolenItems() {
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
    }

}
