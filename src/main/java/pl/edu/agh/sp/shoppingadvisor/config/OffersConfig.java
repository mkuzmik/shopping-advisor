package pl.edu.agh.sp.shoppingadvisor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.sp.shoppingadvisor.allegro.AllegroClient;
import pl.edu.agh.sp.shoppingadvisor.allegro.AllegroOfferFactory;
import pl.edu.agh.sp.shoppingadvisor.allegro.AllegroOfferSearcher;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferAccumulator;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferSearcher;

@Configuration
public class OffersConfig {

    @Bean
    public AllegroOfferSearcher allegroOfferSearcher(AllegroClient allegroClient) {
        return new AllegroOfferSearcher(allegroClient);
    }

    @Bean
    public OfferSearcher offerSearcher(AllegroOfferSearcher allegroOfferSearcher) {
        OfferAccumulator offerAccumulator = new OfferAccumulator();
        offerAccumulator.add(allegroOfferSearcher);
        return offerAccumulator;
    }
}
