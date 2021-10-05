package edu.fontys.horecarobot.adminappbackend;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

/***
 * Example controller for the example api wrapper in our frontend
 * */

@RestController
@CrossOrigin(origins = "http://localhost:4000") // For Dev this is needed because otherwise the browser will block all request to the api
public class ApiTestController {

    @GetMapping("random/int")
    public int getRandomNumber()
    {
        Random rand = new Random();

        return  rand.nextInt(100);
    }

    @PostMapping("edit/form")
    public FormExampel postForm(@ModelAttribute FormExampel form)
    {
        Random rand = new Random();

        form.data = "Edited By The API \uD83D\uDE01" + rand.nextInt();
        return form;
    }

}

class FormExampel
{
    public String data = null;
}
