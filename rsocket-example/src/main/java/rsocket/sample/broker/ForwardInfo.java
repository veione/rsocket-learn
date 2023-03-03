package rsocket.sample.broker;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 14:33:00
 */
public class ForwardInfo {
    private String source;
    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public ForwardInfo setTarget(String target) {
        this.target = target;
        return this;
    }

    @Override
    public String toString() {
        return "ForwardInfo{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}