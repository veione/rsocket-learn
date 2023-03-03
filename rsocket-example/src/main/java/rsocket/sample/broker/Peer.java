package rsocket.sample.broker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.rsocket.RSocket;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 14:17:00
 */
public class Peer {
    private String name;
    @JsonIgnore
    private RSocket socket;

    public String getName() {
        return name;
    }

    public Peer setName(String name) {
        this.name = name;
        return this;
    }

    public RSocket getSocket() {
        return socket;
    }

    public void setSocket(RSocket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "Peer{" +
                "name='" + name + '\'' +
                ", socket=" + socket +
                '}';
    }
}
