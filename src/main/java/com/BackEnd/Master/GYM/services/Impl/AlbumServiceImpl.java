package com.BackEnd.Master.GYM.services.Impl;

import com.BackEnd.Master.GYM.Exceptions.EntityNotFoundException;
import com.BackEnd.Master.GYM.Exceptions.InvalidEntityException;
import com.BackEnd.Master.GYM.entity.Album;
import com.BackEnd.Master.GYM.repository.AlbumRepo;
import com.BackEnd.Master.GYM.services.AlbumService;
import lombok.RequiredArgsConstructor;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepo albumRepo;

    @Override
    public Album findById(Long id) {
        return albumRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found with ID: " + id));
    }

    @Override
    public List<Album> findAll() {
        return albumRepo.findAll();
    }

        @Override
    public Optional<Album> findByName(String albumName) {
        return albumRepo.findByName(albumName);

    }

    @Override
    public Album insert(Album entity) {
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new InvalidEntityException("Album name cannot be empty");
        }
        return albumRepo.save(entity);
    }

    @Override
    public Album update(Album entity) {
        Album currentAlbum = albumRepo.findById(entity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Album not found with ID: " + entity.getId()));
        
        currentAlbum.setName(entity.getName());
        currentAlbum.setDescription(entity.getDescription());
        
        return albumRepo.save(currentAlbum);
    }

    @Override
    public void deleteById(Long id) {
        albumRepo.deleteById(id);
    }
}