package rsocket.sample.server;

import io.rsocket.core.Resume;
import io.rsocket.frame.decoder.PayloadDecoder;
import org.doodle.broker.BrokerFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.DefaultMetadataExtractor;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import reactor.util.retry.Retry;

import java.time.Duration;

@SpringBootConfiguration(proxyBeanMethods = false)
public class RSocketConfig {
    private final Logger log = LoggerFactory.getLogger(RSocketConfig.class);

    @Autowired
    public RSocketConfig(RSocketStrategies rSocketStrategies) {
        MetadataExtractor metadataExtractor = rSocketStrategies.metadataExtractor();
        if (metadataExtractor instanceof DefaultMetadataExtractor) {
            DefaultMetadataExtractor defaultMetadataExtractor = (DefaultMetadataExtractor) metadataExtractor;
            defaultMetadataExtractor.metadataToExtract(new MimeType("application", "x-protobuf"), BrokerFrame.class, "broker-frame");
        }
    }

    /**
     * Add resume ability for RSocketServer
     *
     * @return RSocketServerCustomizer
     */
    @Bean
    RSocketServerCustomizer resumeServerCustomizer() {
        return (rSocketServer) ->
                rSocketServer.resume(new Resume()
                        .streamTimeout(Duration.ofSeconds(60))
                        .sessionDuration(Duration.ofMinutes(5))
                        .retry(
                                Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(5))
                                        .doBeforeRetry(s -> log.warn("Client disconnected. Trying to resume connection..."))
                        )
                );
    }

}
