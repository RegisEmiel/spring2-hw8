package subscriber;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Subscriber {
    private final static String EXCHANGER_NAME = "blog_exchanger";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("subscriber.Subscriber");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Содержание статьи: " + message);
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите первую тему: ");
        String theme = sc.nextLine();
        channel.queueBind(queueName, EXCHANGER_NAME, theme);

        System.out.println("Введите вторую тему: ");
        theme = sc.nextLine();
        channel.queueBind(queueName, EXCHANGER_NAME, theme);

        System.out.println("Ожидайте содержание статьи или нажмите Enter для продолжения.");
        sc.nextLine();

        System.out.println("Введите тему, от которой нужно отписаться:");
        theme = sc.nextLine();
        channel.queueUnbind(queueName, EXCHANGER_NAME, theme);
    }
}
