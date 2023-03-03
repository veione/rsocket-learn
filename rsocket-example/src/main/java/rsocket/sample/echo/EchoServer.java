package rsocket.sample.echo;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 14:39:00
 */
public class EchoServer {
    private static final Logger log = LoggerFactory.getLogger(EchoServer.class);

    public static void main(String[] args) {
        RSocketServer.create()
                .acceptor((setup, sendingSocket) -> Mono.just(
                        new RSocket() {
                            @Override
                            public Mono<Payload> requestResponse(Payload payload) {
                                log.info("rcv: {}", payload.getDataUtf8());
                                return Mono.just(payload);
                            }
                        })).bind(TcpServerTransport.create(7878))
                .doOnSuccess(it -> log.info("++++++ server start success! ++++++"))
                .block()
                .onClose()
                .doOnSuccess(it -> log.info("++++++ server stopped! ++++++"))
                .block();
    }
}
