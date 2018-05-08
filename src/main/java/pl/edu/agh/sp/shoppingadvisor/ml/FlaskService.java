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

    public Hello getHello() {
        Hello hello = new Hello();
        try {
            String response = RestAssured.get(new URL(flaskOrigin)).asString();
            ObjectMapper objectMapper = new ObjectMapper();
            hello = objectMapper.readValue(response, Hello.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hello;
    }
}
