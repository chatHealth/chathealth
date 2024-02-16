package chathealth.chathealth.controller.board;

import chathealth.chathealth.util.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardImageController {

    private final ImageUpload imageUpload;
    @PostMapping("/upload")
    public String uploadEditorImage(@RequestParam final MultipartFile image) {

        return imageUpload.uploadImage(image, "board");
    }

    @GetMapping(value = "/print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] printEditorImage(@RequestParam final String filename) {
        return imageUpload.getImage(filename, "board");
    }
}