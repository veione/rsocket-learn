package rsocket.sample.echo;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 14:54:00
 */
public class FlowControlSever {
    private static final Logger log = LoggerFactory.getLogger(FlowControlSever.class);

    public static void main(String[] args) {
        RSocketServer.create(
                        (setup, sendingSocket) -> Mono.just(new RSocket() {
                            @Override
                            public Flux<Payload> requestStream(Payload payload) {
                                return Flux.range(0, 10)
                                        .map(n -> DefaultPayload.create(
                                                String.format("%s_%04d", payload.getDataUtf8(), n)));
                            }
                        })).bind(TcpServerTransport.create(7878))
                .doOnSuccess(it -> log.info("++++++ server start success! ++++++"))
                .block()
                .onClose()
                .doOnSuccess(it -> log.info("++++++ server stopped! ++++++"))
                .block();
    }
}
