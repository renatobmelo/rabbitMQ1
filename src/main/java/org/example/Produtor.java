package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Produtor {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        String NOME_FILA = "filaHelloWorld";
        try(

                Connection connection = connectionFactory.newConnection();

                Channel channel = connection.createChannel()) {

            channel.queueDeclare(NOME_FILA, false, false, false, null);
            String mensagem = "Hello World!";

            channel.basicPublish("", NOME_FILA, null, mensagem.getBytes());
            System.out.println("Renato enviou: " + mensagem);
        }
    }

}
