package com.springReactive.practice.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springReactive.practice.error.PlatformError;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimeResponse implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @JsonProperty("animeName")
    private String name;

    @JsonProperty("animeDescription")
    private String description;

    @JsonProperty("animeRating")
    private Double rating;

    @JsonProperty("animeDirector")
    private String director;

    @JsonProperty("error")
    private PlatformError error;

    @JsonProperty("entityStatus")
    private String entityStatus;

    @JsonProperty("status")
    private int status;

    @JsonProperty("existingAnime")
    private Map<String,Object> existingAnime;
}
