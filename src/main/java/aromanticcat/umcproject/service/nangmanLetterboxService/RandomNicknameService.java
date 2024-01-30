package aromanticcat.umcproject.service.nangmanLetterboxService;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomNicknameService {

    private static final String[] ADJECTIVES = {
            "이야기를 나누는", "그림을 그리는", "창문 너머를 보는", "뒤척이는", "별빛을 따라 떠나는", "달콤한 꿈을 꾸는", "손에 쥐는",
            "어깨에 기대는", "길을 걷는", "노래하는", "햇살을 받는", "책을 읽는", "쇼핑을 하는", "생각에 잠긴", "향기에 취하는", "바람에 실려 오는",
            "바다를 바라보는", "눈을 감고 듣는", "손을 흔드는", "입술에 미소 지어지는", "흰 구름을 따라가는", "옛 추억에 빠진", "노을을 바라보는",
            "별들을 세는", "사진을 찍는", "운전하는", "손에 든 커피맛이 나는", "열쇠를 꽂는", "무엇인가를 찾는", "일기를 쓰는", "구름 사이를 걷는",
            "친구에게 전화하는", "빗소리를 듣는", "높은 곳을 내려다보는", "모자를 쓴", "새로운 일을 기대하는", "유쾌한 이야기를 나누는", "햇볕에 담근",
            "색다른 맛을 느끼는", "머릿결에 바람이 스치는", "두 손에 꽃을 든", "몽환적인 햇살에 비친", "달콤한 미소를 띤", "꿈결처럼 가벼운", "가슴에 품은",
            "순수한 눈으로 보는", "바닷가를 걷는", "강을 건너는", "별빛이 내리는", "작은 행복을 쌓는"
    };

    private static final String[] ANIMALS = {
            "알파카", "토끼", "햄스터", "고슴도치", "판다", "코알라", "산토끼", "레서 판다", "푸들", "기니피그", "레서캣",
            "물개", "비둘기", "고양이", "강아지", "병아리", "쿼카", "펭귄", "오소리", "개구리", "반달곰", "부엉이", "아기 곰", "거북이",
            "오리", "나비", "올빼미", "여우", "수달", "파랑새", "황제 펭귄", "호랑이", "해달", "자라", "아기 돼지", "백조"
    };

    public String generateRandomNickname() {
        Random random = new Random();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String animal = ANIMALS[random.nextInt(ANIMALS.length)]
                ;
        return adjective + " " + animal;
    }
}
