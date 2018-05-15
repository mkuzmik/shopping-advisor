package pl.edu.agh.sp.shoppingadvisor.offer;

import pl.edu.agh.sp.shoppingadvisor.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "offer", schema = "public")
public class Offer {

    private String title;

    private String imgUrl;

    @Id
    private String url;

    private float price;

    private int feedback;

    @ManyToOne
    @JoinColumn(name="owner")
    private User owner;

    /*JPA*/
    public Offer() {
    }

    public Offer(String title, String imgUrl, String url, float price) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.url = url;
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Float.compare(offer.price, price) == 0 &&
                feedback == offer.feedback &&
                Objects.equals(title, offer.title) &&
                Objects.equals(imgUrl, offer.imgUrl) &&
                Objects.equals(url, offer.url) &&
                Objects.equals(owner, offer.owner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, imgUrl, url, price, feedback, owner);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", url='" + url + '\'' +
                ", price=" + price +
                ", feedback=" + feedback +
                ", owner=" + owner +
                '}';
    }
}
