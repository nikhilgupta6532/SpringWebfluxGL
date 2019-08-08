package com.springReactive.practice.MapService;

import com.springReactive.practice.model.BaseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
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

    public boolean isAnagrams(String str1,String str2){
        char[] firstString = str1.toCharArray();
        char[] secondString = str2.toCharArray();

        HashMap<Character,Integer> firstMap = new HashMap<>();
        HashMap<Character,Integer> secondMap = new HashMap<>();

        for(int i=0;i<firstString.length;i++) {
            if(firstMap.get(firstString[i])==null) {
                firstMap.put(firstString[i],1);
            } else {
                Integer a = firstMap.get(firstString[i]);
                firstMap.put(firstString[i],++a);
            }
        }

        for(int i=0;i<secondString.length;i++) {
            if(secondMap.get(secondString[i])==null) {
                secondMap.put(secondString[i],1);
            } else {
                Integer b = secondMap.get(secondString[i]);
                secondMap.put(secondString[i],++b);
            }
        }

        if(firstMap.equals(secondMap)) return true;
        else return false;
    }

}
