package userstoreauth.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. Реализация интерфейса [#2519].
 * Get countries/cities from planetolog.ru.
 */

public class ParseSiteForCities {
    private static final Logger logger = LoggerFactory.getLogger(ParseSiteForCities.class);
    private final List<City> countriesAndCities = new ArrayList<>();
    private String link = "http://planetolog.ru/city-world-alphabet.php?abc=A";

    public List<City> parsePlanetologDotRu() {
            for (int i = 0; i < 29; i++) {
                Document doc;
                try {
                    doc = Jsoup.connect(link).get();
                    Elements links = doc.select("div[class=regionbox]");
                    link = "http://planetolog.ru/" + links.first().select("a").eq(i + 7).first().attr("href");
                    Elements fullList = doc.select("td[class=CountryList]");
                    Elements cities = fullList.last().select("a");
                    Elements countries =  fullList.last().select("font");
                    int z = 0;
                    for (Element e : cities) {
                        countriesAndCities.add(new City(countries.eq(z++).first().ownText().substring(2), e.ownText()));
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            return countriesAndCities;
    }
}

class City {
    private String country;
    private String city;

    public City(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {

        return country;
    }
}
