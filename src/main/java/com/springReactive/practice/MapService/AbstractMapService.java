package com.springReactive.practice.MapService;

import com.springReactive.practice.model.BaseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AbstractMapService {
    private Map<Long, BaseEntity> mapService = new LinkedHashMap<>();

    public Mono<BaseEntity> save(BaseEntity obj) {
        mapService.put(obj.getId(),obj);
        return Mono.just(obj);
    }

    public BaseEntity findById(Long id) {
        return mapService.get(id);
    }

    public Flux<BaseEntity> findAll(){
        return Flux.fromIterable(new HashSet<>(mapService.values()));
    }

    public void deleteById(Long id){
         mapService.remove(id);
    }

    public void delete(BaseEntity obj){
         mapService.entrySet().removeIf(entry->entry.getValue().equals(obj));
    }

    public Flux<Long> getAllIds(){
        Set<Map.Entry<Long, BaseEntity>> entries = mapService.entrySet();
        List<Long> allKeys = new ArrayList<>();
        for(Map.Entry<Long,BaseEntity> t : entries) {
            allKeys.add(t.getKey());
        }
        return Flux.fromIterable(allKeys);
    }
}
