package uz.shukurov.task1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
    private static int mCount = 0;
    static Lock lock = new ReentrantLock();
    private static Future<Integer> future;

    private static int numberOfThreads = 10;
    private static int numberOfFloors = 10;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfFloors; i++) {

            future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {

                   lock.lock();
                    int localCount= 0;
                    for (int y = 0; y < 100; y++) {

                        localCount++;
                        System.out.println(localCount+ " " + Thread.currentThread().getName());
                    }
                    mCount=mCount+localCount;
                    lock.unlock();
                    TimeUnit.MILLISECONDS.sleep(1000);

                    return mCount;
                }

            });
        }



        System.out.println("\nResult: " + future.get());
        executorService.shutdown();
    }


//    static class MyRunnable implements Runnable {
//
//        @Override
//        public void run() {
//            for (int x = 0; x < 10; x++) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int y = 0; y < 100; y++) {
//                            mCount++;
//                            System.out.println(mCount + " " + Thread.currentThread().getName());
//
//                            notify();
//                        }
//                        try {
//                            wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//
//            }
//        }


//    static class MyCallable implements Callable<String>{
//
//        @Override
//        public String call() throws Exception {
//            return "2";
//        }
//    }


}

