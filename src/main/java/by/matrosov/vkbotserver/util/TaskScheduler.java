package by.matrosov.vkbotserver.util;

import by.matrosov.vkbotserver.config.ThreadPoolTaskSchedulerConfig;
import by.matrosov.vkbotserver.model.HistoryMessage;
import by.matrosov.vkbotserver.model.Message;
import by.matrosov.vkbotserver.service.HistoryService;
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
import java.util.*;
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
    @Autowired
    private HistoryService historyService;

    @PostConstruct
    public void initTask(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 20);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 0);

        taskScheduler.threadPoolTaskScheduler().scheduleAtFixedRate(
                new StatisticTask(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }

    class StatisticTask implements Runnable {

        @Override
        public void run() {
            logger.info("send statistic...");

            //current timestamp
            Calendar calendar = Calendar.getInstance();
            long timestamp = calendar.getTimeInMillis();

            TransportClient transportClient = HttpTransportClient.getInstance();
            VkApiClient vk = new VkApiClient(transportClient);
            GroupActor actor = new GroupActor(groupId, ACCESS_TOKEN);
            try {
                sendMessageToVkConversation(vk, actor,"Отправляем статистику...");

                List<Integer> owners = new ArrayList<>();
                List<Message> listMessages = messageService.getAll();
                for (Message listMessage : listMessages) {
                    owners.add(listMessage.getMessage_owner());
                }

                Map<Integer, Long> freq = owners.stream()
                        .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

                freq.forEach((k,v) -> {
                    try {
                        sendMessageToVkConversation(vk, actor, "Пользователем " + k + " отправлено " + v + " сообщений");
                        historyService.add(new HistoryMessage(k, v, String.valueOf(timestamp)));
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

    private void sendMessageToVkConversation(VkApiClient vk, GroupActor actor, String text) throws ClientException {
        vk.messages().send(actor).peerId(peerId).message(text).executeAsRaw();
    }
}
