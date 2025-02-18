package org.example;

import com.rabbitmq.client.*;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();

        // Criar uma fila não durável
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println("[*] Aguardando mensagens. Para sair, pressione CTRL + C");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Recebido '" + mensagem + "'");

            // Extrair o timestamp da mensagem
            String[] parts = mensagem.split("-");
            long timestampEnviado = Long.parseLong(parts[1]);
            long timestampRecebido = System.currentTimeMillis();

            // Calcular a diferença
            long diff = timestampRecebido - timestampEnviado;
            System.out.println("[x] Diferença de tempo: " + diff + " ms");

            // Ack da mensagem
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {});
    }
}
