package ru.job4j.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.job4j.reactive.service.WeatherService;
import ru.job4j.reactive.weather.HtmlParser;
import ru.job4j.reactive.weather.Weather;

import java.time.Duration;

@RestController
@RequestMapping("/weather")
public class WeatherControl {

    private final WeatherService service;
    private final HtmlParser parser;

    public WeatherControl(WeatherService service, HtmlParser parser) {
        this.service = service;
        this.parser = parser;
    }

    @PostMapping()
    public ResponseEntity<Void> addWeather(@RequestParam() String city) {
        Weather weather = parser.parseWeather(city);
        service.add(weather);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> all() {
        Flux<Weather> data = service.all();
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }

    @GetMapping(value = "/get/{id}")
    public Mono<Weather> get(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping(value = "/hottest")
    public Mono<Weather> hottest() {
        return service.findCityWithHottestTemperature();
    }

    @GetMapping(value = "/cityGreatThen/{temp}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> findALlCitiesGreatThen(@PathVariable Integer temp) {
        Flux<Weather> data = service.findALlCitiesGreatThen(temp);
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }


}
