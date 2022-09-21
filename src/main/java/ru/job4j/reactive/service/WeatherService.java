package ru.job4j.reactive.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.reactive.store.StoreWeather;
import ru.job4j.reactive.weather.Weather;


@Service
public class WeatherService {

    private final StoreWeather storeWeather;

    public WeatherService(StoreWeather storeWeather) {
        this.storeWeather = storeWeather;
    }

    public void add(Weather weather) {
        storeWeather.add(weather);
    }

    public Mono<Weather> findById(Integer id) {
        return storeWeather.findById(id);
    }

    public Flux<Weather> all() {
        return storeWeather.all();
    }

    public Mono<Weather> findCityWithHottestTemperature() {
        return storeWeather.findCityWithHottestTemperature();
    }

    public Flux<Weather> findALlCitiesGreatThen(int temp) {
        return storeWeather.findALlCitiesGreatThen(temp);
    }
}
