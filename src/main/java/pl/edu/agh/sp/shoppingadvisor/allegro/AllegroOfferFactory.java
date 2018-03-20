package pl.edu.agh.sp.shoppingadvisor.allegro;

import api.allegro.wsdl.ItemsListType;
import api.allegro.wsdl.PhotoInfoType;
import api.allegro.wsdl.PriceInfoType;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferViewModel;

public class AllegroOfferFactory {

    public static OfferViewModel createOfferFrom(ItemsListType itemsListType) {

        String photoUrl = itemsListType
                .getPhotosInfo()
                .getItem().stream()
                .findFirst()
                .orElse(new PhotoInfoType())
                .getPhotoUrl();

        float price = itemsListType.getPriceInfo().getItem().stream()
                .filter(priceInfoType -> priceInfoType.getPriceType().equals("buyNow"))
                .findFirst()
                .orElse(new PriceInfoType())
                .getPriceValue();

        String url = "https://allegro.pl/show_item.php?item=" + itemsListType.getItemId();

        return new OfferViewModel(itemsListType.getItemTitle(), photoUrl, url, price);
    }
}
