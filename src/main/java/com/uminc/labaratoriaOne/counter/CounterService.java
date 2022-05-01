package com.uminc.labaratoriaOne.counter;

import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private static int counter;

    public static synchronized void increment() {
        ++counter;
    }

    public static int getCounter(){
        return counter;
    }
}
