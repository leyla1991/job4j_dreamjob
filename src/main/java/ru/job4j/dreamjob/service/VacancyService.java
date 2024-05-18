package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.File;
import ru.job4j.dreamjob.model.Vacancy;
import java.util.Collection;
import java.util.Optional;

@ThreadSafe
public interface VacancyService {

    Vacancy save(Vacancy vacancy, FileDto image);

    void deleteById(int id);

    boolean update(Vacancy vacancy, FileDto image);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();
}
