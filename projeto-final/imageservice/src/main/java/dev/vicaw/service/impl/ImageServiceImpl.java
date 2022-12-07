package dev.vicaw.service.impl;

import javax.inject.Inject;
import javax.transaction.Transactional;

import dev.vicaw.model.Image;
import dev.vicaw.model.MultipartBody;
import dev.vicaw.repository.ImageRepository;
import dev.vicaw.service.ImageService;

import java.io.IOException;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ImageServiceImpl implements ImageService {

    @Inject
    ImageRepository imageRepository;

    @Transactional
    @Override
    public Image save(MultipartBody body) {
        // body.file.getParts().get(0).getMediaType()
        try {

            Image image = new Image();
            image.setData(body.file.readAllBytes());
            image.setName(body.fileName);
            image.setArticle_id(Long.valueOf(body.article_id));
            imageRepository.persist(image);
            return image;

        } catch (IOException e) {
            System.out.println("s " + e.getMessage());
            return new Image();
        }

        // if (image == null)
        // throw new NullPointerException("Image Data NULL");

    }

    @Transactional
    @Override
    public Image update(MultipartBody body) {
        // body.file.getParts().get(0).getMediaType()
        try {

            Optional<Image> imageResult = imageRepository.find("article_id", Long.valueOf(body.article_id))
                    .firstResultOptional();

            Image image = imageResult.get();

            image.setData(body.file.readAllBytes());
            image.setName(body.fileName);

            imageRepository.persist(image);
            return image;

        } catch (IOException e) {
            System.out.println("mee: " + e.getMessage());
            return new Image();
        }

        // if (image == null)
        // throw new NullPointerException("Image Data NULL");

    }

    @Override
    public Image findByFileName(String fileName) {
        Optional<Image> imageResult = imageRepository.findByName(fileName);

        if (imageResult.isEmpty())
            return Image.defaultImage();

        return imageResult.get();
    }

    @Override
    public Image findByUuid(String uuid) {
        return new Image();
    }

    @Override
    public Image findByFileNameAndScale(String name, int w, int h) {
        Image image = findByFileName(name);
        image.scale(w, h);
        return image;
    }

    @Override
    public Image findByArticleId(Long articleId) {
        Optional<Image> imageResult = imageRepository.find("article_id", articleId).firstResultOptional();

        if (imageResult.isEmpty())
            return Image.defaultImage();

        return imageResult.get();
    }

    @Override
    public Image findByArticleIdAndScale(Long articleId, int w, int h) {
        Image image = findByArticleId(articleId);
        image.scale(w, h);
        return image;
    }
}
