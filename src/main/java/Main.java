import objects.Order;

import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebSocketConnection connection = new WebSocketConnection("http://localhost:8080/client");
        connection.init();
        Random random = new Random();
        while (true) {
            if (connection.getParkingList() != null) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                int parkingId = random.nextInt(connection.getParkingList().size());
                String carNum = "car" + random.nextInt(100);
                Date start = new Date(new Date().getTime() + random.nextInt(3600 * 1000 * 8) + 3600 * 1000);
                Date finish = new Date(start.getTime() + 3600 * 1000);
                String paymentInfo = "here comes some payment information " + random.nextInt(100);
                Order order = new Order(null, (long) parkingId, carNum, start.getTime(),
                        finish.getTime(), paymentInfo);
                connection.sendOrder(order);
            } else {
                System.out.println("pList is null");
                Thread.sleep(2000);
            }
        }
    }
}
