package com.springReactive.practice.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Document
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Anime implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @Id
    @Field("id")
    private transient String id;

    private String _id;

    @JsonProperty("name")
    @NotNull
    private String animeName;

    @JsonProperty("description")
    private String animeDescription;

    @JsonProperty("ratings")
    @NotNull
    private Double animeRatings;

    @JsonProperty("director")
    private String directorName;

    @JsonProperty("validationResult")
    private ValidationResult validationResult;
}
