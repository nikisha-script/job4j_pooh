package ru.job4j.service;

import org.junit.Test;
import ru.job4j.model.Request;
import ru.job4j.model.Resp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class QueueServiceImplTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueServiceImpl queueService = new QueueServiceImpl();
        String paramForPostMethod = "temperature=18";
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Request("POST", "queue", "weather", paramForPostMethod)
        );
        /* Забираем данные из очереди weather. Режим queue */
        Resp result = queueService.process(
                new Request("GET", "queue", "weather", null)
        );
        assertThat(result.getText(), is("temperature=18"));
    }

}