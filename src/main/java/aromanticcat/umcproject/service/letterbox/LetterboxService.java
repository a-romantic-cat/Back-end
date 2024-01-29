package aromanticcat.umcproject.service.letterbox;

import aromanticcat.umcproject.entity.Letterbox;
import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.repository.LetterBoxRepository;
import aromanticcat.umcproject.repository.MemberRepository;
import aromanticcat.umcproject.web.dto.LetterboxRequest;
import aromanticcat.umcproject.web.dto.LetterboxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LetterboxService {
    private final LetterBoxRepository letterboxRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String createLetterbox(LetterboxRequest letterboxRequest) {
        Member member = memberRepository.findById(letterboxRequest.getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("Member not found. id=" + letterboxRequest.getMember_id()));

        Letterbox letterbox = letterboxRequest.toEntity();
        letterbox.setMember(member);
        letterboxRepository.save(letterbox);
        return letterbox.getName();
    }

    public LetterboxResponse getLetterboxById(Long letterboxId) {
        Letterbox letterbox = letterboxRepository.findById(letterboxId).orElse(null);

        if (letterbox != null) {
            return letterbox.toResponse(letterbox);
        } else {
            return null;
        }
    }
}
