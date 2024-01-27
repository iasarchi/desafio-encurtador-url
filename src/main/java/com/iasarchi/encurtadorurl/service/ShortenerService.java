package com.iasarchi.encurtadorurl.service;

import com.iasarchi.encurtadorurl.entity.Shortener;
import com.iasarchi.encurtadorurl.exception.AlreadyExistsException;
import com.iasarchi.encurtadorurl.exception.InvalidURLException;
import com.iasarchi.encurtadorurl.exception.UrlNotFound;
import com.iasarchi.encurtadorurl.repository.ShortenerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.ResourceUtils.isUrl;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShortenerService {

    private final ShortenerRepository shortenerRepository;

    public Shortener save(String alias, String url) throws Exception {
        if (!isValidURL(url)) {
            throw new InvalidURLException("Invalid URL provided");
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
        return shortenerRepository.save(shortener);
    }

    public String findOriginalUrl(String alias) {
        Optional<Shortener> optionalShortener = shortenerRepository.findById(alias);
        if (optionalShortener.isPresent()) {
            Shortener shortener = optionalShortener.get();
            incrementClickCount(shortener);
            return shortener.getOriginalUrl();
        } else {
            throw new UrlNotFound("SHORTENED URL NOT FOUND");
        }
    }

    private void incrementClickCount(Shortener shortener) {
        int countner = shortener.getClickCount() + 1;
        shortener.setClickCount(countner);
        shortenerRepository.save(shortener);
    }

    public List<Shortener> findTopTenMostAccesseds(){
        return shortenerRepository.findTop10ByOrderByClickCountDesc();
    }

    private boolean isValidURL(String url) {
        return isUrl(url);
    }

    private void validateAliasIsUnique(String alias) throws AlreadyExistsException {
        if (shortenerRepository.existsById(alias)) {
            throw new AlreadyExistsException("CUSTOM ALIAS ALREADY EXISTS");
        }
    }

    private String generateHash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);

        return no.toString(16);
    }
}
