package pl.edu.agh.sp.shoppingadvisor.ml;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.sp.shoppingadvisor.scraper.AllegroScraper;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/learning")
public class MlController {

    @Autowired
    private FlaskService flaskService;

    @Autowired
    private AllegroScraper allegroScraper;

    @PostMapping
    public RecommendationOutput learn(@RequestBody RecommendationInput recommendationInput){
        recommendationInput.setTraining_offers(recommendationInput.getTraining_offers().stream().peek(offer -> {
            String description = Try.of(() -> String.join(" ", allegroScraper.scrapDescription(offer.getUrl()))).getOrElse("");
            offer.setDescription(description);
        }).collect(Collectors.toList()));
        recommendationInput.setTest_offers(recommendationInput.getTest_offers().stream().peek(offer -> {
            String description = Try.of(() -> String.join(" ", allegroScraper.scrapDescription(offer.getUrl()))).getOrElse("");
            offer.setDescription(description);
        }).collect(Collectors.toList()));
        return flaskService.recommend(recommendationInput);
    }
}
