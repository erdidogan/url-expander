package com.erdi.shortlink;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ShortlinkUtilTests {


    @Test
    public void testRealUrl() throws IOException {
        String actual = "https://www.google.com";
        String shortUrl = "https://www.google.com";
        assertThat(actual).isEqualTo(ShortlinkUtil.expand(shortUrl));

    }

    @Test
    public void testUnshortenOnce() throws IOException {
        String actual = "https://erdidogan.com/";
        String shortUrl = "https://bit.ly/3jYhlrn";
        assertThat(actual).isEqualTo(ShortlinkUtil.expand(shortUrl));
    }

    @Test
    public void testRedirectLoop() throws IOException {
        String actual = "https://ahrefs.com/blog/seo-metrics/";
        String shortUrl = "https://ahrefs.com/blog/seo-metrics";
        // String shortUrl = "https://bit.ly/yvqi8u";
        assertThat(actual).isEqualTo(ShortlinkUtil.expand(shortUrl));
    }
}
