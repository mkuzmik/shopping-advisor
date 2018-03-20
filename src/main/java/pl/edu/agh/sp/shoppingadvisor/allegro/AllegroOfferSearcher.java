package pl.edu.agh.sp.shoppingadvisor.allegro;

import api.allegro.wsdl.DoGetItemsListResponse;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferSearcher;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferViewModel;

import java.util.Collection;
import java.util.stream.Collectors;

public class AllegroOfferSearcher implements OfferSearcher {

    AllegroClient allegroClient;

    public AllegroOfferSearcher(AllegroClient allegroClient) {
        this.allegroClient = allegroClient;
    }

    @Override
    public Collection<OfferViewModel> searchFor(String searchPhrase) {
        DoGetItemsListResponse doGetItemsListResponse = allegroClient.getItems(searchPhrase);
        return doGetItemsListResponse.getItemsList().getItem().stream()
                .map(AllegroOfferFactory::createOfferFrom)
                .collect(Collectors.toList());
    }
}
