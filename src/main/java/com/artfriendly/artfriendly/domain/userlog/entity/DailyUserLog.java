package com.artfriendly.artfriendly.domain.userlog.entity;

import com.artfriendly.artfriendly.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyUserLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int userCount;

    @Builder
    public DailyUserLog(Long id, int userCount) {
        this.id = id;
        this.userCount = userCount;
    }
}
