package aromanticcat.umcproject.service.nangmanLetterBoxService;

import aromanticcat.umcproject.converter.NangmanLetterBoxConverter;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.repository.NangmanLetterRepository;
import aromanticcat.umcproject.repository.NangmanReplyRepository;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO> findCollection(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<NangmanLetter> letterPage = nangmanLetterRepository.findByIsPublicTrueAndHasResponseTrue(pageable);

        List<NangmanLetter> letterList = letterPage.getContent();

        return letterList.stream()
                .map(letter -> NangmanLetterBoxConverter.toPreviewBothResultDTO(letter, letter.getNangmanReply()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NangmanLetterBoxResponseDTO.BothResultDTO findCollectionDetails(Long nangmanLetterId) {

        NangmanLetter nangmanLetter = nangmanLetterRepository.findByIsPublicTrueAndHasResponseTrueAndId(nangmanLetterId);
        if(nangmanLetter == null){
            throw new RuntimeException("편지를 찾을 수 없습니다. ID: " + nangmanLetterId);
        }

        NangmanReply nangmanReply = nangmanReplyRepository.findByNangmanLetter_Id(nangmanLetterId);
        if(nangmanReply == null){
            throw new RuntimeException("해당 편지에 대한 답장을 찾을 수 없습니다. 편지 ID: " + nangmanLetterId);
        }

        return NangmanLetterBoxConverter.toBothResultDTO(nangmanLetter, nangmanReply);
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
}
