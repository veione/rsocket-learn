package rsocket.sample.spring;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
@Controller
public class StringSplitController {

    @MessageMapping("stringSplit")
    public Flux<String> stringSplit(String input) {
        return Flux.fromStream(input.codePoints().mapToObj(c -> String.valueOf((char) c)));
    }
}
