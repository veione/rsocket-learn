package rsocket.sample.standalone;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
public class RequestResponseExample {

    public static void main(String[] args) {
        RSocketServer.create()
                .acceptor((setup, sendingSocket) -> Mono.just(new RSocket() {
                    @Override
                    public Mono<Payload> requestResponse(Payload payload) {
                        return Mono.just(DefaultPayload.create("ECHO >>" + payload.getDataUtf8()));
                    }
                }))
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bind(TcpServerTransport.create(7878))
                .block()
                .onClose();

        RSocket socket = RSocketConnector.create()
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .connect(TcpClientTransport.create(7878))
                .block();

        for (int i = 0; i < 3; i++) {
            socket.requestResponse(DefaultPayload.create("hello, world"))
                    .map(Payload::getDataUtf8)
                    .onErrorReturn("error")
                    .doOnNext(System.out::println)
                    .block();
        }

        socket.dispose();
    }
}
