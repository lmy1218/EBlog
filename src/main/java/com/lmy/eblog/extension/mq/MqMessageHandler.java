package com.lmy.eblog.extension.mq;


import com.lmy.eblog.config.RabbitConfig;
import com.lmy.eblog.extension.search.service.SearchService;
import io.netty.handler.codec.MessageAggregationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqMessageHandler {

    @Autowired
    SearchService searchService;

    @RabbitListener(queues = RabbitConfig.es_queue)
    @RabbitHandler
    public void handler(PostMqIndexMessage message) {

        try {
            log.info("mq 收到一条消息： {}", message.toString());

            switch (message.getType()) {
                case PostMqIndexMessage.CREATE_OR_UPDATE:
                    searchService.createOrUpdateIndex(message);
                    break;
                case PostMqIndexMessage.REMOVE:
                    searchService.removeIndex(message);
                    break;
                default:
                    log.error("没找到对应的消息类型，请注意！！ --》 {}", message.toString());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageAggregationException("消息消费失败");
        }

    }

}
