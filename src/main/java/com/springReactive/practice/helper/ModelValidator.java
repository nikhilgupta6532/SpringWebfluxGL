package com.springReactive.practice.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
public class ModelValidator {

  private Validator validator;

  public ModelValidator(Validator validator) {
    this.validator = validator;
  }

  public <T> Mono<Tuple2<T, List<String>>> applyBeanValidation(T obj, Class<?>... groups) {
    Set<ConstraintViolation<T>> violations = validator.validate(obj, groups);
    List<String> errorList = new ArrayList<>();
    if (!violations.isEmpty()) {
      violations.forEach(violation -> {
        errorList.add(violation.getPropertyPath() + " " + violation.getMessage());
      });
      return Mono.just(obj).zipWith(Mono.just(errorList));
    } else {
      return Mono.just(obj).zipWith(Mono.just(Collections.emptyList()));
    }
  }
}
