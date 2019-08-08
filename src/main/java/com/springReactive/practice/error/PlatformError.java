package com.springReactive.practice.error;

import lombok.Data;

@Data
public class PlatformError {

  private String timestamp;

  private Integer status;

  private String error;

  private String message;

  public PlatformError() {
    super();
  }

}
