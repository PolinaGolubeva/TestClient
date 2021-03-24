import client.WebSocketConnection;
import objects.Constants;
import objects.Order;
import objects.Parking;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebSocketConnection connection = new WebSocketConnection(Constants.LOCALHOST);
        connection.init();
        while (connection.getParkingList() == null) {
            Thread.sleep(1000);
        }
        List<Parking> parkingList = connection.getParkingList();
        Random random = new Random();
        String paymentInfo = "paymentInfo";
        for (int i = 0; i < 100; i++) {
            int parkingId = random.nextInt(parkingList.size());
            parkingId = Math.toIntExact(parkingList.get(parkingId).getId());
            Long start = new Date().getTime() + random.nextInt(3600 * 1000 * 8);
            Long finish = start + 600 * 1000 * random.nextInt(24);
            String carNumber = "car" + random.nextInt(1000);
            Order order = new Order(null, (long)parkingId, carNumber, start, finish, paymentInfo);
            connection.sendOrder(order);
            Thread.sleep(10000);
        }
    }
}
