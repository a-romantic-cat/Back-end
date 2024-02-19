package aromanticcat.umcproject.service.storeService;

import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreService {

    Page<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(String email, int page, int pageSize, String sort);

    List<StoreResponseDTO.StampResultDTO> findStampList(String email, int page, int pageSize, String sort);

    void purchasedLetterPaper(String email, Long letterPaperId);

    void purchasedStamp(String email, Long stampId);

    Long uploadLetterPaper(MultipartFile file, String name, int price) throws IOException;
    Long uploadStamp(MultipartFile file, String name, int price) throws IOException;

    int findUserCoin(String email);


}
