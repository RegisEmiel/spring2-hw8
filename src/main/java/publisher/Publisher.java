package publisher;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Publisher {
    private final static String EXCHANGER_NAME = "blog_exchanger";

    public static void main(String[] args){
        System.out.println("publisher.Publisher");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);

            while (true) {
                System.out.println("Введите тему и содержание: ");
                Scanner sc = new Scanner(System.in);
                String topic = sc.nextLine();
                String[] parts = topic.split(" ", 2);

                String theme = parts[0];
                String body = parts[1];

                if (!parts[0].trim().isEmpty()) {
                    channel.basicPublish(EXCHANGER_NAME, theme, null, body.getBytes());
                    System.out.println("Статья опубликована");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
