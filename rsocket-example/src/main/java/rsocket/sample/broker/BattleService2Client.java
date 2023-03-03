package rsocket.sample.broker;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 15:46:00
 */
public class BattleService2Client {
    private static final Logger log = LoggerFactory.getLogger(BattleService2Client.class);

    public static void main(String[] args) throws InterruptedException {
        RSocket client = RSocketConnector.create()
                .setupPayload(DefaultPayload.create(JSON.stringify(new Peer().setName("battle"))))
                .acceptor((setup, sendingSocket) -> Mono.just(new RSocket() {
                    @Override
                    public Mono<Payload> requestResponse(Payload payload) {
                        return Mono.just(DefaultPayload.create("Response from battle server :" + payload.getDataUtf8()));
                    }
                }))
                .connect(WebsocketClientTransport.create(8080))
                .block();


        /*client.requestResponse(DefaultPayload.create(JSON.stringify(new ForwardInfo().setTarget("chat"))))
                .doOnNext(payload -> log.info("Receive response message:{}", payload.getDataUtf8()))
                .block();*/

        Thread.sleep(Integer.MAX_VALUE);

        client.dispose();
        client.onClose()
                .doOnSuccess(it -> log.info("Client has stopped!"))
                .block();
    }
}
