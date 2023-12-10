package org.example;
import java.util.*;
import java.util.concurrent.BlockingQueue;

//Each Ship is responsible for a transaction
//of Orders between Gotham and Atlanta
public class Ship implements Runnable{
    int NO_OF_CONTINUES_TRIP = 5;
    int MIN_WEIGHT = 50;
    int MAX_WEIGHT = 300;
    int no_of_trips, total_weight;
    BlockingQueue<Order> orderForAtlanta, orderForGotham;
    List<Order> orders = new ArrayList<>();
    String next_destination = RandomLocation.getLocation();
    Summery summery;
    Ship(BlockingQueue<Order> orderForAtlanta, BlockingQueue<Order> orderForGotham, Summery summery){
        this.orderForAtlanta = orderForAtlanta;
        this.orderForGotham = orderForGotham;
        this.summery = summery;
    }

    @Override
    public void run() {
        while(!summery.isTargetDone()){
//            System.out.println("Ship Thread");

            if(next_destination.equals("Atlanta")){
                try {
                    Order curOrder = orderForAtlanta.take();
                    orders.add(curOrder);
                    total_weight += curOrder.weight;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    Order curOrder = orderForGotham.take();
                    orders.add(curOrder);
                    total_weight += curOrder.weight;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(total_weight >= 50){
//                System.out.println(orders.get(0).delay());
                if(orders.get(0).delay() >= 10000){
                    System.out.println(orders.get(0).delay());
                    for(Order or: orders){
                        or.status = "FAILED";
                        System.out.println(or);
                    }
                    orders.clear();
                    total_weight = 0;
                    summery.loss();
                }else{
                    for(Order or: orders){
                        or.status = "SUCCESS";
                        System.out.println(or);
                    }
                    orders.clear();
                    total_weight = 0;
                    summery.profit();
                    no_of_trips += 1;
                }
            }
            if(no_of_trips == 5){
                try {
                    System.out.println("Ship is under-maintenance");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                no_of_trips = 0;
            }
        }
    }
}
