package subscriber;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Subscriber {
    private final static String QUEUE_NAME = "blog_exchanger";
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("subscriber.Subscriber");
    }
}
