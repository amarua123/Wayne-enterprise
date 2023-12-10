package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Customer implements Runnable {
    BlockingQueue<Order> orderForAtlanta, orderForGotham;
    Summery summery;
    Random randomWeight = new Random();
    Customer(BlockingQueue<Order> orderForAtlanta, BlockingQueue<Order> orderForGotham, Summery summery){
        this.orderForAtlanta = orderForAtlanta;
        this.orderForGotham = orderForGotham;
        this.summery = summery;
    }
    private Order makeOrder(){
        return new Order(RandomLocation.getLocation(), 1+randomWeight.nextInt(10));
    }
    @Override
    public void run() {
        while(!summery.isTargetDone()){

//            System.out.println("Customer Thread");
            Order newOrder = makeOrder();
            if(newOrder.destination.equals("Atlanta")){
                orderForAtlanta.add(newOrder);
            }else{
                orderForGotham.add(newOrder);
            }
        }
    }
}
