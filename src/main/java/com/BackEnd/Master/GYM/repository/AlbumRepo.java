package com.BackEnd.Master.GYM.repository;

import com.BackEnd.Master.GYM.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepo extends JpaRepository<Album, Long> {
    Optional<Album> findByName(String name);
}
