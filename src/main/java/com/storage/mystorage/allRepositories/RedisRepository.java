package com.storage.mystorage.allRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.mystorage.utils.myDto.answersDto.ProductConnectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisRepository {

    private final JedisPool jedisPool;
    //    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public Long saveProductConnectionDto(ProductConnectionDto productConnectionDto) {
        Long result = null;
        try
                (Jedis jedis = new Jedis("localhost", 6379)) //todo не знаю как не вводить каждый раз вручную
//                (Jedis jedis = jedisPool.getResource() )
        {
                String hashName = "ProductConnectionHash";
                String field = String.valueOf(productConnectionDto.getId());
                String jsonProductConnection = objectMapper.writeValueAsString(productConnectionDto);
                result = jedis.hset(hashName, field, jsonProductConnection);


//            String key = String.valueOf(productConnectionDto.getId());
//            String jsonProductConnection = objectMapper.writeValueAsString(productConnectionDto);
//            result = jedis.set(key, jsonProductConnection);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    public Optional<List<ProductConnectionDto>> getAllProductConnectionDto() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {  //todo сделать так что бы не вводить хост и порт каждый раз
            String hashName = "ProductConnectionHash";

            Map<String, String> mapOfJsonProductConnections = jedis.hgetAll(hashName);
            List<ProductConnectionDto> jsonProductConnectionList = new ArrayList<>();
            for(String entry : mapOfJsonProductConnections.values()){
                ProductConnectionDto newEntry = objectMapper.readValue(entry, ProductConnectionDto.class);
                jsonProductConnectionList.add(newEntry);
            }
            return Optional.of(jsonProductConnectionList);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }



//    public Optional<ProductConnectionDto> getProductConnectionDtoById(Long key) {
//        try (Jedis jedis = new Jedis("localhost", 6379)) {  //todo сделать так что бы не вводить хост и порт каждый раз
//
//            String jsonProductConnection = jedis.get(String.valueOf(key));
//            var productConnection = objectMapper.readValue(jsonProductConnection, ProductConnectionDto.class);
//            return Optional.of(productConnection);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Optional.empty();
//        }
//    }
//    public Optional<StorageDto> getStorageById(Long key){
//        Optional<ProductConnectionDto> productConnectionDto = getProductConnectionById(key);
//        return Optional.of(productConnectionDto.orElseThrow(RuntimeException::new)
//                .getStorage());
//    }
}
