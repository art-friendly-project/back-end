package com.artfriendly.artfriendly.global.init;

//import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;
//import com.artfriendly.artfriendly.domain.mbti.repository.MbtiRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class InitMbti implements ApplicationRunner {
//    private final MbtiRepository mbtiRepository;
//    @Transactional
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        List<Mbti> mbtiList = new ArrayList<>();
//
//        Mbti ISTJ = Mbti.builder()
//                .mbtiType("ISTJ")
//                .subTitle("감각적인 시선의 소유자")
//                .title("클로드 모네형")
//                .body("전시장을 천천히 거닐며 각 작품의 세세한 디테일을 꼼꼼히 살피며, \n" +
//                        "정확하게 기억합니다. 전시물의 배치와 조명에 대한 감각적인 이해를 가지고 있습니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ISTJ.png")
//                .build();
//        mbtiList.add(ISTJ);
//
//        Mbti ISFJ = Mbti.builder()
//                .mbtiType("ISFJ")
//                .subTitle("감정에 이입하는 순정파")
//                .title("빈센트 반 고흐형")
//                .body("각 작품에 깊은 감동을 받아 전시장에서 혼잣말로 감탄을 표현합니다. \n" +
//                        "작품의 감성에 공감하며, 감정이입을 통해 작품의 메시지를 간접적으로 체험합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ISFJ.png")
//                .build();
//        mbtiList.add(ISFJ);
//
//        Mbti INFJ = Mbti.builder()
//                .mbtiType("INFJ")
//                .subTitle("상징에 빠진 사색가")
//                .title("레오나르도 다빈치형")
//                .body("작품들 사이의 유기적인 연결을 찾아내며, 예술가의 의도를 깊이 이해하려고 노력합니다. \n" +
//                        "전시물의 숨겨진 의미와 상징성에 관심을 가지고 있습니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/INFJ.png")
//                .build();
//        mbtiList.add(INFJ);
//
//        Mbti INTJ = Mbti.builder()
//                .mbtiType("INTJ")
//                .subTitle("변화를 분석하는 혁신가")
//                .title("파블로 피카소형")
//                .body("전시장을 돌아다닐 때 각 작품의 혁신적인 표현을 주목합니다. \n" +
//                        "예술의 변화와 흐름을 분석하여, 작품들이 어떻게 시대를 변화시켰는지 고찰합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/INTJ.png")
//                .build();
//        mbtiList.add(INTJ);
//
//        Mbti ISTP = Mbti.builder()
//                .mbtiType("ISTP")
//                .subTitle("기술, 재료에 집중하는 분석가")
//                .title("미켈란젤로형")
//                .body("작품의 다양한 기술과 재료에 주목하며, \n" +
//                        "작품을 만들 때의 예술가의 손놀림과 기술적인 특성에 관심을 가지고 있습니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ISTP.png")
//                .build();
//        mbtiList.add(ISTP);
//
//        Mbti ISFP = Mbti.builder()
//                .mbtiType("ISFP")
//                .subTitle("정서적 표현에 공감하는 감성파")
//                .title("프리다칼로형")
//                .body("작품들에 담긴 감성을 감지하고, 예술가의 정서적인 표현에 공감합니다. \n" +
//                        "전시물을 통해 감정의 흐름을 따라가며 작품을 감상한다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ISFP.png")
//                .build();
//        mbtiList.add(ISFP);
//
//        Mbti INFP = Mbti.builder()
//                .mbtiType("INFP")
//                .subTitle("작품과 한 몸이 되는 물아일체형")
//                .title("조지아 오키프형")
//                .body("전시장에서 작품의 아름다움과 심미성에 매료되며, 작품 속에 담긴 감정의 깊이를 탐구합니다.\n" +
//                        " 예술가의 정신 세계에 몰입합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/INFP.png")
//                .build();
//        mbtiList.add(INFP);
//
//        Mbti INTP = Mbti.builder()
//                .mbtiType("INTP")
//                .subTitle("논리를 분석하는 철학가")
//                .title("살바도르 달리형")
//                .body("작품들의 논리와 비전을 분석하며, 예술가가 작품에 담고자 하는 철학적인 측면을 고찰합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/INTP.png")
//                .build();
//        mbtiList.add(INTP);
//
//        Mbti ESTP = Mbti.builder()
//                .mbtiType("ESTP")
//                .subTitle("독창성을 발휘하는 자유로운 탐험가")
//                .title("잭슨 폴록형")
//                .body("작품들 사이를 활발하게 이동하며, 작품에 담긴 에너지와 독창성을 경험합니다. \n" +
//                        "전시물과 상호작용하면서 자유로운 감각을 느낍니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ESTP.png")
//                .build();
//        mbtiList.add(ESTP);
//
//        Mbti ESFP = Mbti.builder()
//                .mbtiType("ESFP")
//                .subTitle("화려함에 매혹되는 심미주의")
//                .title("앤디워홀형")
//                .body("전시물의 다양한 스타일과 특색을 즐기며, \n" +
//                        "작품의 화려함과 독창성에 매혹됩니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ESFP.png")
//                .build();
//        mbtiList.add(ESFP);
//
//        Mbti ENFP = Mbti.builder()
//                .mbtiType("ENFP")
//                .subTitle("창의성, 다양성을 즐기는 호기심천국")
//                .title("빈센트 미넬리형")
//                .body("전시장을 즐겁게 돌아다니면서, 예술가의 창의성과 다양성을 즐깁니다. \n" +
//                        "작품의 다양한 면면에 관심을 갖고 있습니다. 종종 감상자들에게 말을 걸기도 합니다. ")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ENFP.png")
//                .build();
//        mbtiList.add(ENFP);
//
//        Mbti ENTP = Mbti.builder()
//                .mbtiType("ENTP")
//                .subTitle("독특함에 주목하는 스페셜리스트")
//                .title("장미셸 바스키아형")
//                .body("전시물의 독특한 표현과 예술의 경계를 뛰어넘는 요소에 주목하며, 작품에 대한 토론과 논쟁을 즐깁니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ENTP.png")
//                .build();
//        mbtiList.add(ENTP);
//
//        Mbti ESTJ = Mbti.builder()
//                .mbtiType("ESTJ")
//                .subTitle("작품의 역사에 집중하는 학자")
//                .title("렘브란트형")
//                .body("작품들의 조직과 표현된 주제에 집중하며, 예술의 역사와 작품들이 어떻게 관리되어 왔는지를 탐험합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ESTJ.png")
//                .build();
//        mbtiList.add(ESTJ);
//
//        Mbti ESFJ = Mbti.builder()
//                .mbtiType("ESFJ")
//                .subTitle("사회적 메시지에 집중하는 정치가")
//                .title("노먼 록웰형")
//                .body("작품들이 사회적인 메시지를 어떻게 전달하는지에 주목하며, 전시물 속 인물들의 이야기에 공감합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ESFJ.png")
//                .build();
//        mbtiList.add(ESFJ);
//
//        Mbti ENFJ = Mbti.builder()
//                .mbtiType("ENFJ")
//                .subTitle("비전을 바라보는 예언가")
//                .title("마우리치오 카텔란형")
//                .body("예술가의 예술적 비전과 사회적 메시지를 중요시하며, 작품의 아름다움과 깊은 의미를 탐험합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ENFJ.png")
//                .build();
//        mbtiList.add(ENFJ);
//
//        Mbti ENTJ = Mbti.builder()
//                .mbtiType("ENTJ")
//                .subTitle("작품으로 사회를 바라보는 분석가")
//                .title("디에고 리베라형")
//                .body("작품들이 어떻게 사회와 문화를 형성하는지를 고찰하며, 예술이 사회에 미치는 영향을 분석합니다.")
//                .imageUrl("https://artfriendly-bucket.s3.ap-northeast-2.amazonaws.com/mbti_Image/ENTJ.png")
//                .build();
//        mbtiList.add(ENTJ);
//
//        ISTJ.setMatchType(ISFJ);
//        ISTJ.setMissMatchType(ENFP);
//
//        ISFJ.setMatchType(ISTJ);
//        ISFJ.setMissMatchType(ENTP);
//
//        INFJ.setMatchType(INFP);
//        INFJ.setMissMatchType(ESTP);
//
//        INTJ.setMatchType(INTP);
//        INTJ.setMissMatchType(ESFP);
//
//        ISTP.setMatchType(ISFP);
//        ISTP.setMissMatchType(ENFJ);
//
//        ISFP.setMatchType(ISTP);
//        ISFP.setMissMatchType(ENTJ);
//
//        INFP.setMatchType(ENFP);
//        INFP.setMissMatchType(ESTJ);
//
//        INTP.setMatchType(ENTP);
//        INTP.setMissMatchType(ESFJ);
//
//        ESTP.setMatchType(ISTP);
//        ESTP.setMissMatchType(INFJ);
//
//        ESFP.setMatchType(ISFP);
//        ESFP.setMissMatchType(INTJ);
//
//        ENFP.setMatchType(INFP);
//        ENFP.setMissMatchType(ISTJ);
//
//        ENTP.setMatchType(INTP);
//        ENTP.setMissMatchType(ISFJ);
//
//        ESTJ.setMatchType(ISTJ);
//        ESTJ.setMissMatchType(INFP);
//
//        ESFJ.setMatchType(ISFJ);
//        ESFJ.setMissMatchType(INTP);
//
//        ENFJ.setMatchType(INFJ);
//        ENFJ.setMissMatchType(ISTP);
//
//        ENTJ.setMatchType(INTJ);
//        ENTJ.setMissMatchType(ISFP);
//
//        mbtiRepository.saveAll(mbtiList);
//    }
//}
