package rsocket.sample.echo;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 14:39:00
 */
public class EchoClient {
    private static final Logger log = LoggerFactory.getLogger(EchoClient.class);

    public static void main(String[] args) {
        RSocket client =
                RSocketConnector.create()
                        .payloadDecoder(PayloadDecoder.ZERO_COPY)
                        .connect(TcpClientTransport.create(7878))
                        .block();

        Payload response = client.requestResponse(DefaultPayload.create("HelloWorld!")).block();
        log.info("got response: {}", response.getDataUtf8());
        client.dispose();
        client.onClose().block();
    }
}
