package com.fsd01.recipes.repository;

import com.fsd01.recipes.model.Image;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, String> {



}
