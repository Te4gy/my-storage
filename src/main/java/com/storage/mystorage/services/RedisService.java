package com.storage.mystorage.services;


import com.storage.mystorage.allRepositories.RedisRepository;
import com.storage.mystorage.utils.myDto.answersDto.ProductConnectionDto;
import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RedisService {

    RedisRepository redisRepository;

    public List<ProductConnectionDto> findAllProductConnectionDtoFromCache(){
        return redisRepository.getAllProductConnectionDto().orElseThrow(() -> new NoSuchElementException("No such hash list in cache"));
    }




//    public Optional<ProductConnectionDto> findProductConnectionDtoFromCacheById(Long key){
//        return redisRepository.getProductConnectionDtoById(key);
//
//    }
//
//// не работает
//    public Optional<StorageDto> findStorageFromCacheById(Long key){
//        Optional<ProductConnectionDto> productConnectionDto = redisRepository.getProductConnectionDtoById(key);
//        return Optional.of(productConnectionDto.orElseThrow(RuntimeException::new)
//                .getStorage());
//    }
}
