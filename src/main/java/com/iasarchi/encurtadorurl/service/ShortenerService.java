package com.iasarchi.encurtadorurl.service;

import com.iasarchi.encurtadorurl.entity.Shortener;
import com.iasarchi.encurtadorurl.exception.AlreadyExistsException;
import com.iasarchi.encurtadorurl.repository.ShortenerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShortenerService {

    private final ShortenerRepository shortenerRepository;

    public Shortener save(String alias, String url) throws Exception {
        Shortener shortener;
        if (Strings.isNotBlank(alias)){
            if(shortenerRepository.existsById(alias)) {
                throw new AlreadyExistsException("CUSTOM ALIAS ALREADY EXISTS");
            }
           shortener = new Shortener(alias, url);
        } else {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(url.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            shortener = new Shortener(hashtext.substring(0, 6), url);
        }
        return shortenerRepository.save(shortener);
    }
}
