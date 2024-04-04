package com.artfriendly.artfriendly.domain.member.entity;

import com.artfriendly.artfriendly.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
// 다른 패키지에서 Member를 생성할 수 없도록 PROTECTED, PRIVATE로 설정 시 프록시 객체가 생성되지 않는다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // email 컬럼 인덱스 처리
    private String email;

    @Column
    private String imageUrl;

    @Column
    private String nickName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String imageUrl, String nickName, List<String> role) {
        this.id = id;
        this.email = email;
        this.imageUrl = imageUrl;
        this.nickName = nickName;
        this.role = role;
    }

    public void updateForm(Member memberToUpdate) {
        this.nickName = memberToUpdate.getNickName();
    }
}
