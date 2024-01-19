package aromanticcat.umcproject.service;

import aromanticcat.umcproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NangmanLetterServiceTests {

    @Autowired
    private NangmanLetterService nangmanLetterService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RandomNicknameService randomNicknameService;

//    @Test
//    public void testRegister(){
//        System.out.println(nangmanLetterService.getClass().getName());
//
//        Member member1 = memberRepository.findById(1L).orElseThrow(() -> new RuntimeException("Member not found"));
//
//        //랜덤 닉네임 생성
//        String randomNickname = randomNicknameService.generateRandomNickname();
//
//        NangmanLetterDTO nangmanLetterDTO = NangmanLetterDTO.builder()
//                .content("낭만 편지 샘플3")
//                .is_public(false)
//                .member(member1)
//                .build();
//
//        //생성된 랜덤 닉네임 설정
//        nangmanLetterDTO.setSender_nickname(randomNickname);
//
//        Long id = nangmanLetterService.register(nangmanLetterDTO);
//
//        System.out.println("id: " + id);
//    }

//    @Test
//    public void testModify(){
//
//        //변경에 필요한 데이터만
//        NangmanLetterDTO nangmanLetterDTO = NangmanLetterDTO.builder()
//                .id(4L)
//                .has_response(true)
//                .build();
//
//        nangmanLetterService.modify(nangmanLetterDTO);
//    }
}
