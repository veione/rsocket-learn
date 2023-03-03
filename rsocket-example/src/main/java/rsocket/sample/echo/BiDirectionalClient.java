package rsocket.sample.echo;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 15:19:00
 */
public class BiDirectionalClient {
    private static final Logger log = LoggerFactory.getLogger(BiDirectionalClient.class);

    public static void main(String[] args) {
        RSocket client = RSocketConnector.create()
                .setupPayload(DefaultPayload.create("CLIENT"))
                .acceptor((setup, sendingSocket) -> Mono.just(new RSocket() {
                    @Override
                    public Mono<Payload> requestResponse(Payload payload) {
                        log.info("Client receive response message:{}", payload.getDataUtf8());
                        return Mono.just(payload);
                    }
                }))
                .connect(TcpClientTransport.create(7878))
                .doOnSuccess(it -> log.info("Client connect to server success!"))
                .block();

        client.requestResponse(DefaultPayload.create("Hello,I'm GOLANG"))
                .doOnNext(payload -> log.info("Client receive message: {}", payload.getDataUtf8()))
                .block();

        client.dispose();
        client.onClose()
                .doOnSuccess(it -> log.info("Client has stopped!"))
                .block();
    }
}
