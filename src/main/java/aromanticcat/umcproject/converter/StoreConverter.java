package aromanticcat.umcproject.converter;

import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.entity.LetterPaper;
import aromanticcat.umcproject.entity.Stamp;
import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;

import java.util.List;

public class StoreConverter {


    public static StoreResponseDTO.LetterPaperResultDTO toLetterPaperResultDTO(LetterPaper letterPaper, List<AcquiredItem> acquiredItemList){
        boolean isAcquired = isItemAcquired(letterPaper, acquiredItemList);
        Integer price = isAcquired ? null : letterPaper.getPrice();

        return StoreResponseDTO.LetterPaperResultDTO.builder()
                .letterPaperId(letterPaper.getId())
                .letterPaperName(letterPaper.getName())
                .letterPaperImageUrl(letterPaper.getImage_url())
                .price(price)
                .createdAt(letterPaper.getCreatedAt())
                .build();
    }

    public static StoreResponseDTO.StampResultDTO toStampResultDTO(Stamp stamp, List<AcquiredItem> acquiredItemList){
        boolean isAcquired = isItemAcquired(stamp, acquiredItemList);
        Integer price = isAcquired ? null : stamp.getPrice();

        return StoreResponseDTO.StampResultDTO.builder()
                .stampId(stamp.getId())
                .stampName(stamp.getName())
                .stampImageUrl(stamp.getImage_url())
                .price(price)
                .createdAt(stamp.getCreatedAt())
                .build();
    }

    private static <T> boolean isItemAcquired(T item, List<AcquiredItem> acquiredItemList) {

        if (item instanceof LetterPaper) {
            Long itemId = ((LetterPaper) item).getId();
            return acquiredItemList.stream()
                    .anyMatch(acquiredItem -> acquiredItem.getLetterPaper() != null && acquiredItem.getLetterPaper().getId().equals(itemId));

        } else if (item instanceof Stamp) {
            Long itemId = ((Stamp) item).getId();
            return acquiredItemList.stream()
                    .anyMatch(acquiredItem -> acquiredItem.getStamp() != null && acquiredItem.getStamp().getId().equals(itemId));
        }

        return false;
    }
}
