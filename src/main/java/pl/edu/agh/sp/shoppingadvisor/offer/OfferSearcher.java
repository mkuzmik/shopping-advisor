package pl.edu.agh.sp.shoppingadvisor.offer;

import java.util.Collection;

public interface OfferSearcher {

    Collection<OfferViewModel> searchFor(String searchPhrase);
}
