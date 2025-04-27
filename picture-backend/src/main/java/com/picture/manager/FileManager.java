package com.picture.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.picture.config.CosClientConfig;
import com.picture.exception.BusinessException;
import com.picture.exception.ErrorCode;
import com.picture.exception.ThrowUtils;
import com.picture.model.dto.file.UploadPictureResult;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FileManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private CosManager cosManager;

    /**
     * 上传对象
     *
     * @param multipartFile    文件
     * @param uploadPathPrefix 上传路径前缀
     * @return 上传成功返回的结果对象
     */
    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
        // 校验图片
        validUploadPicture(multipartFile);
        // 图片上传地址
        String uploadFileName = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), RandomUtil.randomString(16), FileUtil.getSuffix(multipartFile.getOriginalFilename()));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFileName);
        File file = null;
        try {
            // 创建临时文件
            file = File.createTempFile(uploadPath, null);
            multipartFile.transferTo(file);
            // 上传图片
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            // 封装返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            BeanUtils.copyProperties(imageInfo, uploadPictureResult);
            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
            uploadPictureResult.setName(FileUtil.mainName(multipartFile.getOriginalFilename()));
            uploadPictureResult.setSize(FileUtil.size(file));
            uploadPictureResult.setScale(NumberUtil.round(imageInfo.getWidth() * 1.0 / imageInfo.getHeight(), 2).doubleValue());
            // 返回可访问的地址
            return uploadPictureResult;
        } catch (IOException e) {
            log.error("图片上传到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            deleteTempFile(file);
        }
    }

    /**
     * 校验图片文件
     *
     * @param multipartFile 文件
     */
    public void validUploadPicture(MultipartFile multipartFile) {
        // 文件是否存在
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "文件不能为空");
        // 1. 检验文件大小
        long fileSize = multipartFile.getSize();
        final long ONE_M = 1024 * 1024L;
        ThrowUtils.throwIf(fileSize > 2 * ONE_M, ErrorCode.PARAMS_ERROR, "文件大小不能超过2MB");
        // 2. 校验文件的后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        // 允许上传的文件后缀
        final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpeg", "jpg", "png", "webp");
        ThrowUtils.throwIf(ALLOW_FORMAT_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "文件类型错误 ");
    }

    /**
     * 删除临时文件
     *
     * @param file 文件
     */
    public void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        boolean delete = file.delete();
        if (!delete) {
            log.error("file delete error, filepath = {}", file.getAbsolutePath());
        }
    }
}
