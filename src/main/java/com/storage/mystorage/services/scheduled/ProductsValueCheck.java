package com.storage.mystorage.services.scheduled;

import com.storage.mystorage.allEntitys.ProductConnection;
import com.storage.mystorage.allRepositories.entitysRepos.ProductConnectionRepository;
import com.storage.mystorage.allRepositories.RedisRepository;
import com.storage.mystorage.services.tools.StorageProductConvertor;
import com.storage.mystorage.utils.myDto.answersDto.ProductConnectionDto;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor
@Service
public class ProductsValueCheck {

    private final ProductConnectionRepository productConnectionRepository;
    private final RedisRepository redisRepository;

    @Transactional
//    @Scheduled(fixedRate = 5000)
    public void cachingValuableProductsConnections(){
        List<ProductConnection> productConnectionList = productConnectionRepository.findAll();
        List<ProductConnection> best3ProductList = productConnectionList.stream()
                .sorted((pc1, pc2) -> Integer.compare(pc2.getProduct().getSellPrice()/pc2.getProduct().getPurchasePrice()
                        ,pc1.getProduct().getSellPrice()/pc1.getProduct().getPurchasePrice()))
                .limit(3)
                .toList();

        for(ProductConnection productConnection: best3ProductList){
            ProductConnectionDto productConnectionDto = StorageProductConvertor.toProductConnectionDto(productConnection);
            redisRepository.saveProductConnectionDto(productConnectionDto);
        }
    }
}
