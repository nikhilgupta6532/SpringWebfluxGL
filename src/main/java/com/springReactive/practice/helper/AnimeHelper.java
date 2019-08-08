package com.springReactive.practice.helper;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.springReactive.practice.model.Anime;
import com.springReactive.practice.model.ValidationResult;
import com.springReactive.practice.utils.PlatformErrorUtils;

import reactor.util.function.Tuple2;

@Component
public class AnimeHelper {

  public AnimeHelper() {
    super();
  }

  public Anime buildAnimePayloadWithError(Tuple2<Anime, List<String>> animeListTuple2) {
    if (animeListTuple2.getT2().isEmpty()) {
      return animeListTuple2.getT1();
    } else {
      Anime anime = animeListTuple2.getT1();
      ValidationResult validationResult = PlatformErrorUtils
          .createValidationResult(animeListTuple2.getT2(), HttpStatus.BAD_REQUEST);
      anime.setValidationResult(validationResult);
      return anime;
    }
  }
}
