package com.erdi.shortlink;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shortlink {


    @NotEmpty(message = "ShortUrl is required")
    private String shortUrl;


    private String webUrl;

}
