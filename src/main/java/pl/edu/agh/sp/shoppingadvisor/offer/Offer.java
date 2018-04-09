package pl.edu.agh.sp.shoppingadvisor.offer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Offer {

    private String title;

    private String imgUrl;

    @Id
    private String url;

    private float price;

    /*JPA*/
    public Offer() {
    }

    public Offer(String title, String imgUrl, String url, float price) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.url = url;
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Float.compare(offer.price, price) == 0 &&
                Objects.equals(title, offer.title) &&
                Objects.equals(imgUrl, offer.imgUrl) &&
                Objects.equals(url, offer.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, imgUrl, url, price);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", url='" + url + '\'' +
                ", price=" + price +
                '}';
    }
}
