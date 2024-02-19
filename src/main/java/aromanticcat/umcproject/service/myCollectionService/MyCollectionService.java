package aromanticcat.umcproject.service.myCollectionService;

import aromanticcat.umcproject.web.dto.MyCollectionResponseDTO;
import org.springframework.data.domain.Page;

public interface MyCollectionService {

    Page<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> findLetterPaperList(String memberEmail, int page, int pageSize, boolean onlyMyDesign);
    Page<MyCollectionResponseDTO.AcquiredStampResultDTO>  findStampList(String memberEmail, int page, int pageSize, boolean onlyMyDesign);
}
