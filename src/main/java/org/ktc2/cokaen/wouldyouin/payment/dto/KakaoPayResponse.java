package org.ktc2.cokaen.wouldyouin.payment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonNaming(value = SnakeCaseStrategy.class)
public class KakaoPayResponse {

    private final String tid;
    private final String nextRedirectAppUrl;
    private final String nextRedirectMobileUrl;
    private final String nextRedirectPcUrl;
    private final String androidAppScheme;
    private final String iosAppScheme;
    private final LocalDateTime createdAt;
}