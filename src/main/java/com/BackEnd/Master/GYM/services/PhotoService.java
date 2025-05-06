package com.BackEnd.Master.GYM.services;

import com.BackEnd.Master.GYM.entity.Photo;
import java.util.List;

public interface PhotoService {
    Photo findById(Long id);

    List<Photo> findAll();

    List<Photo> findByAlbumId(Long albumId);

    Photo insert(Photo entity);

    Photo update(Photo entity);

    void deleteById(Long id);
}