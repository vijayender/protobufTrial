package com.kvijayender;
import com.kvijayender.logp.*;

/**
 * Created by vijayender on 2/24/16.
 */
public class Trial {
    public static void main(String[] args) {
        System.out.println("Hello world");
        for(String s : args) {
            System.out.println(s);
        }
        LogP.Log log = LogP.Log.newBuilder().setId(0).setMessage("Hello world").build();
        System.out.println("--------");
        System.out.printf(log.toString());
        System.out.println("--------");
    }
}
