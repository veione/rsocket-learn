package rsocket.sample.echo;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 15:12:00
 */
public class BiDirectionalServer {
    private static final Logger log = LoggerFactory.getLogger(BiDirectionalServer.class);

    public static void main(String[] args) {
        RSocketServer.create((setup, sendingSocket) -> {
                //服务器请求客户端
            sendingSocket
                    .requestResponse(DefaultPayload.create("Hello Golang! I'm JAVA"))
                    .subscribeOn(Schedulers.boundedElastic())
                    .doOnNext(payload -> log.info("got response: {}", payload.getDataUtf8()))
                    .subscribe();

                    return Mono.just(new RSocket() {
                        @Override
                        public Mono<Payload> requestResponse(Payload payload) {
                            return Mono.just(DefaultPayload.create("ECHO:" + payload.getDataUtf8()));
                        }
                    });
                })
                .bind(TcpServerTransport.create(7878))
                .doOnSuccess(it -> log.info("++++++ server started ++++++"))
                .block()
                .onClose()
                .doOnSuccess(it -> log.info("++++++ server stopped ++++++"))
                .block();
    }
}
