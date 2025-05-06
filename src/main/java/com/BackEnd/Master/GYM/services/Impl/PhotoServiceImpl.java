package com.BackEnd.Master.GYM.services.Impl;

import com.BackEnd.Master.GYM.Exceptions.EntityNotFoundException;
import com.BackEnd.Master.GYM.Exceptions.InvalidEntityException;
import com.BackEnd.Master.GYM.entity.Photo;
import com.BackEnd.Master.GYM.repository.PhotoRepo;
import com.BackEnd.Master.GYM.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepo photoRepo;

    @Override
    public Photo findById(Long id) {
        return photoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Photo not found with ID: " + id));
    }

    @Override
    public List<Photo> findAll() {
        return photoRepo.findAll();
    }

    @Override
    public List<Photo> findByAlbumId(Long albumId) {
        return photoRepo.findByAlbumId(albumId);
    }

    @Override
    public Photo insert(Photo entity) {
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new InvalidEntityException("Photo name cannot be empty.");
        }
        return photoRepo.save(entity);
    }

    @Override
    public Photo update(Photo entity) {
        Photo currentPhoto = photoRepo.findById(entity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Photo not found with ID: " + entity.getId()));
        
        currentPhoto.setName(entity.getName());
        currentPhoto.setDescription(entity.getDescription());
        currentPhoto.setAlbum(entity.getAlbum());
        
        return photoRepo.save(currentPhoto);
    }

    @Override
    public void deleteById(Long id) {
        photoRepo.deleteById(id);
    }
}