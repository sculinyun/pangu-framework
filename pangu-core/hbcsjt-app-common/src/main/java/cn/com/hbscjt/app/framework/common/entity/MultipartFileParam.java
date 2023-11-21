package cn.com.hbscjt.app.framework.common.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author : ly
 * 文件上传对象
 **/
@Data
public class MultipartFileParam implements Serializable {
    //文件流
    private MultipartFile file;
    //文件名称
    private String fileName;
    @NotEmpty(message = "OSS存储桶名称不能为空")
    //桶名称
    private String bucketName;
    @NotEmpty(message = "存储业务代码不能为空")
    //业务code
    private String bizCode;
}
