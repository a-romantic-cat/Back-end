package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.MyDesignService;
import aromanticcat.umcproject.web.dto.MyDesignRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myDesign")
public class MyDesignController {
    private final MyDesignService myDesignService;

    @PostMapping("/letter-paper")
    @ApiOperation(value = "마이디자인 편지지 생성")
    public ApiResponse<Long> createMyLetterPaper(@RequestPart("myDesignRequest") MyDesignRequest myDesignRequest, @RequestPart(value = "img")MultipartFile file) throws IOException {
        if (file != null) {
            try {
                return ApiResponse.onSuccess(myDesignService.createMyLetterPaper(myDesignRequest, file));
            } catch (Exception e) {
                return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "file - exist, error", null); //바꾸기
            }
        }
        return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "file dosen't exist", null); //바꾸기
    }

}

