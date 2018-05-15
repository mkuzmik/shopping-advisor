package pl.edu.agh.sp.shoppingadvisor.ml;

import java.util.Collection;

public class RecommendationInput {

  private Collection<Offer> training_offers;

  private Collection<Offer> test_offers;

  public Collection<Offer> getTraining_offers() {
    return training_offers;
  }

  public void setTraining_offers(Collection<Offer> training_offers) {
    this.training_offers = training_offers;
  }

  public Collection<Offer> getTest_offers() {
    return test_offers;
  }

  public void setTest_offers(Collection<Offer> test_offers) {
    this.test_offers = test_offers;
  }

  public static class Offer {

    private String url;

    private String description;

    private int feedback;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public int getFeedback() {
      return feedback;
    }

    public void setFeedback(int feedback) {
      this.feedback = feedback;
    }
  }
}
