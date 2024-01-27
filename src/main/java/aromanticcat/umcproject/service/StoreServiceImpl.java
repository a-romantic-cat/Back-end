package aromanticcat.umcproject.service;

import aromanticcat.umcproject.converter.StoreConverter;
import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.entity.LetterPaper;
import aromanticcat.umcproject.repository.AcquiredItemRepository;
import aromanticcat.umcproject.repository.LetterPaperRepository;
import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;
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
public class StoreServiceImpl implements StoreService{

    private final LetterPaperRepository letterPaperRepository;
    private final AcquiredItemRepository acquiredItemRepository;


    @Override
    @Transactional
    public List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(Long memberId, int page, int pageSize){

        // 사용자가 구매한 편지지 목록 조회
        List<AcquiredItem> acquiredLetterPaperList = acquiredItemRepository.findByMemberId(memberId);


        // 모든 편지지 목록 조회
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<LetterPaper> allLetterPaperPage = letterPaperRepository.findAll(pageable);

        List<LetterPaper> allLetterPaperList = allLetterPaperPage.getContent();

        List<StoreResponseDTO.LetterPaperResultDTO> responseDTOs = allLetterPaperList.stream()
                .map(letterPaper -> StoreConverter.toLetterPaperResultDTO(letterPaper, acquiredLetterPaperList))
                .collect(Collectors.toList());

        return responseDTOs;
    }
}
