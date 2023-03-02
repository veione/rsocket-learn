package rsocket.sample.client;

import io.rsocket.core.Resume;
import io.rsocket.frame.decoder.PayloadDecoder;
import org.doodle.broker.BrokerAddress;
import org.doodle.broker.BrokerFrame;
import org.doodle.broker.BrokerRouteId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.MimeType;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.UUID;

@EnableScheduling
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Bean
    public RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
        return strategies -> strategies
                .decoder(new ProtobufDecoder())
                .encoder(new ProtobufEncoder());
    }

    @Bean
    public RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
        return builder.setupRoute("connect")
                .rsocketConnector(connector -> {
                    connector.resume(new Resume())
                            .reconnect(Retry.backoff(Integer.MAX_VALUE, Duration.ofMillis(500)))
                            .payloadDecoder(PayloadDecoder.ZERO_COPY);
                })
                .setupData(UUID.randomUUID())
                .tcp("127.0.0.1", 8001);
    }

    @Autowired
    RSocketRequester rSocketRequester;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    public static final MimeType ROUTING_FRAME_TYPE = new MimeType("application", "x-protobuf");

    @Scheduled(fixedDelay = 1000)
    public void reqSample() {
        BrokerFrame frame = BrokerFrame.newBuilder()
                .setAddress(BrokerAddress.newBuilder().setRouteId(BrokerRouteId.newBuilder().setLsb(1).setMsb(2).build())
                        .setFlag(1001)
                        .build())
                .build();
        rSocketRequester.route("sample")
                .metadata(frame, ROUTING_FRAME_TYPE)
                .send()
                .subscribe(System.out::println);
    }

    @Override
    public void run(String... args) throws Exception {
        // rSocketRequester.route("sample").send();
    }
}
