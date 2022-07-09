package ru.job4j.service;

import ru.job4j.model.Request;
import ru.job4j.model.Resp;
import ru.job4j.util.SimpleHttpApi;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicServiceImpl implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topicMap
            = new ConcurrentHashMap<>();
    private final String post = SimpleHttpApi.POST_METHOD.getApi();
    private final String get = SimpleHttpApi.GET_METHOD.getApi();

    @Override
    public Resp process(Request req) {
        Resp resp = new Resp(SimpleHttpApi.ERROR.getApi(), SimpleHttpApi.RESPONSE_STATUS_500.getApi());
        if (get.equals(req.getHttpRequestType())) {
            String queue = req.getSourceName();
            topicMap.putIfAbsent(queue, new ConcurrentHashMap<>());
            topicMap.get(queue).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String text = topicMap.get(queue).get(req.getParam()).poll();
            resp = text == null ? resp : new Resp(text, SimpleHttpApi.RESPONSE_STATUS_200.getApi());
        } else if (post.equals(req.getHttpRequestType())) {
            String text = req.getParam();
            topicMap.getOrDefault(req.getSourceName(), new ConcurrentHashMap<>()).forEach((key, value) -> value.add(text));
        }
        return resp;
    }
}
