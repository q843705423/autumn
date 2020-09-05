package io.github.q843705423.autumn.file;


import org.springframework.core.io.InputStreamResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class CommonInputStreamResource extends InputStreamResource {
    private String filePath;
    private InputStream inputStream;


    public CommonInputStreamResource(MultipartFile multipartFile) throws IOException {
        super(multipartFile.getInputStream());

        this.inputStream = multipartFile.getInputStream();
        String originalFilename = multipartFile.getOriginalFilename();
        if (!StringUtils.isEmpty(originalFilename)) {
            this.filePath = originalFilename;
        } else {
            this.filePath = multipartFile.getName();
        }
    }

    /**
     * 覆写父类方法
     * 如果不重写这个方法，并且文件有一定大小，那么服务端会出现异常
     * {@code The multi-part request contained parameter data (excluding uploaded files) that exceeded}
     *
     * @return
     */
    @Override
    public String getFilename() {
        return filePath;
    }

    @Override
    public long contentLength() throws IOException {
        return inputStream.available();
    }
}
