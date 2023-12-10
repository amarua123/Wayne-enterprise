package org.example;
import java.util.*;
import java.text.*;
public class Order {
    int weight;
    String destination, status, order_time;
    Order(String destination, int weight){
        this.destination = destination;
        this.weight = weight;
        this.order_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
        this.status = "PENDING";
    }

    @Override
    public String toString() {
        return "Destination: " + destination +
                ", Weight: " + weight +
                ", Status: " + status +
                ", Ordered @" + order_time;
    }
    public long delay(){
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return sdf.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())).getTime() -
                    sdf.parse(this.order_time).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Order or = new Order("alt", 10);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(or.delay());
    }
}
