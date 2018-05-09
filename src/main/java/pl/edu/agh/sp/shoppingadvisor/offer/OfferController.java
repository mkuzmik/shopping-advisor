package pl.edu.agh.sp.shoppingadvisor.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OfferController {

    private final OfferSearcher offerSearcher;
    private int i = 0;

    @Autowired
    public OfferController(OfferSearcher offerSearcher) {
        this.offerSearcher = offerSearcher;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Collection<Offer> searchForOffers(@RequestParam("q") String searchPhrase,
                                             @RequestParam(value="o", defaultValue="0") int offset) {
        return offerSearcher.searchFor(searchPhrase, offset);
    }

    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    public String assessOffer(@RequestBody String searchPhrase) {
        return "ok";
    }
}
