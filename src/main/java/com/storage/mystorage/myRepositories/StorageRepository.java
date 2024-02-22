package com.storage.mystorage.myRepositories;

import com.storage.mystorage.myEntitys.Storage;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    @NonNull
    Optional<Storage> findById(Long id);

}
