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
 * @author ${USER}
 * @version 1.0
 * @date ${DATE}
 */
public class FireAndForgetExample {
    public static void main(String[] args) {
        RSocket rSocket = new RSocket() {
            @Override
            public Mono<Void> fireAndForget(Payload payload) {
                System.out.println("Receive: " + payload.getDataUtf8());
                return Mono.empty();
            }
        };

        RSocketServer.create()
                //.create(SocketAcceptor.with(rSocket))
                .acceptor((setup, sendingSocket) -> Mono.just(rSocket))
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bind(TcpServerTransport.create(7878))
                .block()
                .onClose()
                .subscribe();

        RSocket socket =
                RSocketConnector.create()
                        .payloadDecoder(PayloadDecoder.ZERO_COPY)
                        .connect(TcpClientTransport.create(7878))
                        .cache()
                        .block();

        socket.fireAndForget(DefaultPayload.create("hello")).block();
        socket.fireAndForget(DefaultPayload.create("world")).block();

        socket.dispose();
    }
}