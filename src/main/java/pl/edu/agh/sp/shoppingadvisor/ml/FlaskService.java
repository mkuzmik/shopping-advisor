package pl.edu.agh.sp.shoppingadvisor.ml;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class FlaskService {

    @Value("${flask.ml.origin}")
    private String flaskOrigin;

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
