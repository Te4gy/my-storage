package com.storage.mystorage.myControllers;

import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.services.EntityRepos.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
@RequestMapping("/new")
public class CreationController {

    final StorageService storageService;

    final RestTemplate restTemplate;

    @PostMapping("/storage")
    public Storage newStorage(@RequestBody Storage request) {
        Storage storage = new Storage();
        storage.setName(request.getName());
        storageService.saveStorage(storage);
        return storage;
    }
}
