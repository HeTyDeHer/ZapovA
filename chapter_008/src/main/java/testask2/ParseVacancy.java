package testask2;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * Testask JDBC.
 * Class for parsing valid vacancy.
 */
public class ParseVacancy implements Callable<Vacancy> {
    /** Vacancy url. */
    private final String url;
    /** Vacancy header. */
    private final String header;
    /** Date and time of last database update. */
    private final LocalDateTime lastUpdate;
    /** Date and time of processing current element. */
    private final LocalDateTime processed;
    /** Logger. */
    private final Logger logger = Logger.getLogger(ParseVacancy.class.getName());

    /**
     * Constructor.
     * @param url Vacancy url.
     * @param header Vacancy header.
     * @param lastUpdate Date and time of last database update.
     */
    public ParseVacancy(String url, String header, LocalDateTime lastUpdate) {
        this.url = url;
        this.header = header;
        this.processed = LocalDateTime.now();
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return Vacancy.
     *         or null if vacancy is created before than last db update.
     */
    @Override
    public Vacancy call() {
        Document doc;
        String description = "";
        String time = "";
        Vacancy result = null;
        Element msg;
        try {
            doc = Jsoup.connect(url).get();
            msg = doc.selectFirst("table[class=msgTable]");
            description = msg.selectFirst("td[class=msgBody]:not(td[style])").ownText();
            time = msg.selectFirst("tr > td[class=msgFooter]").ownText();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        LocalDateTime addedToSite = parseTime(time);
        if (addedToSite.isAfter(lastUpdate)) {
            result = new Vacancy(this.header, this.url, description, addedToSite, this.processed);
        }
        return result;

    }

    /**
     * Parsing String to LocalDateTime.
     * @param time String, that contains time.
     * @return LocalDateTime.
     */
    private LocalDateTime parseTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy", Locale.forLanguageTag("ru"));
        time = time.replace("сегодня", LocalDate.now().format(formatter)).replace("вчера", LocalDate.now().minusDays(1)
                .format(formatter)).replace("й", "я").substring(0, 16).trim();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM yy, HH:mm", Locale.forLanguageTag("ru"));
        return LocalDateTime.parse(time, dtf);
    }
}
