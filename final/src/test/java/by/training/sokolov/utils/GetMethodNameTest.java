package by.training.sokolov.utils;

import org.junit.jupiter.api.Test;

public class GetMethodNameTest {
/*
todo удалить это
 */

    private final static String getName(){
        return new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
    }

    @Test
    void go(){
        System.out.println(getName());
    }
}
