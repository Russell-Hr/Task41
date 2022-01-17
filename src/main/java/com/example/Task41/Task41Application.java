package com.example.Task41;

import com.example.Task41.cfg.FileReader;
import com.example.Task41.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import java.io.Reader;
import java.util.List;

@SpringBootApplication
//@EnableIntegration
//@IntegrationComponentScan
public class Task41Application {
    @Autowired
    FileReader fileReader;

    @Bean
    DirectChannel outputChannel() {
        return new DirectChannel();
    }

    @MessagingGateway
    public interface I {
        @Gateway(requestChannel = "orderFlow.input")
        void process(Order order);
    }

    // канал DirectChannel с именем animalFlow.input создается автоматически
    @Bean
    public IntegrationFlow orderFlow() {
        return flow -> flow.handle("aService", "process")
//				.handle("bService", "process")
//				.handle("cService", "process")
                .channel("outputChannel");
    }

    public static void main(String[] args) {
        List readers = fileReader.reader();
        for (Reader r : readers) {

        }
        ConfigurableApplicationContext ctx = SpringApplication.run(Task41Application.class, args);
        DirectChannel outputChannel = ctx.getBean("outputChannel", DirectChannel.class);
        // обработчик внутри subscribe выполнится как только закончится выполнение flow
        outputChannel.subscribe(System.out::println);

        // запускаем выполнение flow
        ctx.getBean(I.class).process(new Order());

        // можно было запустить flow отправкой сообщения во входной канал input:
        // MessageChannel inputChannel = ctx.getBean("animalFlow.input", MessageChannel.class);
        // inputChannel.send(MessageBuilder.withPayload(new Animal("cat")).build());
        ctx.close();
    }
}
