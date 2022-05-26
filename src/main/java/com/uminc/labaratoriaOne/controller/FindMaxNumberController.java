package com.uminc.labaratoriaOne.controller;

import com.uminc.labaratoriaOne.cache.CacheNumbers;
import com.uminc.labaratoriaOne.counter.CounterService;
import com.uminc.labaratoriaOne.entity.ApiError;
import com.uminc.labaratoriaOne.entity.InputParams;
import com.uminc.labaratoriaOne.entity.ResultDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
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
        if(hashMap.findByKeyInHashMap(inputParams)) {
            return hashMap.getResult(inputParams);
        }
        else {
            ResultDto resultDto = new ResultDto(Math.max(Math.max(first, second), third));
            hashMap.addToMap(inputParams, resultDto);
            logger.info("Successfully request find max number");
            return resultDto;
        }
    }

    @PostMapping("/findmaxnumber")
    public ResponseEntity<?> calculateBulk(@Valid @RequestBody List<InputParams> bodyList) {
        List<ResultDto> resultList = new LinkedList<>();
        bodyList.forEach((currentElem)->{
            resultList.add(new ResultDto(Math.max(Math.max(currentElem.getFirst(), currentElem.getSecond()), currentElem.getThird())));
        });

        logger.info("Successfully postMapping");

        double averageResult = 0;
        if(!resultList.isEmpty()){
            averageResult = resultList.stream().mapToInt(ResultDto::getMaxValue).average().getAsDouble();
        }
        int maxResult = 0;
        if(!resultList.isEmpty()){
            maxResult = resultList.stream().mapToInt(ResultDto::getMaxValue).max().getAsInt();
        }
        int minResult = 0;
        if(!resultList.isEmpty()){
            minResult = resultList.stream().mapToInt(ResultDto::getMaxValue).min().getAsInt();
        }
        return new ResponseEntity<>(resultList + "\nAverage: " + averageResult +
                "\nMax result: " + maxResult + "\nMin result: " + minResult, HttpStatus.OK);
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
    ResponseEntity<Object> handleErrorEntryParams(MethodArgumentTypeMismatchException e) {
        logger.error("Entry wrong input params");
        return new ResponseEntity<>(new ApiError("Entry wrong input parameters", HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<Object> handleEntryParams(MissingServletRequestParameterException e) {
        logger.error("Missing parameters");
        return new ResponseEntity<>(new ApiError("Missing parameters", HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<Object> handleErrorServer(RuntimeException e) {
        logger.error("This is the server and there is an error");
        return new ResponseEntity<>(new ApiError("Internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value()),HttpStatus.BAD_REQUEST);
    }
}