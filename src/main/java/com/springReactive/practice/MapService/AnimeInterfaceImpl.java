package com.springReactive.practice.MapService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springReactive.practice.constant.CommonConstants;
import com.springReactive.practice.error.PlatformError;
import com.springReactive.practice.helper.AnimeHelper;
import com.springReactive.practice.model.Anime;
import com.springReactive.practice.model.AnimeModel;
import com.springReactive.practice.model.AnimeResponse;
import com.springReactive.practice.repository.AnimeRepository;
import com.springReactive.practice.utils.CommonUtils;
import com.springReactive.practice.utils.PlatformErrorUtils;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class AnimeInterfaceImpl implements AnimeInterface {

  Logger logger = LoggerFactory.getLogger(AnimeInterfaceImpl.class);

  @Autowired
  private AnimeRepository animeRepository;

  @Autowired
  private AnimeHelper animeHelper;

  @Override
  public Mono<AnimeResponse> postAnime(Tuple2<Anime, List<String>> animeListTuple2) {
    Anime anime = animeHelper.buildAnimePayloadWithError(animeListTuple2);
    if (!ObjectUtils.isEmpty(anime.getValidationResult())) {
      return PlatformErrorUtils.createValidationError(anime);
    } else {
      return createImmutableAnimeModel.andThen(saveAnimeModel).apply(anime)
          .map(animeModel -> this.buildAnimeResponse.apply(animeModel));
    }
  }

  private Function<Anime, AnimeModel> createImmutableAnimeModel = anime -> {
    AnimeModel animeModel = new AnimeModel();
    animeModel.set_id(CommonUtils.generateUUID());
    animeModel.setName(anime.getAnimeName());
    animeModel.setDescription(anime.getAnimeDescription());
    animeModel.setDirector(anime.getDirectorName());
    animeModel.setRating(anime.getAnimeRatings());
    return animeModel;
  };

  private Function<AnimeModel, AnimeResponse> buildAnimeResponse = anime -> {
    AnimeResponse animeResponse = new AnimeResponse();
    animeResponse.setName(anime.getName());
    animeResponse.setDescription(anime.getDescription());
    animeResponse.setDirector(anime.getDirector());
    animeResponse.setRating(anime.getRating());
    animeResponse.setExistingAnime(anime.getExistingAnime());
    animeResponse.setError(anime.getError());
    animeResponse.setEntityStatus(CommonConstants.SUCCESS);
    animeResponse.setStatus(HttpStatus.CREATED.value());
    return animeResponse;
  };

  private Function<AnimeModel, Mono<AnimeModel>> saveAnimeModel = anime -> animeRepository
      .findByName(anime.getName())
      .doOnSuccess(success -> logger.debug("Document fetched successfully with id : {}", success))
      .flatMap(document -> {
        Map<String, Object> existingResourceMap = new HashMap<>();
        AnimeModel animeModel = new AnimeModel();
        existingResourceMap.put(anime.getName(), anime);
        animeModel.setExistingAnime(existingResourceMap);
        PlatformError error = PlatformErrorUtils.createPlatformError(CommonUtils
            .formatMessage("Anime with name {0} already exists", new Object[] { anime.getName() }),
            HttpStatus.CONFLICT.value());
        animeModel.setError(error);
        return Mono.just(animeModel);
      }).switchIfEmpty(this.saveDocument.apply(anime)).onErrorResume(ex -> {
        logger.error("Exception occured: {}", ex);
        return Mono.error(ex);
      });

  private Function<AnimeModel, Mono<AnimeModel>> saveDocument = anime -> {
    String docId = CommonUtils.generateDocId(CommonConstants.ANIME, CommonConstants.DOUBLE_COLON,
        anime.get_id());
    anime.setId(docId);
    return animeRepository.save(anime)
        .doOnSuccess(success -> logger.debug("Saved successfully with id : {}", success.getId()));
  };
}
