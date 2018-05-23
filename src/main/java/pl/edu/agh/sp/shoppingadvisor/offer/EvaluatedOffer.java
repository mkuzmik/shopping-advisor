package pl.edu.agh.sp.shoppingadvisor.offer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.agh.sp.shoppingadvisor.user.User;

import javax.persistence.*;

@Entity
@Table(name = "evaluated_offer", schema = "public")
public class EvaluatedOffer {

  public static EvaluatedOffer of(Offer offer) {
    return new EvaluatedOffer(offer.getUrl(), 0.5d, offer.getOwner());
  }

  @Id
  private String url;

  private double evaluation;

  @ManyToOne
  @JoinColumn(name="owner")
  @JsonIgnore
  private User owner;

  public EvaluatedOffer() {
  }

  public EvaluatedOffer(String url, double evaluation, User owner) {
    this.url = url;
    this.evaluation = evaluation;
    this.owner = owner;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public double getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(double evaluation) {
    this.evaluation = evaluation;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
}
