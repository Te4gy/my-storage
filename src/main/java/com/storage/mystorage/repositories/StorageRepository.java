package com.storage.mystorage.repositories;

import com.storage.mystorage.entities.Storage;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    //todo ?? аннотация противоречит наличию Optional
    @NonNull
    Optional<Storage> findById(Long id);

}
