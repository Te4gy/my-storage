package com.storage.mystorage.controllers;

import com.storage.mystorage.entities.Storage;
import com.storage.mystorage.services.crud.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/new")
public class CreationController {

    private final StorageService storageService;

    @PostMapping("/storage")
    public Storage newStorage(@RequestBody Storage request) {
        Storage storage = new Storage();
        storage.setName(request.getName());
        storageService.saveStorage(storage);
        return storage;
    }
}
