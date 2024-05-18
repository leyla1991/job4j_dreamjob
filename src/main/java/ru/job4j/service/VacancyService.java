package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.model.Vacancy;
import java.util.Collection;
import java.util.Optional;

@ThreadSafe
public interface VacancyService {

    Vacancy save(Vacancy vacancy);

    boolean deleteById(int id);

    boolean update(Vacancy vacancy);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();
}
