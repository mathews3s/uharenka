package com.uminc.labaratoriaOne;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindMaxNumber {
    @GetMapping("/findmaxnumber")
    public int findmaxnumber(@RequestParam(value = "first") int first,@RequestParam(value = "second") int second,@RequestParam(value = "third") int third)
    {
        return Math.max(Math.max(first, second), third);
    }

}
