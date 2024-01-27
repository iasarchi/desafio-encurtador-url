package com.iasarchi.encurtadorurl.service;

import com.iasarchi.encurtadorurl.entity.Shortener;
import com.iasarchi.encurtadorurl.exception.AlreadyExistsException;
import com.iasarchi.encurtadorurl.exception.InvalidURLException;
import com.iasarchi.encurtadorurl.exception.UrlNotFound;
import com.iasarchi.encurtadorurl.repository.ShortenerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.ResourceUtils.isUrl;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ShortenerService {

    private final ShortenerRepository shortenerRepository;

    public Shortener save(String alias, String url) throws Exception {
        log.info("[ShortenerService.save] :: Starting shortener url");

        if (!isValidURL(url)) {
            log.error("[ShortenerService.save] :: Invalid URL provided -> {} ", url);
            throw new InvalidURLException("INVALID URL PROVIDED");
        }

        Shortener shortener;
        if (Strings.isNotBlank(alias)){
            validateAliasIsUnique(alias);
           shortener = new Shortener(alias, url, 0);
        } else {
            String urlIncremented = url + Instant.now().toString();
            String generatedHash = generateHash(urlIncremented);
            shortener = new Shortener(generatedHash.substring(0, 6), url ,0);
        }
        log.info("[ShortenerService.save] :: Finishing shortener url");
        return shortenerRepository.save(shortener);
    }

    public String findOriginalUrl(String alias) {
        log.info("[ShortenerService.findOriginalUrl] :: Searching url");
        Optional<Shortener> optionalShortener = shortenerRepository.findById(alias);
        if (optionalShortener.isPresent()) {
            Shortener shortener = optionalShortener.get();
            incrementClickCount(shortener);
            return shortener.getOriginalUrl();
        } else {
            log.error("[ShortenerService.findOriginalUrl] :: SHORTENED URL NOT FOUND");
            throw new UrlNotFound("SHORTENED URL NOT FOUND");
        }
    }

    private void incrementClickCount(Shortener shortener) {
        log.info("[ShortenerService.incrementClickCount] :: Incrementing click count");
        int countner = shortener.getClickCount() + 1;
        shortener.setClickCount(countner);
        shortenerRepository.save(shortener);
    }

    public List<Shortener> findTopTenMostAccesseds(){
        log.info("[ShortenerService.findTopTenMostAccesseds] :: Find top 10 URL most clicked");
        return shortenerRepository.findTop10ByOrderByClickCountDesc();
    }

    private boolean isValidURL(String url) {
        log.info("[ShortenerService.isValidURL] :: Validating URL");
        return isUrl(url);
    }

    private void validateAliasIsUnique(String alias) throws AlreadyExistsException {
        log.info("[ShortenerService.validateAliasIsUnique] :: Validating if alias is unique");
        if (shortenerRepository.existsById(alias)) {
            log.error("[ShortenerService.validateAliasIsUnique] :: CUSTOM ALIAS ALREADY EXISTS");
            throw new AlreadyExistsException("CUSTOM ALIAS ALREADY EXISTS");
        }
    }

    private String generateHash(String input) throws Exception {
        log.info("[ShortenerService.generateHash] :: Generating hash");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);

        return no.toString(16);
    }
}
