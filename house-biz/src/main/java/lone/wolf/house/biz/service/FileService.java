package lone.wolf.house.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/31 12:01
 */
@Service
public class FileService {
    @Value("${file.path}")
    private String filePath;

    public List<String> getImgPath(List<MultipartFile> files) {
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        files.forEach(file -> {
            File localFile = null;
            if (!file.isEmpty()) {
                try {

                    localFile = saveToLocal(file, filePath);
                    String absolutePath = localFile.getAbsolutePath();
                    filePath = filePath.replaceAll("/", "\\\\");
                    String path = StringUtils.substringAfterLast(absolutePath, filePath);//filePath最后出现的位置向后截取
                    paths.add(path);
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }

            }
        });
        return paths;
    }

    public static String getResourcePath() {
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    private File saveToLocal(MultipartFile file, String filePath2) throws IOException {
        File newFile = new File(filePath2 + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        if (!newFile.exists()) {//上级目录不存在就创建上级文件夹
            File parentFile = newFile.getParentFile();
            parentFile.mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(), newFile);
        return newFile;
    }


}
