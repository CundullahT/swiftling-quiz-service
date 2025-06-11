package com.swiftling.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDailyStreakMessageDTO {

    private UUID userAccountId;
    private DailyStreakDTO dailyStreakDTO;
    private LocalDateTime timestamp;

}
