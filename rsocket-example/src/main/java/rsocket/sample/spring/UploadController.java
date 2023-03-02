package rsocket.sample.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 10:23:00
 */
@Controller
public class UploadController {
    private final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Value("${output.file.path.upload}")
    private Path outputPath;

    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester, UUID id) {
        log.info("RSocket client connect:{}", id);
        requester.rsocket()
                .onClose()
                .doFinally(consumer -> {
                    log.info("Client {} DISCONNECTED", consumer);
                })
                .subscribe();
        return Mono.empty();
    }

    @MessageMapping("message.upload")
    public Flux<UploadStatus> upload(
            @Headers Map<String, Object> metadata,
            @Payload Flux<DataBuffer> content
    ) throws Exception {
        log.info("[上传路径] outputPath={}", this.outputPath);
        Object fileName = metadata.get(UploadConstants.FILE_NAME);
        Object fileExt = metadata.get(UploadConstants.MIME_FILE_EXTENSION);
        Path path = Paths.get(fileName + "." + fileExt);
        log.info("[文件上传]filename={},fileExt={},path={}", fileName, fileExt, path);
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(outputPath.resolve(path), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        return Flux.concat(DataBufferUtils.write(content, channel)
                        .map(s -> UploadStatus.CHUNKED_COMPLETE), Mono.just(UploadStatus.COMPLETED))
                .doOnComplete(() -> {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        log.error("close channel occur exception", e);
                    }
                })
                .onErrorReturn(UploadStatus.FAILED);
    }
}
