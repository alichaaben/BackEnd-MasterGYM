package com.BackEnd.Master.GYM.controller;

import com.BackEnd.Master.GYM.dto.AlbumDto;
import com.BackEnd.Master.GYM.entity.Album;
import com.BackEnd.Master.GYM.Exceptions.ResourceNotFoundException;
import com.BackEnd.Master.GYM.Mapper.AlbumMapper;
import com.BackEnd.Master.GYM.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
@CrossOrigin("*")
public class AlbumController {
    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> findById(@PathVariable Long id) {
        Album entity = albumService.findById(id);
        return ResponseEntity.ok(albumMapper.map(entity));
    }

    @GetMapping
    public ResponseEntity<List<AlbumDto>> findAll() {
        List<Album> entities = albumService.findAll();
        return ResponseEntity.ok(albumMapper.map(entities));
    }

    @PostMapping()
    public ResponseEntity<AlbumDto> insert(@RequestBody AlbumDto dto) {
        Optional<Album> existingAlbum = albumService.findByName(dto.getName());
        if (existingAlbum.isPresent()) {
            throw new ResourceNotFoundException("Role already exists with name: " + dto.getName());
        }
    
        Album album = albumMapper.unMap(dto);
        Album entity = albumService.insert(album);
        AlbumDto albumdto = albumMapper.map(entity);
    
        return ResponseEntity.ok(albumdto);
    }

    @PutMapping()
    public ResponseEntity<AlbumDto> update(@RequestBody AlbumDto dto) {

        Album currentAlbum = albumService.findById(dto.getId());
        albumMapper.updateEntityFromDto(currentAlbum, dto);
        Album updatedAlbum = albumService.update(currentAlbum);
        AlbumDto responseDto = albumMapper.map(updatedAlbum);

        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        albumService.deleteById(id);
    }
}