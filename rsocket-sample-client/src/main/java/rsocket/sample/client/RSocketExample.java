package rsocket.sample.client;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.core.Resume;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月01日 17:31:00
 */
public class RSocketExample {
    private static final Logger logger = LoggerFactory.getLogger(RSocketExample.class);

    public static void main(String[] args) {
        RSocketServer.create(
                        SocketAcceptor.forRequestResponse(
                                p -> {
                                    String data = p.getDataUtf8();
                                    logger.info("Received request data {}", data);

                                    Payload responsePayload = DefaultPayload.create("Echo: " + data);
                                    p.release();

                                    return Mono.just(responsePayload);
                                }))
                .bind(TcpServerTransport.create("localhost", 7000))
                .delaySubscription(Duration.ofSeconds(5))
                .doOnNext(cc -> logger.info("Server started on the address : {}", cc.address()))
                .block();

        Mono<RSocket> source =
                RSocketConnector.create()
                        .resume(new Resume())
                        .reconnect(Retry.backoff(50, Duration.ofMillis(500)))
                        .connect(TcpClientTransport.create("localhost", 7000));

        RSocketClient.from(source)
                .requestResponse(Mono.just(DefaultPayload.create("Test Request")))
                .doOnSubscribe(s -> logger.info("Executing Request"))
                .doOnNext(
                        d -> {
                            logger.info("Received response data {}", d.getDataUtf8());
                            d.release();
                        })
                .repeat(10)
                .blockLast();
    }
}
