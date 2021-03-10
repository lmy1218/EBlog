package com.lmy.eblog.provider;
/**
 * @Project bbs
 * @Package com.lmy.bbs.provider
 * @author lmy
 * @date 2020/5/3 19:08
 * @version V1.0
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author lmy
 * @ClassName AliyunProvider
 * @Description 阿里云OSS对象存储
 * @date 2020/5/3 19:08
 **/
@Slf4j
@Service
@EnableAutoConfiguration
public class AliyunProvider {

    @Value("${yun.endpoint}")
    private String endpoint;
    @Value("${yun.accessKeyId}")
    private String accessKeyId;
    @Value("${yun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${yun.bucketName}")
    private String bucketName;

    public String upload(InputStream fileInput, String fileName) {

        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String[] filePaths = fileName.split("\\.");
        String generateFileName = "eblog/";
        if (filePaths.length > 1) {
            generateFileName += UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        ossClient.putObject(bucketName,generateFileName , fileInput);

        // 关闭OSSClient。
        ossClient.shutdown();

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, generateFileName, expiration).toString();
        if (StringUtils.isNotBlank(url)) {
            String[] urls = url.split("\\?");
            url = urls[0] + "?x-oss-process=image/resize,h_200";
        }
        log.info("URL:{}" + url);
        return url;
    }


}
