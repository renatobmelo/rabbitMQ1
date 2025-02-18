package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


public class NewTask {


    private static final String TASK_QUEUE_NAME = "task_queue";


    public static void main (String [] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory ();
        connectionFactory.setHost ("localhost");

        try (Connection connection = connectionFactory.newConnection ();
             Channel channel = connection.createChannel ()) {
            // não durável
            channel.queueDeclare (TASK_QUEUE_NAME, false, false, false, null);


            // Enviar a mensagem com timestamp
            String mensagem = "NUMERO_MENSAGEM-" + System.currentTimeMillis();

            // Enviar a mensagem sem persistência
            channel.basicPublish("", TASK_QUEUE_NAME, null, mensagem.getBytes("UTF-8"));
            System.out.println ("[x] Renato Melo '" + mensagem + "'");
        }
    }


}
