package com.fsd01.recipes.controller;

import java.io.IOException;
import java.util.stream.Stream;

import com.fsd01.recipes.model.Image;
import com.fsd01.recipes.model.Recipe;
import com.fsd01.recipes.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class ImageStorageService {

    @Autowired
    private ImageRepository imageRepo;

    public Image store(MultipartFile file, Recipe recipe) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println("fileName set");
        Image fileDB = new Image(fileName, file.getContentType(), file.getBytes(), recipe);
        System.out.println("Image object created");

        imageRepo.save(fileDB);
        return fileDB;
    }

    @Transactional
    public Image getFile(String id) {
        return imageRepo.findById(id).get();
    }

    public Stream<Image> getAllFiles() {
        return imageRepo.findAll().stream();
    }
}