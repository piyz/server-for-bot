package by.matrosov.vkbotserver.util;

import by.matrosov.vkbotserver.config.ThreadPoolTaskSchedulerConfig;
import by.matrosov.vkbotserver.model.Message;
import by.matrosov.vkbotserver.service.MessageService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class TaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);
    private static final int groupId = 183794818;
    private static final String ACCESS_TOKEN = "8db7703318f5909bdd18c77016b0de158b033e2c9afda73e2dfbfe371822f4fbea12921f71b76e5db351c";
    private static final int peerId = 2000000003;

    @Autowired
    private ThreadPoolTaskSchedulerConfig taskScheduler;
    @Autowired
    private MessageService messageService;

    @PostConstruct
    public void initTask(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 20);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);

        taskScheduler.threadPoolTaskScheduler().scheduleAtFixedRate(
                new StatisticTask(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }

    class StatisticTask implements Runnable {

        @Override
        public void run() {
            logger.info("send statistic...");
            TransportClient transportClient = HttpTransportClient.getInstance();
            VkApiClient vk = new VkApiClient(transportClient);
            GroupActor actor = new GroupActor(groupId, ACCESS_TOKEN);
            try {
                vk.messages().send(actor).peerId(peerId).message("Отправляем статистику...").executeAsRaw();

                List<Integer> owners = new ArrayList<>();
                List<Message> listMessages = messageService.getAll();
                for (Message listMessage : listMessages) {
                    owners.add(listMessage.getMessage_owner());
                }

                Map<Integer, Long> freq = owners.stream()
                        .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

                freq.forEach((k,v) -> {
                    try {
                        vk.messages().send(actor).peerId(peerId)
                                .message("Пользователем " + k + " отправлено " + v + " сообщений").executeAsRaw();
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                });

            } catch (ClientException e) {
                e.printStackTrace();
            } finally {
                logger.info("clear db...");
                messageService.deleteAll();
            }
        }
    }
}
