package testask2;

import java.time.LocalDateTime;

/**
 * Testask JDBC.
 * Contains vacancy information.
 */
public class Vacancy {
    private final String header;
    private final String url;
    private final String description;
    private final LocalDateTime added;
    private final LocalDateTime processed;

    public Vacancy(String header, String url, String description, LocalDateTime added, LocalDateTime processed) {
        this.header = header;
        this.url = url;
        this.description = description;
        this.added = added;
        this.processed = processed;
    }

    public String getHeader() {
        return header;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public LocalDateTime getProcessed() {
        return processed;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "header='" + header + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", added=" + added +
                ", processed=" + processed +
                '}';
    }
}
