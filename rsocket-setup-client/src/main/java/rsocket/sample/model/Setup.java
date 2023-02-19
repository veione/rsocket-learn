package rsocket.sample.model;

/**
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
public class Setup {
    private String language;
    private String country;

    public Setup(String language, String country) {
        this.language = language;
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
