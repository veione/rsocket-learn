package rsocket.sample.spring;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
@Controller
public class EchoController {

    @MessageMapping("echo")
    public Mono<String> echo(String input) {
        return Mono.just("ECHO >> " + input);
    }
}
