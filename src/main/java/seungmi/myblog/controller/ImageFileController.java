package seungmi.myblog.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class ImageFileController {

    //썸머노트의 이미지 인코딩 base64의 길이가 너무 길어서 이미지의 경우 외부 경로에 파일을 저장시키고 외부경로 링크로 DB에 저장함
    @PostMapping("/uploadSummernoteImageFile")
    @ResponseBody
    public HashMap<String, String> uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        HashMap<String, String> map = new HashMap<>();


        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장

            map.put("url", "/summernoteImage/"+savedFileName);
            map.put("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            map.put("responseCode", "error");
            e.printStackTrace();
        }

        return map;
    }
}
