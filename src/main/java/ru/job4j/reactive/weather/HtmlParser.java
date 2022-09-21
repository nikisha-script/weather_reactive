package ru.job4j.reactive.weather;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class HtmlParser {

    private static final String SOURCE_LINK = "https://www.meteoservice.ru/weather/overview/";
    private AtomicInteger id = new AtomicInteger();

    public Weather parseWeather(String city) {
        Weather result = new Weather();
        AtomicInteger temperature = new AtomicInteger();
        try {
            Connection connection = Jsoup.connect(String.format("%s%s", SOURCE_LINK, city));
            Document document = connection.get();
            Elements trElements = document.getElementsByAttributeValue("class", "small-6 columns text-center");
            trElements.forEach(trElement -> {
                Elements elementsByClass = trElement.getElementsByClass("temperature margin-bottom-0");
                Element element = elementsByClass.get(0);
                String temp = element.getElementsByClass("value").text();
                temperature.set(Integer.parseInt(temp.replaceAll("[^A-Za-zА-Яа-я0-9]", "")));
            });
            result.setId(id.incrementAndGet());
            result.setCity(city);
            result.setTemperature(temperature.get());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
