package rsocket.sample.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;

@SpringBootApplication
public class ServerApplication {

    @Bean
    public RSocketStrategiesCustomizer customizer() {
        return strategies -> strategies.encoder(new ProtobufEncoder())
                .decoder(new ProtobufDecoder());
    }


    public static void main(String[] args){
        SpringApplication.run(ServerApplication.class, args);
    }
}
