package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import objects.MessageGenerator;
import objects.Order;
import objects.Parking;
import okhttp3.*;

import java.util.ArrayList;
import java.util.List;

public class WebSocketConnection {
    private OkHttpClient client ;
    private Request request;
    private WebSocket webSocket;
    private ClientWebSocketListener listener;
    private List<Parking> parkingList;

     public WebSocketConnection(String url) {
        client = new OkHttpClient();
        request = new Request.Builder().url(url).build();
        listener = new ClientWebSocketListener(this);
        parkingList = null;
    }

    public void init() {
        webSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    public void send(String message) {
         webSocket.send(message);
        System.out.println("Text sent: " + message);
    }

    public void sendMessage(String message) {
         message = MessageGenerator.MESSAGE + message;
         webSocket.send(message);
         System.out.println("Message sent: " + message);
    }

    public void sendError(String error) {
         error = MessageGenerator.ERROR + error;
         webSocket.send(error);
        System.out.println("Error sent: " + error);
    }

    public void sendOrder(Order order) {
         String message = MessageGenerator.SEND_ORDER + order.toString();
         webSocket.send(message);
        System.out.println("Order sent: " + message);
    }

    public void onParkingListGet(List<Parking> parkingList) {
        // place here your code
        // this is an example
         System.out.println("Received all parkings:");
         for (int i = 0; i < parkingList.size(); i++) {
             System.out.println(parkingList.get(i).toString());
         }
         this.parkingList = parkingList;
        System.out.println(this.parkingList.size());
    }

    public void onParkingGet(Parking parking) {
        // place here your code
        // this is an example
        System.out.println("Received parking update:");
        System.out.println(parking.toString());
        for (Parking p : parkingList) {
            if (p.getId().equals(parking.getId())) {
                parkingList.remove(p);
                parkingList.add(parking);
                break;
            }
        }
    }

    public void onMessageGet(String message) {
        // place here your code
        // this is an example
        System.out.println("Received message:");
        System.out.println(message);
    }

    public void onError(String message) {
        // place here your code
        // this is an example
        System.out.println("Received error message");
        System.out.println(message);
    }

    public void close() {
        // place here your code
        // this is an example
         webSocket.close(1000, "");
        System.out.println("Connection closed");
    }

    public void reconnect() {
         // place here your code
    }

    public List<Parking> getParkingList() {
         return this.parkingList;
    }
}
