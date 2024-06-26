package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
public interface CandidateService {

    Candidate save(Candidate candidate, FileDto image);

    void deleteById(int id);

    boolean update(Candidate candidate, FileDto image);

    Optional<Candidate> findById(int id);

    Collection<Candidate> findAll();
}
