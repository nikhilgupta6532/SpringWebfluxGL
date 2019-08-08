package com.springReactive.practice.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springReactive.practice.error.PlatformError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimeModel implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  @Id
  @Field("id")
  private transient String id;

  private String _id;

  @JsonProperty("animeName")
  private String name;

  @JsonProperty("animeDescription")
  private String description;

  @JsonProperty("animeRating")
  private Double rating;

  @JsonProperty("animeDirector")
  private String director;

  private PlatformError error;

  private Map<String, Object> existingAnime;

}
