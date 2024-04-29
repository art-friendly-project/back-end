package com.artfriendly.artfriendly.domain.member.entity;

import com.artfriendly.artfriendly.domain.common.BaseTimeEntity;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.DambyeolagBookmark;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionLike;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionHope;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionView;
import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;
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

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    private MemberImage image;

    @Column
    private String nickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> artPreferenceTypeList = new ArrayList<>();

    // 연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Dambyeolag> dambyeolagList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<DambyeolagBookmark> dambyeolagBookmarkList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Sticker> stickerList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ExhibitionLike> exhibitionLikeList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ExhibitionHope> exhibitionHopeList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ExhibitionView> exhibitionViewList = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String nickName, MemberImage image, List<String> role, List<String> artPreferenceTypeList, Mbti mbti) {
        this.id = id;
        this.email = email;
        this.image = image;
        this.nickName = nickName;
        this.role = role;
        this.artPreferenceTypeList = artPreferenceTypeList;
        this.mbti = mbti;
    }

    public void updateForm(Member memberToUpdate) {
        this.nickName = memberToUpdate.getNickName() == null ? this.nickName : memberToUpdate.getNickName();
        // 입력 받은 전시 취향이 존재하는가
        for(String type : memberToUpdate.artPreferenceTypeList) {
            ArtPreferenceType.check(type);
        }
        this.artPreferenceTypeList = memberToUpdate.artPreferenceTypeList == null ? this.artPreferenceTypeList : memberToUpdate.artPreferenceTypeList;
        this.mbti = memberToUpdate.mbti == null ? this.mbti : memberToUpdate.mbti;
    }
    public void setImage(MemberImage memberImage) { this.image = memberImage; }
}
