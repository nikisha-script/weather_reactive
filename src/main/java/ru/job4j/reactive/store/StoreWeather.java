package ru.job4j.reactive.store;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.reactive.weather.Weather;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class StoreWeather {

    private final Map<Integer, Weather> weathers;
    private static AtomicInteger id = new AtomicInteger();

    public StoreWeather() {
        this.weathers = new ConcurrentHashMap<>();
    }

    public void add(Weather weather) {
        weathers.put(id.incrementAndGet(), weather);
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> findCityWithHottestTemperature() {
        return Mono.justOrEmpty(weathers.values()
                .stream()
                .max(Comparator.comparing(Weather::getTemperature)));
    }

    public Flux<Weather> findALlCitiesGreatThen(int temp) {
        return Flux.fromIterable(weathers.values()
                .stream()
                .filter(e -> e.getTemperature() > temp)
                .collect(Collectors.toList()));
    }
}
