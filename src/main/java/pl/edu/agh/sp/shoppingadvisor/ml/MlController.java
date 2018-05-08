package pl.edu.agh.sp.shoppingadvisor.ml;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MlController {

    @Autowired
    private FlaskService flaskService;

    @RequestMapping(value = "/flask", method = RequestMethod.GET)
    public Hello foo(){
        return flaskService.getHello();
    }
}
