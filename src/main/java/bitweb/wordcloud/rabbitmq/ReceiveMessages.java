package bitweb.wordcloud.rabbitmq;

import bitweb.wordcloud.TextDTO;
import bitweb.wordcloud.TextService;
import bitweb.wordcloud.textfileinfo.UploadStatus;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReceiveMessages {
    private final TextService textService;

    public ReceiveMessages(TextService textService) {
        this.textService = textService;
    }


    @Retryable(retryFor = {Exception.class}, backoff = @Backoff(delay = 100))
    @RabbitListener(queues = "${rabbitmq.queue.words}")
    public void receiveMessage(TextDTO textDTO, Message message, Channel channel) {
        textService.processMessage(textDTO);
    }

    @Recover
    public void recover(TextDTO textDTO, Message message, Channel channel) throws IOException{
        textService.setTextFileInfoStatus(textDTO, UploadStatus.FAILED);
        MessageProperties properties = message.getMessageProperties();
        channel.basicNack(properties.getDeliveryTag(), false, false);
    }
}
