package rsocket.sample;

import io.rsocket.frame.decoder.PayloadDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import rsocket.sample.spring.UploadConstants;
import rsocket.sample.spring.UploadStatus;

import java.time.Duration;
import java.util.UUID;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 10:39:00
 */
@SpringBootApplication
public class RSocketClientApplication implements CommandLineRunner {

    @Bean
    public RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
        return builder
                .setupRoute("connect")
                .setupData(UUID.randomUUID())
                .rsocketConnector(connector -> {
                    connector.reconnect(Retry.backoff(Integer.MAX_VALUE, Duration.ofMillis(500)))
                            .payloadDecoder(PayloadDecoder.ZERO_COPY);
                })
                .tcp("127.0.0.1", 7100);
    }

    @Autowired
    RSocketRequester rSocketRequester;

    @Value("classpath:/images/pic.jpeg")
    private Resource resource;

    public static void main(String[] args) {
        SpringApplication.run(RSocketClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String fileName = UUID.randomUUID().toString();
        String fileExt = this.resource.getFilename().substring(this.resource.getFilename().lastIndexOf(".") + 1);
        Flux<DataBuffer> resourceFlux = DataBufferUtils.read(this.resource, new DefaultDataBufferFactory(), 1024)
                .doOnNext(s -> System.out.println("文件上传：" + s));

        Flux<UploadStatus> uploadStatusFlux = this.rSocketRequester.route("message.upload")
                .metadata(metadataSpec -> {
                    System.out.println("【上传测试】：文件名称：" + fileName + "." + fileExt);
                    metadataSpec.metadata(fileName, MimeType.valueOf(UploadConstants.MIME_FILE_NAME));
                    metadataSpec.metadata(fileExt, MimeType.valueOf(UploadConstants.MIME_FILE_EXTENSION));
                })
                .data(resourceFlux)
                .retrieveFlux(UploadStatus.class)
                .doOnNext(o -> System.out.println("上传进度：" + o));

        uploadStatusFlux.blockLast();
    }
}

