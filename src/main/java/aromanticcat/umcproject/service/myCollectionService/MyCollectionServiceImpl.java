package aromanticcat.umcproject.service.myCollectionService;

import aromanticcat.umcproject.converter.MyCollectionConverter;
import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.repository.AcquiredItemRepository;
import aromanticcat.umcproject.web.dto.MyCollectionResponseDTO;
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
public class MyCollectionServiceImpl implements MyCollectionService {

    private final AcquiredItemRepository acquiredItemRepository;

    @Override
    @Transactional
    public List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> findLetterPaperList(Long memberId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        // 사용자가 구매한 편지지 목록 조회
        Page<AcquiredItem> acquiredLetterPaperPage = acquiredItemRepository.findByMemberIdAndLetterPaperIdIsNotNull(memberId, pageable);

        List<AcquiredItem> acquiredLetterPaperList = acquiredLetterPaperPage.getContent();

        List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> responseDTOs = acquiredLetterPaperList.stream()
                .map(letterPaper -> MyCollectionConverter.toAcquiredLetterPaperResultDTO(letterPaper))
                .collect(Collectors.toList());
        return responseDTOs;

    }

    @Override
    @Transactional
    public List<MyCollectionResponseDTO.AcquiredStampResultDTO> findStampList(Long memberId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        // 사용자가 구매한 우표 목록 조회
        Page<AcquiredItem> acquiredStampPage = acquiredItemRepository.findByMemberIdAndStampIdIsNotNull(memberId, pageable);

        List<AcquiredItem> acquiredStampList = acquiredStampPage.getContent();

        List<MyCollectionResponseDTO.AcquiredStampResultDTO> responseDTOs = acquiredStampList.stream()
                .map(stamp -> MyCollectionConverter.toAcquiredStampResultDTO(stamp))
                .collect(Collectors.toList());
        return responseDTOs;

    }


}
