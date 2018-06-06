package pl.edu.agh.sp.shoppingadvisor.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.sp.shoppingadvisor.offer.EvaluatedOffer;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/preferences")
public class UserPreferencesController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  OfferRepository offerRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void savePreferences(@RequestBody UserPreferencesInput userPreferencesInput) {
    User user = userPreferencesInput.getUser();
    userRepository.save(user);
    userPreferencesInput.getOffers().stream()
            .peek(offer -> offer.setOwner(user))
            .forEach(offer -> offerRepository.save(offer));
  }

  @GetMapping
  public List<EvaluatedOffer> getEvaluatedOffers(@RequestParam("mail") String mail) {
    Optional<User> maybeUser = userRepository.findById(mail);
    List<EvaluatedOffer> evaluatedOffers = maybeUser.isPresent() ? maybeUser.get().getEvaluatedOffers() : new ArrayList<>();
    evaluatedOffers.sort(Comparator.comparing(EvaluatedOffer::getEvaluation));
    return evaluatedOffers;
  }
}
