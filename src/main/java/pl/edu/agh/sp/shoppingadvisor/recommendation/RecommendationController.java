package pl.edu.agh.sp.shoppingadvisor.recommendation;

import com.google.common.collect.Lists;
import groovy.util.Eval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.sp.shoppingadvisor.notifications.mail.EmailMessage;
import pl.edu.agh.sp.shoppingadvisor.notifications.mail.EmailService;
import pl.edu.agh.sp.shoppingadvisor.offer.EvaluatedOffer;
import pl.edu.agh.sp.shoppingadvisor.offer.EvaluatedOfferRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/recommendation/run")
public class RecommendationController {

  @Autowired
  private RecommendationService recommendationService;

  @Autowired
  private EvaluatedOfferRepository evaluatedOfferRepository;

  @Autowired
  private EmailService emailService;

  @GetMapping
  public void runRunRecommendation() {
    recommendationService.recommend();

    Map<String, List<EvaluatedOffer>> offersByEmail = StreamSupport.stream(evaluatedOfferRepository.findAll().spliterator(), false)
      .collect(Collectors.groupingBy(o -> o.getOwner().getEmail()));

    offersByEmail.keySet()
      .forEach(mail -> {
        List<EvaluatedOffer> offers = offersByEmail.get(mail);
        offers.sort(Comparator.comparing(EvaluatedOffer::getEvaluation));
        String topOffers = offers.stream().limit(3).map(EvaluatedOffer::getUrl).collect(Collectors.joining("\n"));
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setText("We have found top 3 offers for you: \n" + topOffers);
        emailMessage.setTo(mail);
        emailMessage.setSubject("Your top offers");
        emailService.send(emailMessage);
      });
  }
}
