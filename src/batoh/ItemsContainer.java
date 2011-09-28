package batoh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Spolecny predek pro batoh a barak
 * Obe entity totiz umoznuji skladovat polozky, pridavat, ubirat
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class ItemsContainer {

    /**
     * Polozky kontejneru
     */
    public List<BatohItem> polozky;

    /**
     * Konstruktor
     */
    public ItemsContainer() {
        this.polozky = new ArrayList<BatohItem>();
    }

    /**
     * Vrati pole polozek
     * @return
     */
    public List<BatohItem> getPolozky() {
        return polozky;
    }

    /**
     * Seradi polozky dle pomeru cena/vaha
     */
    public void orderItems() {
        Collections.sort(this.polozky);
    }

    /**
     * Vymaze vsechny veci
     */
    public void clear() {
        this.polozky.clear();
    }

}
