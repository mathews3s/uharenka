package com.uminc.labaratoriaOne.cache;

import com.uminc.labaratoriaOne.controller.FindMaxNumberController;
import com.uminc.labaratoriaOne.entity.InputParams;
import com.uminc.labaratoriaOne.entity.ResultDto;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CacheNumbers {
    private final Map<InputParams, ResultDto> hashMap = new LinkedHashMap<>();

    public boolean findByKeyInHashMap(InputParams key) {
        return hashMap.containsKey(key);
    }

    public void addToMap(InputParams key, ResultDto result) {
        hashMap.put(key, result);
    }

    public ResultDto getResult(InputParams key){
        return hashMap.get(key);
    }

    public Map<InputParams, ResultDto> getHashMap() {
        return hashMap;
    }
}
