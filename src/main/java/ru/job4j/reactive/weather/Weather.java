package ru.job4j.reactive.weather;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Weather {

    @EqualsAndHashCode.Include
    @NonNull
    private int id;
    @NonNull
    private String city;
    @NonNull
    private int temperature;

}
