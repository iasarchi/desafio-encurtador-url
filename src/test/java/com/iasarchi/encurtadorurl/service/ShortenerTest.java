package com.iasarchi.encurtadorurl.service;

import com.iasarchi.encurtadorurl.entity.Shortener;
import com.iasarchi.encurtadorurl.exception.AlreadyExistsException;
import com.iasarchi.encurtadorurl.exception.InvalidURLException;
import com.iasarchi.encurtadorurl.exception.UrlNotFound;
import com.iasarchi.encurtadorurl.repository.ShortenerRepository;
import com.iasarchi.encurtadorurl.service.ShortenerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenerTest {

    @Mock
    private ShortenerRepository shortenerRepository;

    @InjectMocks
    private ShortenerService shortenerService;

    @Test
    void save_validURL_shouldCreateShortener() throws Exception {
        String url = "https://www.google.com";
        String alias = "google";

        Shortener expectedShortener = new Shortener(alias, url, 0);
        when(shortenerRepository.save(Mockito.any())).thenReturn(expectedShortener);

        Shortener savedShortener = shortenerService.save(alias, url);

        Assertions.assertEquals(expectedShortener, savedShortener);
        Mockito.verify(shortenerRepository).save(Mockito.any());
    }

@Test
void save_invalidURL_shouldThrowInvalidURLException() {
    String url = "invalid_url";
    String alias = "invalid";

    assertThrows(InvalidURLException.class, () -> shortenerService.save(alias, url));
}

@Test
void save_existingAlias_shouldThrowAlreadyExistsException() throws Exception {
    String url = "https://www.google.com";
    String alias = "existing_alias";

    when(shortenerRepository.existsById(alias)).thenReturn(true);

    assertThrows(AlreadyExistsException.class, () -> shortenerService.save(alias, url));
}

@Test
void findOriginalUrl_existingAlias_shouldReturnOriginalURL() {
    String alias = "existing_alias";
    String originalURL = "https://www.google.com";
    Shortener shortener = new Shortener(alias, originalURL, 0);

    when(shortenerRepository.findById(alias)).thenReturn(Optional.of(shortener));

    String foundURL = shortenerService.findOriginalUrl(alias);

    Assertions.assertEquals(originalURL, foundURL);
    Mockito.verify(shortenerRepository).findById(alias);
    Mockito.verify(shortenerRepository).save(shortener);
}

@Test
void findOriginalUrl_nonExistingAlias_shouldThrowUrlNotFound() {
    String alias = "non_existing_alias";

    when(shortenerRepository.findById(alias)).thenReturn(Optional.empty());

    assertThrows(UrlNotFound.class, () -> shortenerService.findOriginalUrl(alias));
}

@Test
void findTopTenMostAccesseds_shouldReturnTopTenShorteners() {
    Shortener shortener1 = new Shortener("alias1", "url1", 10);
    Shortener shortener2 = new Shortener("alias2", "url2", 5);

    when(shortenerRepository.findTop10ByOrderByClickCountDesc()).thenReturn(List.of(shortener1, shortener2));

    List<Shortener> topTen = shortenerService.findTopTenMostAccesseds();

    Assertions.assertEquals(2, topTen.size());
    Assertions.assertEquals(shortener1, topTen.get(0));
    Assertions.assertEquals(shortener2, topTen.get(1));
}
}