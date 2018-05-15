package pl.edu.agh.sp.shoppingadvisor.user;

import pl.edu.agh.sp.shoppingadvisor.offer.Offer;

import java.util.Collection;

public class UserPreferencesInput {

  private User user;

  private Collection<Offer> offers;

  public UserPreferencesInput() {
  }

  public UserPreferencesInput(User user, Collection<Offer> offers) {
    this.user = user;
    this.offers = offers;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Collection<Offer> getOffers() {
    return offers;
  }

  public void setOffers(Collection<Offer> offers) {
    this.offers = offers;
  }
}
