package pl.edu.agh.sp.shoppingadvisor;

import api.allegro.wsdl.DoGetItemsListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.agh.sp.shoppingadvisor.allegro.AllegroClient;
import pl.edu.agh.sp.shoppingadvisor.offer.Offer;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferRepository;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferSearcher;
import pl.edu.agh.sp.shoppingadvisor.user.User;
import pl.edu.agh.sp.shoppingadvisor.user.UserRepository;

import java.util.List;

@SpringBootApplication
public class ShoppingAdvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingAdvisorApplication.class, args);
	}

	@Bean
	CommandLineRunner init(OfferSearcher offerSearcher, OfferRepository offerRepository, UserRepository userRepository) {
		return args -> {
		    User user = new User("user@mail.com");
		    userRepository.save(user);

		    offerSearcher.searchFor("whatever").stream()
                    .peek(offer -> offer.setOwner(user))
                    .forEach(offerRepository::save);
        };
	}
}
