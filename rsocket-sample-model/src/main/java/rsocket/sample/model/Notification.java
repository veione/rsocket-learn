package rsocket.sample.model;

/**
 * @author veione
 * @version 1.0
 * @date 2022/9/25
 */
public class Notification {
    private String source;
    private String destination;
    private String text;

    public Notification() {
        super();
    }

    public Notification(String source, String destination, String text) {
        this.source = source;
        this.destination = destination;
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
