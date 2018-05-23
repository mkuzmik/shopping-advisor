package pl.edu.agh.sp.shoppingadvisor.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation/run")
public class RecommendationController {

  @Autowired
  private RecommendationService recommendationService;

  @GetMapping
  public void runRunRecommendation() {
    recommendationService.recommend();
  }
}
