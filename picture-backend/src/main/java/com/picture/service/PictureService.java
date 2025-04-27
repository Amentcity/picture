package com.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.picture.model.dto.picture.PictureQueryRequest;
import com.picture.model.dto.picture.PictureUploadRequest;
import com.picture.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.picture.model.entity.User;
import com.picture.model.vo.PictureVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Amentcity
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-04-26 01:11:04
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param multipartFile        文件
     * @param pictureUploadRequest 请求图片参数
     * @param loginUser            当前用户
     * @return 图片信息
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser) throws IOException;


    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);
}
