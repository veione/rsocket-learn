package rsocket.sample.model;

import java.util.List;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
public class HelloRequest {
    private List<String> names;

    public HelloRequest(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
