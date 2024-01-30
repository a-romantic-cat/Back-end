package aromanticcat.umcproject.converter;

import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanLetterboxRequestDTO;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanLetterBoxResponseDTO;

public class NangmanLetterBoxConverter {

    public static NangmanLetterBoxResponseDTO.SendLetterResultDTO toWriteLetterResultDTO(NangmanLetter nangmanLetter) {

        return NangmanLetterBoxResponseDTO.SendLetterResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .senderNickname(nangmanLetter.getSenderNickname())
                .createdAt(nangmanLetter.getCreatedAt())
                .build();
    }

    public static NangmanLetter toNangmanLetter(NangmanLetterboxRequestDTO.SendLetterDTO request, Member member) {

        return NangmanLetter.builder()
                .isPublic(request.getIsPublic())
                .content(request.getContent())
                .senderNickname(request.getSenderRandomNickname())
                .member(member)
                .build();
    }

    public static NangmanLetterBoxResponseDTO.PreviewLetterResultDTO toPreviewLetterResultDTO(NangmanLetter nangmanLetter) {
        return NangmanLetterBoxResponseDTO.PreviewLetterResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .preview(getPreviewText(nangmanLetter.getContent()))
                .createdAt(nangmanLetter.getCreatedAt())
                .build();
    }

    private static String getPreviewText(String content) {
        // 답장 내용을 40자로 제한
        return content.length() <= 40 ? content : content.substring(0, 40) + "...";

    }

    public static NangmanLetterBoxResponseDTO.SelectedLetterResultDTO toSelectedLetterResultDTO(NangmanLetter nangmanLetter) {
        return NangmanLetterBoxResponseDTO.SelectedLetterResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .nangmanLetterContent(nangmanLetter.getContent())
                .senderNickname(nangmanLetter.getSenderNickname())
                .build();
    }

    public static NangmanReply toNangmanReply(NangmanLetterboxRequestDTO.SendReplyDTO request, NangmanLetter nangmanLetter, Member member) {
        return NangmanReply.builder()
                .content(request.getReplyContent())
                .replySenderNickname(request.getReplySenderNickname())
                .nangmanLetter(nangmanLetter)
                .member(member)
                .build();

    }

    public static NangmanLetterBoxResponseDTO.SendReplyResultDTO toWriteReplyResultDTO(NangmanReply nangmanReply) {
        return NangmanLetterBoxResponseDTO.SendReplyResultDTO.builder()
                .nangmanReplyId(nangmanReply.getId())
                .nangmanLetterId(nangmanReply.getNangmanLetter().getId())
                .replySenderNickname(nangmanReply.getReplySenderNickname())
                .createdAt(nangmanReply.getCreatedAt())
                .build();
    }
}
