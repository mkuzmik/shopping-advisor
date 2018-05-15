package pl.edu.agh.sp.shoppingadvisor.ml;

import java.util.Collection;

public class RecommendationOutput {

  private Collection<AssertedOffer> asserted_offers;

  public Collection<AssertedOffer> getAsserted_offers() {
    return asserted_offers;
  }

  public void setAsserted_offers(Collection<AssertedOffer> asserted_offers) {
    this.asserted_offers = asserted_offers;
  }

  private static class AssertedOffer {

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
