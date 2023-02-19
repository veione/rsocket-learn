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
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
public class RequestChannelExample {

    public static void main(String[] args) {
    SocketAcceptor echoAcceptor = SocketAcceptor.forRequestChannel(
            payloads -> Flux.from(payloads)
                    .map(Payload::getDataUtf8)
                    .map(s -> "ECHO：" + s)
                    .map(DefaultPayload::create));

        RSocketServer.create(echoAcceptor)
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bind(TcpServerTransport.create(7878))
                .block();

        RSocket socket = RSocketConnector.create()
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .connect(TcpClientTransport.create(7878))
                .doOnNext(System.out::println)
                .block();

        socket.requestChannel(
                        Flux.interval(Duration.ofMillis(1000))
                                .map(i -> DefaultPayload.create("Hello")))
                .map(Payload::getDataUtf8)
                .doOnNext(System.out::println)
                .take(10)
                .doFinally(signalType -> socket.dispose())
                .then()
                .block();
    }
}
