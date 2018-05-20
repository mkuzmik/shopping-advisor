package pl.edu.agh.sp.shoppingadvisor.recommendation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation/run")
public class RecommendationController {

  @GetMapping
  public void runRunRecommendation() {

  }
}
