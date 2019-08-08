/*
 * PEARSON PROPRIETARY AND CONFIDENTIAL INFORMATION SUBJECT TO NDA 
 * Copyright (c) 2018 Pearson Education, Inc.
 * All Rights Reserved. 
 * 
 * NOTICE: All information contained herein is, and remains the property of 
 * Pearson Education, Inc. The intellectual and technical concepts contained 
 * herein are proprietary to Pearson Education, Inc. and may be covered by U.S. 
 * and Foreign Patents, patent applications, and are protected by trade secret 
 * or copyright law. Dissemination of this information, reproduction of this  
 * material, and copying or distribution of this software is strictly forbidden   
 * unless prior written permission is obtained from Pearson Education, Inc.
 */
package com.springReactive.practice.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * The Class DateUtils.
 */
public final class DateUtils {

  /** The ISO_9601_FORMAT constant. */
  private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'+00:00'";

  /**
   * Instantiates a new date utils.
   */
  private DateUtils() {
    super();
  }

  /**
   * Formats the input local date time into the given pattern.
   *
   * @param dateTime
   *          the date time to format
   * @return the optional formatted date time string, empty optional if date
   *         time or pattern null
   */
  public static Optional<String> formatDateTime(final LocalDateTime dateTime) {
    Optional<String> formattedDateTime = Optional.empty();
    if (dateTime != null) {
      OffsetDateTime offsetDateTime = dateTime.atOffset(ZoneOffset.UTC);
      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_FORMAT);
      formattedDateTime = Optional.ofNullable(offsetDateTime.format(formatter));
    }
    return formattedDateTime;
  }

  /**
   * Current timestamp.
   *
   * @return the long
   */
  public static Long currentTimestamp() {
    final ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
    return utc.toEpochSecond() * 1000;
  }
}