package rsocket.sample.server;

import io.rsocket.core.RSocketClient;
import org.doodle.broker.BrokerAddress;
import org.doodle.broker.BrokerFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsocket.sample.model.Notification;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;


@Controller
public class ServerController {
    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);
    private final List<RSocketRequester> CLIENTS = new CopyOnWriteArrayList<>();
    public static final MimeType ROUTING_FRAME_TYPE = new MimeType("application", "x-protobuf");

    @ConnectMapping("connect")
    public Mono<Void> handleSetup(RSocketRequester requester, UUID id) {
        logger.info("RSocket client connect:{}", id);

        CLIENTS.add(requester);

        requester.rsocket()
                .onClose()
                .doFinally(consumer -> {
                    CLIENTS.remove(consumer);
                    logger.info("Client {} DISCONNECTED", consumer);
                })
                .subscribe();
        return Mono.empty();
    }

    @MessageMapping("sample")
    public void sample(@Header(name = "broker-frame") BrokerFrame frame) {
        System.out.println("server receive message:" + frame);
        CLIENTS.forEach(client-> {
            client.route("hello").metadata(frame, ROUTING_FRAME_TYPE).send().subscribe();
        });
    }

    @MessageMapping("my-request-response")
    public Notification requestResponse(Notification notification) {
        return new Notification(notification.getDestination(), notification.getSource(), "In response to: " + notification.getText());
    }

    @MessageMapping("my-fire-and-forget")
    public void fireAndForget(Notification notification) {
        logger.info("Received notification: " + notification);
    }

    @MessageMapping("request-stream")
    public Flux<Notification> requestStream(Notification notification) {
        return Flux.
                interval(Duration.ofSeconds(3))
                .map(i -> new Notification(notification.getDestination(), notification.getSource(), "In response to: " + notification.getText()));
    }

    @MessageMapping("my-channel")
    public Flux<Long> channel(Flux<Notification> notifications) {
        final AtomicLong notificationCount = new AtomicLong(0);
        return notifications
                .doOnNext(notification -> {
                    notificationCount.incrementAndGet();
                })
                .switchMap(notification ->
                        Flux.interval(Duration.ofSeconds(1)).map(new Object() {
                            private Function<Long, Long> numberOfMessages(AtomicLong notificationCount) {
                                long count = notificationCount.get();
                                logger.info("Return flux with count: " + count);
                                return i -> count;
                            }
                        }.numberOfMessages(notificationCount))).log();
    }
}
