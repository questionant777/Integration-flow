package ru.otus.spring.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.domain.Bug;

import static ru.otus.spring.integration.config.ChannelConstants.*;

@Configuration
public class IntegrationConfig {

    @Bean
    public IntegrationFlow thriveFlow() {
        return IntegrationFlows.from(START_CHANNEL)
                .publishSubscribeChannel(subscription ->
                        subscription
                                .subscribe(subflow -> subflow
                                        .<Bug> filter(Bug::isActionPartOfDayIsDay)
                                        .channel(ACTION_PART_OF_DAY_IS_DAY_CHANNEL))
                                .subscribe(subflow -> subflow
                                        .<Bug> filter(Bug::isActionPartOfDayIsNight)
                                        .channel(ACTION_PART_OF_DAY_IS_NIGHT_CHANNEL))
                )
                //трансформация в кокон
                .<Bug, Bug>transform(b -> new Bug(b.getName(), b.getStage() + 1, b.getActionPartOfDay()))
                .publishSubscribeChannel(subscription ->
                        subscription
                                .subscribe(subflow -> subflow
                                        .<Bug> filter(Bug::isActionPartOfDayIsDay)
                                        .channel(ACTION_PART_OF_DAY_IS_DAY_CHANNEL))
                                .subscribe(subflow -> subflow
                                        .<Bug> filter(Bug::isActionPartOfDayIsNight)
                                        .channel(ACTION_PART_OF_DAY_IS_NIGHT_CHANNEL))
                                )
                //трансформация в бабочку
                .<Bug, Bug>transform(b -> new Bug(b.getName(), b.getStage() + 1, b.getActionPartOfDay()))
                .publishSubscribeChannel(subscription ->
                        subscription
                                .subscribe(subflow -> subflow
                                        .<Bug> filter(Bug::isActionPartOfDayIsDay)
                                        .channel(ACTION_PART_OF_DAY_IS_DAY_CHANNEL))
                                .subscribe(subflow -> subflow
                                        .<Bug> filter(Bug::isActionPartOfDayIsNight)
                                        .channel(ACTION_PART_OF_DAY_IS_NIGHT_CHANNEL))
                                )
                .channel(THRIVE_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow subFlowDay() {
        return IntegrationFlows.from(ACTION_PART_OF_DAY_IS_DAY_CHANNEL)
                .handle("actionPartOfDayIsDayImpl", "showWhatDo")
                .get();
    }

    @Bean
    public IntegrationFlow subFlowNight() {
        return IntegrationFlows.from(ACTION_PART_OF_DAY_IS_NIGHT_CHANNEL)
                .handle("actionPartOfDayIsNightImpl", "showWhatDo")
                .get();
    }

    @Bean
    public QueueChannel startChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel thriveChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean (name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller () {
        return Pollers.fixedRate(70).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public QueueChannel actionPartOfDayIsDay() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel actionPartOfDayIsNight() {
        return MessageChannels.queue(10).get();
    }

}
