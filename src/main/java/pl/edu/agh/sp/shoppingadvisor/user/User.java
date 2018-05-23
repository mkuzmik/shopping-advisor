package pl.edu.agh.sp.shoppingadvisor.user;

import pl.edu.agh.sp.shoppingadvisor.offer.EvaluatedOffer;
import pl.edu.agh.sp.shoppingadvisor.offer.Offer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    private String email;

    private String query;

    @OneToMany(mappedBy = "owner")
    private List<Offer> prefferedOffers;

    @OneToMany(mappedBy = "owner")
    private List<EvaluatedOffer> evaluatedOffers;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Offer> getPrefferedOffers() {
        return prefferedOffers;
    }

    public List<EvaluatedOffer> getEvaluatedOffers() {
        return evaluatedOffers;
    }

    public void setEvaluatedOffers(List<EvaluatedOffer> evaluatedOffers) {
        this.evaluatedOffers = evaluatedOffers;
    }

    public void setPrefferedOffers(List<Offer> prefferedOffers) {
        this.prefferedOffers = prefferedOffers;
    }
}
