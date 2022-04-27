package com.uminc.labaratoriaOne.controller;

import com.uminc.labaratoriaOne.cache.CacheNumbers;
import com.uminc.labaratoriaOne.counter.CounterService;
import com.uminc.labaratoriaOne.entity.InputParams;
import com.uminc.labaratoriaOne.entity.ResultDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;


@RestController
public class FindMaxNumberController
{
    private static final Logger logger = LogManager.getLogger(FindMaxNumberController.class);

    @Autowired
    private CacheNumbers hashMap;

    @GetMapping("/findmaxnumber")
    public ResultDto findMaxNumber(@RequestParam(value = "first") int first,
                                   @RequestParam(value = "second") int second,
                                   @RequestParam(value = "third") int third)
    {
        new Thread(CounterService::increment).start();
        InputParams inputParams = new InputParams(first, second, third);
        if(hashMap.findByKeyInHashMap(inputParams))
            return hashMap.getResult(inputParams);
        else
        {
            ResultDto resultDto = new ResultDto(Math.max(Math.max(first, second), third));
            hashMap.addToMap(inputParams, resultDto);
            logger.info("Successfully request find max number");
            return resultDto;
        }
    }

    @GetMapping("/counter")
    public ResponseEntity<Integer> getCounter() {
        return new ResponseEntity<>(CounterService.getCounter(), HttpStatus.OK);
    }

    @GetMapping("/cache")
    public Map<InputParams, ResultDto> getCache(){
        logger.info("Get cache");
        return hashMap.getHashMap();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<String> errorEntryParams(MethodArgumentTypeMismatchException e) {
        logger.error("Entry wrong input params");
        return new ResponseEntity<>("<h1>Error: 400</h1>", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> errorServer(RuntimeException e) {
        logger.error("This is the server and there is an error");
        return new ResponseEntity<>("<h1>Error: 500</h1>", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
