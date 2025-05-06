package com.BackEnd.Master.GYM.repository;

import com.BackEnd.Master.GYM.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Long> {
    List<Photo> findByAlbumId(Long albumId);
}