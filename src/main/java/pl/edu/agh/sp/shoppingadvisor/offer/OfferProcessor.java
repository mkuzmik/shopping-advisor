package pl.edu.agh.sp.shoppingadvisor.offer;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.sp.shoppingadvisor.ml.RecommendationInput;
import pl.edu.agh.sp.shoppingadvisor.scraper.AllegroScraper;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class OfferProcessor {

  @Autowired
  private AllegroScraper allegroScraper;

  public RecommendationInput inputOf(Collection<Offer> learingOffers, Collection<EvaluatedOffer> offersToEvaluate) {
    RecommendationInput recommendationInput = new RecommendationInput();

    recommendationInput.setTraining_offers(learingOffers.stream().map(offer -> {
      RecommendationInput.Offer inputOffer = RecommendationInput.Offer.of(offer);
      String description = Try.of(() -> String.join(" ", allegroScraper.scrapDescription(offer.getUrl()))).getOrElse("");
      inputOffer.setDescription(description);
      return inputOffer;
    }).collect(Collectors.toList()));

    recommendationInput.setTest_offers(offersToEvaluate.stream().map(offer -> {
      RecommendationInput.Offer inputOffer = RecommendationInput.Offer.of(offer);
      String description = Try.of(() -> String.join(" ", allegroScraper.scrapDescription(offer.getUrl()))).getOrElse("");
      inputOffer.setDescription(description);
      return inputOffer;
    }).collect(Collectors.toList()));

    return recommendationInput;
  }
}
