package aromanticcat.umcproject.S3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class S3Service {
    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private String dir1 = "/letter-paper";
    private String dir2 = "/stamp";
    private String defaultUrl = "https://romantic-cat-bucket.s3.ap-northeast-2.amazonaws.com";

    public String uploadFile1(MultipartFile file) throws IOException {

        String bucketDir = bucketName + dir1;
        String dirUrl = defaultUrl + dir1 + "/";
        String fileName = generateFileName(file);

        amazonS3.putObject(bucketDir, fileName, file.getInputStream(), getObjectMetadata(file));
        return dirUrl + fileName;

    }

    public String uploadFile2(MultipartFile file) throws IOException {

        String bucketDir = bucketName + dir2;
        String dirUrl = defaultUrl + dir2 + "/";
        String fileName = generateFileName(file);

        amazonS3.putObject(bucketDir, fileName, file.getInputStream(), getObjectMetadata(file));
        return dirUrl + fileName;
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }
}
