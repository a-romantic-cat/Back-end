package aromanticcat.umcproject.service;

import aromanticcat.umcproject.converter.NangmanLetterBoxConverter;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.repository.NangmanLetterRepository;
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

    @Override
    @Transactional
    public List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO> getCollection(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<NangmanLetter> letterPage = nangmanLetterRepository.findByIsPublicTrueAndHasResponseTrue(pageable);

        List<NangmanLetter> letterList = letterPage.getContent();

        return letterList.stream()
                .map(letter -> NangmanLetterBoxConverter.toPreviewBothResultDTO(letter, letter.getNangmanReply()))
                .collect(Collectors.toList());

    }

}
