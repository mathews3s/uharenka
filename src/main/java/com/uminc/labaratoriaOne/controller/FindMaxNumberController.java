package com.uminc.labaratoriaOne.controller;

import com.uminc.labaratoriaOne.entity.ResultDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindMaxNumberController {

    @GetMapping("/findmaxnumber")
    public ResultDto findMaxNumber(@RequestParam(value = "first") int first,
                                   @RequestParam(value = "second") int second,
                                   @RequestParam(value = "third") int third) {
        return new ResultDto(Math.max(Math.max(first, second), third));
    }
}
