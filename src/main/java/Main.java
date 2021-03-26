import client.OrderService;
import client.ParkingService;
import client.WebSocketConnection;
import objects.Constants;
import objects.Order;
import objects.Parking;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ParkingService parkingService = new ParkingService();
        OrderService orderService = new OrderService();
        WebSocketConnection connection = new WebSocketConnection(Constants.LOCALHOST, parkingService, orderService);
        connection.init();
        while (parkingService.getParkingList().size() < 1) {
            Thread.sleep(1000);
        }
        List<Parking> parkingList = parkingService.getParkingList();
        Random random = new Random();
        String paymentInfo = "paymentInfo";
        for (int i = 0; i < 2; i++) {
            int parkingId = random.nextInt(parkingList.size());
            parkingId = Math.toIntExact(parkingList.get(parkingId).getId());
            Long start = new Date().getTime() + random.nextInt(3600 * 1000 * 8);
            Long finish = start + 600 * 1000 * (random.nextInt(24) + 1);
            String carNumber = "car" + random.nextInt(1000);
            Order order = new Order(-new Date().getTime(), (long)parkingId, carNumber, start, finish, paymentInfo);
            connection.sendOrder(order);
            Thread.sleep(10000);
        }
    }
}
