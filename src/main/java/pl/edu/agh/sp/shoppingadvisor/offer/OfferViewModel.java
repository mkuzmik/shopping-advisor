package pl.edu.agh.sp.shoppingadvisor.offer;

public class OfferViewModel {

    private String title;

    private String imgUrl;

    private String url;

    private float price;

    public OfferViewModel(String title, String imgUrl, String url, float price) {
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
}
