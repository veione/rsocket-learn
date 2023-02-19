package rsocket.sample.standalone;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
public class RequestStreamExample {

    public static void main(String[] args) throws InterruptedException {
        RSocketServer.create(SocketAcceptor.forRequestStream(
                payload -> Flux.interval(Duration.ofMillis(100))
                        .map(aLong -> DefaultPayload.create("Interval: " + aLong))
                ))
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bind(TcpServerTransport.create(7878))
                .block();

        RSocket socket = RSocketConnector.create()
                .setupPayload(DefaultPayload.create("test", "test"))
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .connect(TcpClientTransport.create(7878))
                .block();

        Payload payload = DefaultPayload.create("hello,world");
        socket.requestStream(payload)
                .map(Payload::getDataUtf8)
                .doOnNext(System.out::println)
                .take(10)
                .then()
                .doFinally(signalType -> socket.dispose())
                .block();

        socket.onClose().block();
    }
}
