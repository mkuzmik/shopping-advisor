package pl.edu.agh.sp.shoppingadvisor.offer;

import java.util.Collection;

public interface OfferSearcher {

    Collection<Offer> searchFor(String searchPhrase);
    Collection<Offer> searchFor(String searchPhrase, int offset);
}
