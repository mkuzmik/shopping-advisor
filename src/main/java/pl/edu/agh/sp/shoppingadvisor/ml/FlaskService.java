package pl.edu.agh.sp.shoppingadvisor.ml;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.edu.agh.sp.shoppingadvisor.offer.EvaluatedOffer;
import pl.edu.agh.sp.shoppingadvisor.offer.Offer;
import pl.edu.agh.sp.shoppingadvisor.offer.OfferProcessor;
import pl.edu.agh.sp.shoppingadvisor.user.User;

import java.net.URL;
import java.util.Collection;

@Component
public class FlaskService {

    @Value("${flask.ml.origin}")
    private String flaskOrigin;

    @Autowired
    private OfferProcessor offerProcessor;

    public Collection<EvaluatedOffer> evaluate(Collection<Offer> learningOffers, Collection<EvaluatedOffer> offersToEvaluate, User owner) {
        RecommendationInput recommendationInput = offerProcessor.inputOf(learningOffers, offersToEvaluate);
        RecommendationOutput output = recommend(recommendationInput);
        return output.getEvaluatedOffers(owner);
    }

    public RecommendationOutput recommend(RecommendationInput recommendationInput) {
        RecommendationOutput recommendationOutput = new RecommendationOutput();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String response = RestAssured.given()
                    .contentType("application/json")
                    .body(objectMapper.writeValueAsString(recommendationInput))
                    .post(new URL(flaskOrigin)).asString();
            recommendationOutput = objectMapper.readValue(response, RecommendationOutput.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendationOutput;
    }
}
