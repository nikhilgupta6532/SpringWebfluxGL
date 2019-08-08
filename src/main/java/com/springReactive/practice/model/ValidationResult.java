package com.springReactive.practice.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationResult implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4620877833981639204L;

  @SerializedName("failureFlag")
  private Boolean failureFlag;

  @SerializedName("errors")
  private List<String> errors;

  @SerializedName("status")
  private HttpStatus status;
}
