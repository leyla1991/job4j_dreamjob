package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.File;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
public interface VacancyRepository {

    Vacancy save(Vacancy vacancy);

    void deleteById(int id);

    boolean update(Vacancy vacancy);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();

}