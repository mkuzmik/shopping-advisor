package pl.edu.agh.sp.shoppingadvisor.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.sp.shoppingadvisor.ml.FlaskService;
import pl.edu.agh.sp.shoppingadvisor.offer.*;
import pl.edu.agh.sp.shoppingadvisor.user.User;
import pl.edu.agh.sp.shoppingadvisor.user.UserRepository;

import java.util.*;
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
    Collection<EvaluatedOffer> randomOffersToEvaluate = getRandomOffersToEvaluate(20, user.getQuery(), learningOffers);
    learningOffers = processLearningOffers(learningOffers);
    Collection<EvaluatedOffer> evaluatedOffers = flaskService.evaluate(learningOffers, randomOffersToEvaluate, user);
    evaluatedOffers.forEach(evaluatedOfferRepository::save);
  }

  private Collection<EvaluatedOffer> getRandomOffersToEvaluate(int amount, String searchPhrase, List<Offer> learningOffers) {
    Map<String, List<Offer>> learningOffersByUrl = learningOffers.stream().collect(Collectors.groupingBy(Offer::getUrl));

    List<Offer> offers = new ArrayList<>(offerSearcher.searchFor(searchPhrase)).stream()
            .filter(offer -> !learningOffersByUrl.containsKey(offer.getUrl()))
            .collect(Collectors.toList());

    return IntStream.range(0,amount).boxed().map(n -> {
      Collections.shuffle(offers);
      return EvaluatedOffer.of(offers.get(0));
    }).collect(Collectors.toList());
  }

  private List<Offer> processLearningOffers(List<Offer> offers) {
    Collections.shuffle(offers);

    long badFeedbackAmount = offers.stream().filter(offer -> offer.getFeedback() == 0).count();
    long goodFeedbackAmount = offers.stream().filter(offer -> offer.getFeedback() == 1).count();

    long learningAmount = goodFeedbackAmount < badFeedbackAmount ? goodFeedbackAmount : badFeedbackAmount;

    List<Offer> goodFeedbackOffers = offers.stream()
            .filter(offer -> offer.getFeedback() == 1)
            .limit(learningAmount)
            .collect(Collectors.toList());

    List<Offer> badFeedbackOffers = offers.stream()
            .filter(offer -> offer.getFeedback() == 0)
            .limit(learningAmount)
            .collect(Collectors.toList());

    ArrayList<Offer> resultOffers = new ArrayList<>(goodFeedbackOffers);
    resultOffers.addAll(badFeedbackOffers);
    return resultOffers;
  }

}
