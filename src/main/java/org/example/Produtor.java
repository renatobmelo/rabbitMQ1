package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Produtor {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        try(

                Connection connection = connectionFactory.newConnection();

                Channel channel = connection.createChannel()
        )         {
            String NOME_FILA = "filaHello";
            String mensagem = "Hello!";

            channel.queueDeclare(NOME_FILA, false, false, false, null);
            channel.basicPublish("", NOME_FILA, false, false, null, mensagem.getBytes());



        }
    }

}
