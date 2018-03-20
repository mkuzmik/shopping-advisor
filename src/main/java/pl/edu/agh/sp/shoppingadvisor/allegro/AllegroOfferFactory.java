package pl.edu.agh.sp.shoppingadvisor.allegro;

import api.allegro.wsdl.ItemsListType;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferViewModel;

public class AllegroOfferFactory {

    public static OfferViewModel createOfferFrom(ItemsListType itemsListType) {
        return new OfferViewModel(itemsListType.getItemTitle(),
                itemsListType.getPhotosInfo().getItem().stream().findFirst().get().getPhotoUrl(),
                "https://allegro.pl/show_item.php?item=" + itemsListType.getItemId(),
                itemsListType.getPriceInfo().getItem().stream()
                        .filter(priceInfoType -> priceInfoType.getPriceType().equals("buyNow"))
                        .findFirst().get().getPriceValue());
    }
}
