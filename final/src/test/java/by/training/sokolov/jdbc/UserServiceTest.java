package by.training.sokolov.jdbc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceTest {

    private Lock connectionLock = new ReentrantLock();

}
