package ru.job4j.service;

import org.junit.Test;
import ru.job4j.model.Request;
import ru.job4j.model.Resp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TopicServiceImplTest {

    @Test
    public void whenTopic() {
        TopicServiceImpl topicService = new TopicServiceImpl();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        /* Режим topic. Подписываемся на топик weather. client407. */
        topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );
        /* Режим topic. Добавляем данные в топик weather. */
        topicService.process(
                new Request("POST", "topic", "weather", paramForPublisher)
        );
        /* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client407. */
        Resp result1 = topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );
        /* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client6565.
        Очередь отсутствует, т.к. еще не был подписан - получит пустую строку */
        Resp result2 = topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.getText(), is("temperature=18"));
        assertThat(result2.getText(), is(""));
    }

}