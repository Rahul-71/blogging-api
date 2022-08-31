package com.blogapplication.blogappapis.payloads;

import lombok.Data;

// @Data is a convenient shortcut annotation that bundles the features of @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor together
@Data
public class JwtAuthResponse {

    private String token;


}
