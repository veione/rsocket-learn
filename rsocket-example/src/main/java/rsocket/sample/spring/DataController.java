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
public class DataController {

    @MessageMapping("collect")
    public Mono<Void> collect(String data) {
        System.out.println("Received = " + data);
        return Mono.empty();
    }
}
