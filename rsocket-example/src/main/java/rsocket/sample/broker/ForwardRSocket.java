package rsocket.sample.broker;

import io.rsocket.RSocket;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 14:22:00
 */
public class ForwardRSocket implements RSocket {
    private final Peer peer;

    public ForwardRSocket(Peer peer) {
        this.peer = peer;
    }

    public Peer getPeer() {
        return peer;
    }
}
