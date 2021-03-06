package pl.edu.agh.sp.shoppingadvisor.offer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OfferAccumulator implements OfferSearcher {

    private List<OfferSearcher> offerSearchers = new ArrayList<>();

    public void add(OfferSearcher offerSearcher) {
        this.offerSearchers.add(offerSearcher);
    }

    @Override
    public Collection<Offer> searchFor(String searchPhrase) {
        return searchFor(searchPhrase, 0);
    }

    @Override
    public Collection<Offer> searchFor(String searchPhrase, int offset) {
        return offerSearchers.stream()
                .flatMap(offerSearcher -> offerSearcher.searchFor(searchPhrase).stream())
                .collect(Collectors.toList());
    }
}
