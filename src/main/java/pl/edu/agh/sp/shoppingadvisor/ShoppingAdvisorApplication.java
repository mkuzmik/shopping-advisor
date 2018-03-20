package pl.edu.agh.sp.shoppingadvisor;

import api.allegro.wsdl.DoGetItemsListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.agh.sp.shoppingadvisor.allegro.AllegroClient;

@SpringBootApplication
public class ShoppingAdvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingAdvisorApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AllegroClient allegroClient) {
		return args -> {
			DoGetItemsListResponse response = allegroClient.getItems("whatever");

			System.out.println("done");
		};
	}
}
