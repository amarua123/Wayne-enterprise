package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Summery{
    int TARGET;
    int revenue = 0;
    int total_transaction;
    int successful_transaction;
    Summery(int target){
        this.TARGET = target;
    }
    public void profit(){
        synchronized (this){
            revenue += 1000;
            total_transaction += 1;
            successful_transaction += 1;
        }
    }
    public void loss(){
        synchronized (this){
            revenue -= 250;
            total_transaction += 1;
        }
    }
    public boolean isTargetDone(){
        return revenue >= TARGET;
    }
}
public class Main {
    public static void main(String[] args) {
        int NO_OF_CUSTOMER = 7;
        int NO_OF_SHIPPER = 5;
        Summery summery = new Summery(1000000);

        BlockingQueue<Order> orderForAtlanta = new LinkedBlockingDeque<>();
        BlockingQueue<Order> orderForGotham = new LinkedBlockingDeque<>();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < NO_OF_CUSTOMER; i++){
            Thread t = new Thread(new Customer(orderForAtlanta, orderForGotham, summery));
            threads.add(t);
            t.start();
        }
        for(int i = 0; i < NO_OF_SHIPPER; i++){
            Thread t = new Thread(new Ship(orderForAtlanta, orderForGotham, summery));
            threads.add(t);
            t.start();
        }
        for(Thread t: threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Total Orders: " + summery.total_transaction);
        System.out.println("Successful Transaction: " + summery.successful_transaction);
        System.out.println("Revenue: " + summery.revenue);
    }
}