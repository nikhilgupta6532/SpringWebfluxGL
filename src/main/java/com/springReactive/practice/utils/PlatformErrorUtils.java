package com.springReactive.practice.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springReactive.practice.constant.CommonConstants;
import com.springReactive.practice.error.PlatformError;
import com.springReactive.practice.model.Anime;
import com.springReactive.practice.model.AnimeResponse;
import com.springReactive.practice.model.ValidationResult;

import reactor.core.publisher.Mono;

public final class PlatformErrorUtils {

  private static Map<Integer, String> errorMessage = new HashMap<>();

  static {
    errorMessage.put(HttpStatus.NOT_FOUND.value(), CommonConstants.OBJECT_NOT_FOUND_ERROR);
    errorMessage.put(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        CommonConstants.INTERNAL_SERVER_ERROR);
    errorMessage.put(HttpStatus.BAD_REQUEST.value(), CommonConstants.BAD_REQUEST);
    errorMessage.put(HttpStatus.CONFLICT.value(), CommonConstants.CONFLICT_REQUEST);
    errorMessage.put(HttpStatus.UNPROCESSABLE_ENTITY.value(), CommonConstants.UNPROCESSABLE_ENTITY);
  }

  public static PlatformError createPlatformError(String userDesc, int statusCode) {
    PlatformError error = new PlatformError();
    error.setTimestamp(CommonUtils.getTimeStamp());
    error.setMessage(userDesc);
    error.setStatus(statusCode);
    error.setError(errorMessage.get(statusCode));
    return error;
  }

  public static ValidationResult createValidationResult(List<String> errors, HttpStatus status) {
    ValidationResult validationResult = new ValidationResult();
    validationResult.setFailureFlag(Boolean.TRUE);
    validationResult.setErrors(errors);
    validationResult.setStatus(status);
    return validationResult;
  }

  private static Mono<ServerResponse> createErrorResponse(String message, int statusCode) {
    return ServerResponse.status(statusCode)
        .body(BodyInserters.fromObject(createPlatformError(message, statusCode)));
  }

  public static Mono<ServerResponse> handleExceptions(Throwable ex) {
    if (ex instanceof ValidationException) {
      String message = ex.getMessage();
      String errorCode = ((ValidationException) ex).getErrorCode();
      int statusCode = HttpStatus.valueOf(errorCode).value();
      return ServerResponse.status(statusCode)
          .body(BodyInserters.fromObject(createErrorResponse(message, statusCode)));
    } else {
      return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BodyInserters.fromObject(
          createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 500)));
    }
  }

  public static Mono<AnimeResponse> createValidationError(Anime anime) {
    AnimeResponse animeResponse = new AnimeResponse();
    PlatformError platformError = createPlatformError(
        anime.getValidationResult().getErrors().toString(),
        anime.getValidationResult().getStatus().value());
    animeResponse.setError(platformError);
    animeResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    animeResponse.setEntityStatus(CommonConstants.ERROR);
    return Mono.just(animeResponse);

  }
}
