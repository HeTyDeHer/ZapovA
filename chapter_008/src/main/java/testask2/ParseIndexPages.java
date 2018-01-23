package testask2;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Testask JDBC.
 * Class for parsing forum index pages.
 */

public class ParseIndexPages implements Callable<ArrayList<Vacancy>> {

    /** Forum URL. */
    private final String url;
    /** Date and time of last database update. */
    private final LocalDateTime lastUpdate;
    /** Executor service. */
    private final ExecutorService ex;
    /** page of forum to parse. */
    private int page = 1;
    /** Array of parsing results (Valid vacancies). */
    private final ArrayList<Vacancy> queue = new ArrayList<>();
    /** Logger. */
    private final Logger logger = Logger.getLogger(ParseIndexPages.class.getName());

    /**
     * Constructor.
     * @param url Forum url.
     * @param lastUpdate last database update.
     * @param ex Executor
     */
    public ParseIndexPages(String url, LocalDateTime lastUpdate, ExecutorService ex) {
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.ex = ex;
    }

    /**
     * @return Array of parsing results (Valid vacancies).
     * @throws Exception any
     */
    @Override
    public ArrayList<Vacancy> call() throws Exception {
        try {
            ArrayList<Future<Vacancy>> results = new ArrayList<>();
            while (!Thread.interrupted()) {
                Pattern pat = Pattern.compile("java(?!.{0,4}script)");
                Document doc;
                Elements offers = null;
                int attempts = 0;
                while (offers == null || offers.isEmpty() && attempts < 5) {
                    try {
                        attempts++;
                        doc = Jsoup.connect(url + page).get();
                        offers = doc.select("tr:has(td[class=postslisttopic]:not(:has(span[class=closedTopic])))");
                        if (offers == null || offers.isEmpty()) {
                            Thread.sleep(1000);
                        }
                    } catch (IOException | NullPointerException | InterruptedException e) {
                        logger.fatal(e.getMessage(), e);
                        break;
                    }
                }

                if (offers == null || offers.isEmpty()) {
                    logger.error("No data");        // todo спросить
                    return queue;
                }

                for (Element element : offers) {
                    if (this.checkIfElementDateIsBeforeUpdate(element)) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    Element offer = element.select("td[class=postslisttopic] > a[href]:not(.newTopic)").first();
                    Matcher m = pat.matcher(offer.ownText().toLowerCase());
                    if (m.find()) {
                        results.add(ex.submit(new ParseVacancy(offer.attr("href"), offer.ownText(), lastUpdate)));
                    }
                }
                page++;
            }

            for (Future<Vacancy> f : results) {
                if (f.get() != null) {
                    this.queue.add(f.get());
                }
            }
            return this.queue;
        } finally {
            ex.shutdown();
        }
    }

    /**
     * Check if time of current vacancy is before last database update.
     * @param element current Element (vacancy).
     * @return true - time is BEFORE last update, no further action needed,
     *         false - time is AFTER, need to check element.
     */
    private boolean checkIfElementDateIsBeforeUpdate(Element element) {
        boolean result = false;
        String dateTime = element.select("td[class=altCol]").get(1).ownText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy", Locale.forLanguageTag("ru"));
        dateTime = dateTime.replace("сегодня", LocalDate.now().format(formatter));
        dateTime = dateTime.replace("вчера", LocalDate.now().minusDays(1).format(formatter));
        dateTime = dateTime.replace("й", "я");
        formatter = DateTimeFormatter.ofPattern("d MMM yy, HH:mm", Locale.forLanguageTag("ru"));
        LocalDateTime ldt = LocalDateTime.parse(dateTime, formatter);
        if (ldt.isBefore(lastUpdate)) {
            result = true;
        }
        return result;
    }
}
