package aromanticcat.umcproject.service;

import aromanticcat.umcproject.converter.NangmanLetterBoxConverter;
import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.repository.MemberRepository;
import aromanticcat.umcproject.repository.NangmanLetterRepository;
import aromanticcat.umcproject.repository.NangmanReplyRepository;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NangmanLetterBoxServiceImpl implements NangmanLetterBoxService {

    private final NangmanLetterRepository nangmanLetterRepository;
    private final MemberRepository memberRepository;
    private final RandomNicknameService randomNicknameService;
    private final NangmanReplyRepository nangmanReplyRepository;
    @Override
    @Transactional
    public NangmanLetter writeAndSendLetter(NangmanLetterBoxRequestDTO.WriteLetterDTO request){
        //멤버 엔티티 조회
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다. ID: " + request.getMemberId()));

        NangmanLetter newNangmanLetter = NangmanLetterBoxConverter.toNangmanLetter(request, member);

        return nangmanLetterRepository.save(newNangmanLetter);
    }

    @Override
    @Transactional
    public List<NangmanLetter> getLetterList(){

        return nangmanLetterRepository.findByHasResponseFalse();
    }

    @Override
    @Transactional
    public NangmanLetter getLetterById(Long nangmanLetterId){
        return nangmanLetterRepository.findById(nangmanLetterId)
                .orElseThrow(() -> new RuntimeException("편지를 찾을 수 없습니다. ID: " + nangmanLetterId));
    }

    @Override
    @Transactional
    public NangmanReply writeAndSendReply(NangmanLetterBoxRequestDTO.WriteReplyDTO request, Long nangmanLetterId){
        //사용자가 오늘 이미 답장을 작성했는지 확인
        boolean hasUserRepliedToday = hasUserRepliedToday(request.getMemberId());

        if (hasUserRepliedToday) {
            throw new RuntimeException("오늘은 이미 답장을 작성했습니다.");
        }

        //특정 편지에 대한 정보 조회
        NangmanLetter nangmanLetter = getLetterById(nangmanLetterId);

        //멤버 엔티티 조회
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다. ID: " + request.getMemberId()));

        //답장 작성 및 발송
        NangmanReply newNangmanReply = NangmanLetterBoxConverter.toNangmanReply(request, nangmanLetter, member);

        //편지 답장 상태 업데이트
        nangmanLetter.setHasResponse(true);

        return nangmanReplyRepository.save(newNangmanReply);

    }

    private boolean hasUserRepliedToday(Long memberId){
        LocalDate today = LocalDate.now();

        return nangmanReplyRepository.existsByMemberIdAndCreatedAtBetween(
                memberId,
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay().minusSeconds(1)
        );
    }

//
//    private void addLetterToNangmanCollectoin(NangmanLetter nangmanLetter){
//        // 낭만 모음집에 해당 편지를 추가하는 로직을 구현
//        // 추가되는 방법은 해당 모음집의 컬렉션에 편지를 추가하거나 다른 적절한 방법으로 구현
//    }
}
