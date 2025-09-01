package com.mt_analytics.mt_analytics.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
public class SuccessResponse {
  private Integer statusCode;
  private String message;
  private LocalDateTime date;
}
