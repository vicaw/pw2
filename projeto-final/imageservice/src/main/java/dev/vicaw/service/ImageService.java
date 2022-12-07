package dev.vicaw.service;

import dev.vicaw.model.Image;
import dev.vicaw.model.MultipartBody;

public interface ImageService {
    public Image save(MultipartBody body);

    public Image findByFileName(String fileName);

    public Image findByUuid(String uuid);

    public Image findByArticleId(Long articleId);

    public Image findByArticleIdAndScale(Long articleId, int w, int h);

    public Image findByFileNameAndScale(String name, int w, int h);

    public Image update(MultipartBody body);
}
