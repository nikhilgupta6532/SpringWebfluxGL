package com.springReactive.practice.utils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public interface CommonUtils {

  static String generateDocId(String... args) {
    return Arrays.stream(args).collect(Collectors.joining());
  }

  static String getTimeStamp() {
    return DateUtils.formatDateTime(LocalDateTime.now()).get();
  }

  static String formatMessage(String pattern, Object[] obj) {
    MessageFormat format = new MessageFormat(pattern);
    return format.format(obj);
  }

  static String generateUUID() {
    return UUID.randomUUID().toString();
  }

}
