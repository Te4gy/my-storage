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
    public ResponseEntity<?> newStorage(@RequestBody Storage request) {
        Storage storage = new Storage();
        storage.setName(request.getName());
        storageService.saveStorage(storage);

        String url = "http://localhost:8080/new/respond";

        Storage response = restTemplate.postForObject(url, storage, Storage.class);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/respond")
    public ResponseEntity<?> respond(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
