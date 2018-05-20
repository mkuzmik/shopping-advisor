package pl.edu.agh.sp.shoppingadvisor.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.sp.shoppingadvisor.ml.FlaskService;
import pl.edu.agh.sp.shoppingadvisor.offer.*;
import pl.edu.agh.sp.shoppingadvisor.user.User;
import pl.edu.agh.sp.shoppingadvisor.user.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RecommendationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OfferRepository offerRepository;

  @Autowired
  private OfferSearcher offerSearcher;

  @Autowired
  private EvaluatedOfferRepository evaluatedOfferRepository;

  @Autowired
  private FlaskService flaskService;

  public void recommend() {
    userRepository.findAll().forEach(this::recommendFor);
  }

  public void recommendFor(User user) {
    List<Offer> learningOffers = user.getPrefferedOffers();
    Collection<EvaluatedOffer> randomOffersToEvaluate = getRandomOffersToEvaluate(20, user.getQuery());
    Collection<EvaluatedOffer> evaluatedOffers = flaskService.evaluate(learningOffers, randomOffersToEvaluate, user);
    evaluatedOffers.forEach(evaluatedOfferRepository::save);
  }

  public Collection<EvaluatedOffer> getRandomOffersToEvaluate(int amount, String searchPhrase) {
    List<Offer> offers = new ArrayList<>(offerSearcher.searchFor(searchPhrase));
    return IntStream.range(0,amount).boxed().map(n -> {
      Collections.shuffle(offers);
      return EvaluatedOffer.of(offers.get(0));
    }).collect(Collectors.toList());
  }

}
