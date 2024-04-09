package com.artfriendly.artfriendly.domain.dambyeolag.repository;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.entity.MemberImage;
import com.artfriendly.artfriendly.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DambyeolagRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DambyeolagRepository dambyeolagRepository;
    @Autowired
    private StickerRepository stickerRepository;
    @BeforeEach
    void setUp() {
        List<Member> members = new ArrayList<>();
        for(int i = 1; i < 5; i++) {
            Member member = Member.builder()
                    .id(Long.parseLong(String.valueOf(i)))
                    .image(MemberImage.builder()
                            .id(Long.parseLong(String.valueOf(i)))
                            .imageUrl("testMember"+ i)
                            .fileName("testMember"+ i)
                            .build())
                    .email("testMember"+ i)
                    .role(List.of("USER"))
                    .nickName("testMember"+ i)
                    .build();

            members.add(member);
        }

        List<Dambyeolag> dambyeolags = new ArrayList<>();
        for(int i = 1; i < 5; i++) {
            Dambyeolag dambyeolag = Dambyeolag.builder()
                    .id(Long.parseLong(String.valueOf(i)))
                    .title("testTitle"+ i)
                    .body("testBody"+ i)
                    .exhibitionId(1L)
                    .member(members.get((i-1) % 4))
                    .build();

            dambyeolags.add(dambyeolag);
        }

        List<Sticker> stickers = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            for(int j = 1; j < i; j++) {

            }
            Sticker sticker = Sticker.builder()
                    .id(Long.parseLong(String.valueOf(i)))
                    .xCoordinate(i)
                    .yCoordinate(i)
                    .body("test" + i)
                    .member(members.get(i - 1))
                    .dambyeolag(dambyeolags.get(i - 1))
                    .build();

            stickers.add(sticker);
            sticker.getDambyeolag().addSticker(sticker);
        }

        Sticker sticker = Sticker.builder()
                .id(Long.parseLong(String.valueOf(5)))
                .xCoordinate(3)
                .yCoordinate(4)
                .body("test" + "5")
                .member(members.get(1))
                .dambyeolag(dambyeolags.get(0))
                .build();

        stickers.add(sticker);
        sticker.getDambyeolag().addSticker(sticker);

        memberRepository.saveAll(members);
        dambyeolagRepository.saveAll(dambyeolags);
        stickerRepository.saveAll(stickers);
    }

    @Test
    void findDambyeolagById() {
    }

    @Test
    void findDambyeolagPage() {
        Pageable pageable = PageRequest.of(0, 4);
        Page<Dambyeolag> dambyeolags = dambyeolagRepository.findByOrderByStickerCountDesc(pageable, 1L);

        List<Dambyeolag> content = dambyeolags.getContent();
        System.out.println(content.size());
        content.stream().map(dambyeolag -> dambyeolag.getStickerList().size()).forEach(System.out::println);
    }
}