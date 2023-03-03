package rsocket.sample.broker;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 15:46:00
 */
public class ChatService1Client {
    private static final Logger log = LoggerFactory.getLogger(ChatService1Client.class);

    public static void main(String[] args) {
        RSocket client = RSocketConnector.create()
                .setupPayload(DefaultPayload.create(JSON.stringify(new Peer().setName("chat"))))
                .connect(WebsocketClientTransport.create(8080))
                .block();


        Payload request = DefaultPayload.create("chat-to-battle", JSON.stringify(new ForwardInfo().setTarget("battle")));
        client.requestResponse(request)
                .doOnNext(payload -> log.info("Receive response message:{}", payload.getDataUtf8()))
                .block();

        client.dispose();
        client.onClose()
                .doOnSuccess(it -> log.info("Client has stopped!"))
                .block();
    }
}
