package com.erdi.shortlink;

import com.erdi.shortlink.exception.ShortUrlValidationException;
import com.erdi.shortlink.exception.WebUrlNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class ShortlinkService {

    Shortlink shortlinkOps(Shortlink input) {
        try {
            final String shortUrl = this.validateShortUrl(input);
            Shortlink response = new Shortlink();
            final String webUrl = ShortlinkUtil.expand(shortUrl);
            response.setShortUrl(shortUrl);
            response.setWebUrl(webUrl);
            return response;
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            throw new WebUrlNotFoundException("Short url expand error");
        }
    }

    private String validateShortUrl(Shortlink shortlink) {
        try {
            return new URL(shortlink.getShortUrl()).toString();
        } catch (MalformedURLException e) {
            throw new ShortUrlValidationException("ShortUrl must be a url");
        }
    }


}
