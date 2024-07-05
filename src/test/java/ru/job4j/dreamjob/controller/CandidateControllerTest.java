package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateControllerTest {

   private CandidateService candidateService;

   private CityService cityService;

   private CandidateController candidateController;

   private MultipartFile testFile;

   @BeforeEach
   public void init() {
       candidateService = mock(CandidateService.class);
       cityService = mock(CityService.class);
       candidateController = new CandidateController(candidateService, cityService);
       testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
   }

  @Test
  public void whenRequestCandidateListPageThenGetPageWithCandidates() {
      var candidate1 = new Candidate(1, "Name1", "bla-bla", now(), 1, 1);
      var candidate2 = new Candidate(1, "Name2", "bla-bla2", now(), 2, 1);
      var expectedCandidates = List.of(candidate1, candidate2);
      when(candidateService.findAll()).thenReturn(expectedCandidates);

      var model = new ConcurrentModel();
      var view = candidateController.getAll(model);
      var actualCandidates = model.getAttribute("candidates");

      assertThat(view).isEqualTo("candidates/list");
      assertThat(actualCandidates).isEqualTo(expectedCandidates);
  }

  @Test
    public void whenRequestCandidateCreationPageThenGetPageWithCities() {
      var city1 = new City(1, "Москва");
      var city2 = new City(2, "Санкт-Петербург");
      var expectedCities = List.of(city1, city2);
      when(cityService.findAll()).thenReturn(expectedCities);

      var model = new ConcurrentModel();
      var view = candidateController.getCreationPage(model);
      var actualCandidates = model.getAttribute("cities");

      assertThat(view).isEqualTo("candidates/create");
      assertThat(actualCandidates).isEqualTo(expectedCities);
  }

  @Test
    public void whenPostCandidateWithFileThenSameDataAndRedirectToCandidatesPage() throws Exception {
      var candidate1 = new Candidate(1, "Name1", "bla-bla", now(), 1, 1);
      var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
      var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
      var fileArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
      when(candidateService.save(candidateArgumentCaptor.capture(), fileArgumentCaptor.capture())).thenReturn(candidate1);

      var model = new ConcurrentModel();
      var view =  candidateController.create(candidate1, testFile, model);
      var candidateActual = candidateArgumentCaptor.getValue();
      var actualFileDto = fileArgumentCaptor.getValue();

      assertThat(view).isEqualTo("redirect:/vacancies");
      assertThat(candidateActual).isEqualTo(candidate1);
      assertThat(fileDto).usingRecursiveComparison().isEqualTo(actualFileDto);
  }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Резюме с указанным идентификатором не найдено");
        when(candidateService.save(any(), any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = candidateController.create(new Candidate(), testFile, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
}
