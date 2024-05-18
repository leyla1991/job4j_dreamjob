package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateRepository;
import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleCandidateService implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final FileService fileService;

    public SimpleCandidateService(CandidateRepository candidateRepository, FileService fileService) {
        this.candidateRepository = candidateRepository;
        this.fileService = fileService;
    }

    @Override
    public Candidate save(Candidate candidate, FileDto image) {
        saveNewFile(candidate, image);
        return candidateRepository.save(candidate);
    }

    private void saveNewFile(Candidate candidate, FileDto image) {
        var file = fileService.save(image);
        candidate.setFileId(file.getId());
    }

    @Override
    public void deleteById(int id) {
        var fileOptional = findById(id);
        if (fileOptional.isPresent()) {
            candidateRepository.deleteById(id);
            fileService.deleteById(fileOptional.get().getFileId());
        }
    }

    @Override
    public boolean update(Candidate candidate, FileDto image) {
        var isNewFile = image.getContent().length == 0;
        if (isNewFile) {
            return candidateRepository.update(candidate);
        }
        var oldField = candidate.getFileId();
        saveNewFile(candidate, image);
        var isUpdate = candidateRepository.update(candidate);
        fileService.getFileById(oldField);
        return isUpdate;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return candidateRepository.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidateRepository.findAll();
    }
}