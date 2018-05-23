package pl.edu.agh.sp.shoppingadvisor.ml;

import pl.edu.agh.sp.shoppingadvisor.offer.EvaluatedOffer;
import pl.edu.agh.sp.shoppingadvisor.user.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class RecommendationOutput {

  private Collection<AssertedOffer> asserted_offers;

  public Collection<AssertedOffer> getAsserted_offers() {
    return asserted_offers;
  }

  public Collection<EvaluatedOffer> getEvaluatedOffers(User owner) {
    return asserted_offers.stream().map(assertedOffer -> {
      EvaluatedOffer evaluatedOffer = assertedOffer.toEvaluatedOffer();
      evaluatedOffer.setOwner(owner);
      return evaluatedOffer;
    }).collect(Collectors.toList());
  }

  public void setAsserted_offers(Collection<AssertedOffer> asserted_offers) {
    this.asserted_offers = asserted_offers;
  }

  private static class AssertedOffer {

    public EvaluatedOffer toEvaluatedOffer() {
      EvaluatedOffer evaluatedOffer = new EvaluatedOffer();
      evaluatedOffer.setUrl(url);
      evaluatedOffer.setEvaluation(feedback);
      return evaluatedOffer;
    }

    private String url;
    private float feedback;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public float getFeedback() {
      return feedback;
    }

    public void setFeedback(float feedback) {
      this.feedback = feedback;
    }
  }
}
