package com.storage.mystorage.myRepositories;

import com.storage.mystorage.myEntitys.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    public Optional<Storage> findById(Long id);
    public Optional<Storage> findByName (String name);
}
