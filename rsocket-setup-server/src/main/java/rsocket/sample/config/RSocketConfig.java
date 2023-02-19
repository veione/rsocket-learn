package rsocket.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
@Configuration
public class RSocketConfig {

    @Bean
    public RSocketMessageHandler rsocketMessageHandler(RSocketStrategies rSocketStrategies) {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRSocketStrategies(rSocketStrategies);
        return handler;
    }

    @Bean
    public RSocketStrategies rsocketStrategies() {
        return RSocketStrategies.builder()
                .decoders(decoders -> {
                    decoders.add(new Jackson2JsonDecoder());
                    decoders.add(new Jackson2CborDecoder());
                })
                .build();
    }
}
