package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryCityRepository implements CityRepository {

    private final Map<Integer, City> cities = new ConcurrentHashMap<>() {
        {
            put(1, new City(1, "Moscow"));
            put(2, new City(2, "Kiev"));
            put(3, new City(3, "Lisabon"));
        }
    };

    @Override
    public Collection<City> findAll() {
        return cities.values();
    }
}
