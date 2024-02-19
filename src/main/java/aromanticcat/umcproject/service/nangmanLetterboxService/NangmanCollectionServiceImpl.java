package aromanticcat.umcproject.service.nangmanLetterboxService;

import aromanticcat.umcproject.converter.NangmanCollectionConverter;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.repository.NangmanLetterRepository;
import aromanticcat.umcproject.repository.NangmanReplyRepository;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanCollectionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NangmanCollectionServiceImpl implements NangmanCollectionService{
    private final NangmanLetterRepository nangmanLetterRepository;
    private final NangmanReplyRepository nangmanReplyRepository;


    @Override
    @Transactional
    public Page<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> findCollection(int page, int pageSize, String sort){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<NangmanLetter> letterPage;

        if (sort.equals("popular")) {
            letterPage = nangmanLetterRepository.findPopularLetters(pageable);
        } else {
            letterPage = nangmanLetterRepository.findLatestLetters(pageable);
        }

        List<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> resultDTOList = letterPage.getContent().stream()
                .map(letter -> NangmanCollectionConverter.toPreviewBothResultDTO(letter))
                .collect(Collectors.toList());

        return new PageImpl<>(resultDTOList, pageable, letterPage.getTotalElements());
    }

    @Override
    @Transactional
    public NangmanCollectionResponseDTO.LetterAndReplyResultDTO findCollectionDetails(Long nangmanLetterId) {

        NangmanLetter nangmanLetter = nangmanLetterRepository.findByIsPublicTrueAndHasResponseTrueAndId(nangmanLetterId);
        if(nangmanLetter == null){
            throw new RuntimeException("편지가 공개되지 않았거나 해당 편지를 찾을 수 없습니다. ID: " + nangmanLetterId);
        }

        NangmanReply nangmanReply = nangmanReplyRepository.findByNangmanLetter_Id(nangmanLetterId);
        if(nangmanReply == null){
            throw new RuntimeException("해당 편지에 대한 답장을 찾을 수 없습니다. 편지 ID: " + nangmanLetterId);
        }

        return NangmanCollectionConverter.toBothResultDTO(nangmanLetter, nangmanReply);
    }


    @Override
    @Transactional
    public void likeCollection(Long nangmanLetterId, String emojiType){
        NangmanLetter nangmanLetter = nangmanLetterRepository.findByIsPublicTrueAndHasResponseTrueAndId(nangmanLetterId);
        if(nangmanLetter == null){
            throw new RuntimeException("편지를 찾을 수 없습니다. ID: " + nangmanLetterId);
        }

        if(!isValidEmojiType(emojiType)){
            throw new IllegalArgumentException("유효하지 않은 emojiType 입니다. emojiType: " + emojiType);
        }

        nangmanLetter.increaseEmojiCount(emojiType);

        nangmanLetterRepository.save(nangmanLetter);

    }

    // 유효하지 않은 emojiType인지 확인하는 메서드
    private boolean isValidEmojiType(String emojiType) {
        return emojiType.equals("thumbsUp") ||
                emojiType.equals("heart") ||
                emojiType.equals("cry") ||
                emojiType.equals("clover") ||
                emojiType.equals("clap") ||
                emojiType.equals("star");
    }


    // 사용자 ID로 해당 사용자가 작성한 편지 목록 조회
    @Override
    @Transactional
    public Page<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> getMyLetterPage(String email, int page, int pageSize){

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<NangmanLetter> myLetterPage = nangmanLetterRepository.findByMemberEmail(email, pageable);

        return myLetterPage.map(letter -> NangmanCollectionConverter.toPreviewBothResultDTO(letter));

    }

    @Override
    @Transactional
    public  Page<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> getMyReplyPage(String email, int page, int pageSize){

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<NangmanReply> myReplyPage = nangmanReplyRepository.findByMemberEmail(email, pageable);

        // 각 답장(+ 연결된 편지)에 대해 미리보기로 생성
        return myReplyPage.map(reply -> NangmanCollectionConverter.toPreviewBothResultDTO(reply.getNangmanLetter()));
    }
}
