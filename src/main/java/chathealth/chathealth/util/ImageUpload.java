package chathealth.chathealth.util;

import chathealth.chathealth.exception.NotExistFile;
import chathealth.chathealth.exception.NotPermitted;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@Component
public class ImageUpload {

    @Value("${file.path}")
    private String pathValue;

    public String uploadImage(MultipartFile image, String domain) {

        domain = domain + File.separator;

        String uploadDir = Paths.get(pathValue, domain).toString();
        if (image.isEmpty()) {
            throw new NotExistFile();
        }


        String orgFilename = image.getOriginalFilename();                                         // 원본 파일명
        String uploadMon = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")) + "/";        // 업로드 월 (yyyyMM
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String saveFilename = uploadMon + uuid + "_" + orgFilename;

        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
        File dir = new File(uploadDir, uploadMon);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new NotPermitted("디렉터리 생성에 실패했습니다.");
            }
        }

        try {
            // 파일 저장 (write to disk)
            Path path = Paths.get(uploadDir, saveFilename);
            Files.write(path ,image.getBytes());
            return saveFilename;
        } catch (IOException e) {
            throw new NotExistFile("파일 크기는 10MB를 넘을 수 없습니다.");
        }
    }

    public byte[] getImage(String filename, String domain) {
        String uploadDir = Paths.get(pathValue, domain).toString();

        // 업로드된 파일의 전체 경로
        String fileFullPath = Paths.get(uploadDir, filename).toString();

        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fileFullPath);
        if (!uploadedFile.exists()) {
            throw new NotExistFile();
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            return Files.readAllBytes(uploadedFile.toPath());

        } catch (IOException e) {
            throw new NotExistFile();
        }
    }
}
