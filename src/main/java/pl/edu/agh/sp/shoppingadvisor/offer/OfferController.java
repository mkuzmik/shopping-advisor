package pl.edu.agh.sp.shoppingadvisor.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class OfferController {

    private final OfferSearcher offerSearcher;

    @Autowired
    public OfferController(OfferSearcher offerSearcher) {
        this.offerSearcher = offerSearcher;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Collection<OfferViewModel> searchForOffers(@RequestParam("q") String searchPhrase) {
        return offerSearcher.searchFor(searchPhrase);
    }
}
