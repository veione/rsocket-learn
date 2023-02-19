package rsocket.sample.standalone;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
public class WebSocketRequestResponseExample {

    public static void main(String[] args) {
        SocketAcceptor acceptor = SocketAcceptor.forRequestResponse(payload ->
                Mono.just(DefaultPayload.create("ECHO >> " + payload.getDataUtf8()))
        );
        RSocketServer.create(acceptor)
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bind(WebsocketServerTransport.create(7080))
                .block();

        RSocket socket = RSocketConnector.connectWith(WebsocketClientTransport.create(7080))
                .block();

        socket.requestResponse(DefaultPayload.create("hello, world"))
                .map(Payload::getDataUtf8)
                .doOnNext(System.out::println)
                .doFinally(signalType -> socket.dispose())
                .block();
    }
}
