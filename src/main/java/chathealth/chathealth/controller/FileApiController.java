package chathealth.chathealth.controller;

import chathealth.chathealth.exception.NotExistFile;
import chathealth.chathealth.exception.NotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post-img")
@Slf4j
public class FileApiController {

    @Value("${file.path}")
    private String pathValue;

    private final String domain = File.separator + "post" + File.separator;

    @PostMapping("/upload")
    public String uploadEditorImage(@RequestParam final MultipartFile image) {

        String uploadDir = Paths.get(pathValue, domain).toString();
        if (image.isEmpty()) {
            throw new NotExistFile();
        }

        String orgFilename = image.getOriginalFilename();                                         // 원본 파일명
        String uploadMon = "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")) + "/";        // 업로드 월 (yyyyMM
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String saveFilename = getFilename(orgFilename, uploadMon, uuid);
        String fileFullPath = Paths.get(uploadDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로

        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
        File dir = new File(uploadDir+uploadMon);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new NotPermitted("디렉터리 생성에 실패했습니다.");
            }
        }

        try {
            // 파일 저장 (write to disk)
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            return saveFilename;
        } catch (IOException e) {
            throw new NotExistFile("파일 크기는 10MB를 넘을 수 없습니다.");
        }
    }

    @GetMapping(value = "/print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] printEditorImage(@RequestParam final String filename) {
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

    private static String getFilename(String orgFilename, String uploadMon, String uuid) {
        String extension = "";

        if (orgFilename != null && !orgFilename.isEmpty()) {
            int lastIndexOfDot = orgFilename.lastIndexOf(".");
            if (lastIndexOfDot >= 0) {
                extension = orgFilename.substring(lastIndexOfDot + 1);
            } else {
                // 확장자가 없는 경우
                throw new NotExistFile("파일 확장자가 없습니다.");
            }
        } else {
            // 파일 이름이 null이거나 비어 있는 경우
            throw new NotExistFile("파일 이름이 없습니다.");
        }

        return uploadMon + uuid + "." + extension;
    }

    @PostMapping("/uploadMain")
    public String uploadMainImage(@RequestParam final MultipartFile mainImg) {
        String uploadDir = Paths.get(pathValue, domain).toString();
        if (mainImg.isEmpty()) {
            throw new NotExistFile();
        }

        String orgFilename = mainImg.getOriginalFilename();                                         // 원본 파일명
        String uploadMon = "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")) + "/";        // 업로드 월 (yyyyMM
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String saveFilename = getFilename(orgFilename, uploadMon, uuid);
        String fileFullPath = Paths.get(uploadDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로
        String retrunName = Paths.get(domain,saveFilename).toString();

        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
        File dir = new File(uploadDir+uploadMon);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new NotPermitted("디렉터리 생성에 실패했습니다.");
            }
        }

        try {
            // 파일 저장 (write to disk)
            File uploadFile = new File(fileFullPath);
            mainImg.transferTo(uploadFile);
            return retrunName;

        } catch (IOException e) {
            throw new NotExistFile("파일 크기는 10MB를 넘을 수 없습니다.");
        }
    }

    @PostMapping("/uploadServe")
    public List<String> uploadServeImage(@RequestParam("serveImg") final List<MultipartFile> serveImg) {
        log.info("serveImg========={}",serveImg);
        List<String> imgArr=new ArrayList<>();

        for(int i=0;i<serveImg.size();i++) {
            MultipartFile imgg=serveImg.get(i);
        String uploadDir = Paths.get(pathValue, domain).toString();
        if (serveImg.isEmpty()) {
            throw new NotExistFile();
        }

        String orgFilename = imgg.getOriginalFilename();                                         // 원본 파일명
        String uploadMon = "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")) + "/";        // 업로드 월 (yyyyMM
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String saveFilename = getFilename(orgFilename, uploadMon, uuid);
        String fileFullPath = Paths.get(uploadDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로
        String retrunName = Paths.get(domain,saveFilename).toString();

        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
        File dir = new File(uploadDir+uploadMon);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new NotPermitted("디렉터리 생성에 실패했습니다.");
            }
        }

        try {
            // 파일 저장 (write to disk)
            File uploadFile = new File(fileFullPath);
            imgg.transferTo(uploadFile);
            imgArr.add(retrunName);

        } catch (IOException e) {
            throw new NotExistFile("파일 크기는 10MB를 넘을 수 없습니다.");
        }
        }
        return imgArr;
    }

}