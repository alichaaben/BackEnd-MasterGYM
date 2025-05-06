package com.BackEnd.Master.GYM.services;

import com.BackEnd.Master.GYM.entity.Album;
import java.util.List;
import java.util.Optional;

public interface AlbumService {
    Album findById(Long id);

    List<Album> findAll();

    Optional<Album> findByName(String name);

    Album insert(Album entity);

    Album update(Album entity);

    void deleteById(Long id);
}