package rsocket.sample.echo;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 15:00:00
 */
public class FlowControlClient {
    private static final Logger log = LoggerFactory.getLogger(FlowControlClient.class);

    public static void main(String[] args) throws InterruptedException {
        RSocket client = RSocketConnector.create()
                .connect(TcpClientTransport.create(7878))
                .doOnSuccess(it -> log.info("Connect to server success"))
                .block();

        client.requestStream(DefaultPayload.create("HelloWorld!"))
                .subscribe(payload -> log.info("Client receive message:{}", payload.getDataUtf8()));

        Thread.sleep(5000);
        client.dispose();
        client.onClose()
                .doOnSuccess(it -> log.info("Client stopped!"))
                .block();
    }
}
