package ru.job4j.service;

import ru.job4j.model.Request;
import ru.job4j.model.Resp;
import ru.job4j.util.SimpleHttpApi;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueServiceImpl implements Service {

    private final Map<String, ConcurrentLinkedQueue<String>> queueMap = new ConcurrentHashMap<>();
    private final String post = SimpleHttpApi.POST_METHOD.getApi();
    private final String get = SimpleHttpApi.GET_METHOD.getApi();

    @Override
    public Resp process(Request req) {
        Resp resp = new Resp(SimpleHttpApi.ERROR.getApi(), SimpleHttpApi.RESPONSE_STATUS_500.getApi());
        if (post.equals(req.getHttpRequestType())) {
            queueMap.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            String text = req.getParam();
            queueMap.get(req.getSourceName()).add(text);
            resp = new Resp(text, SimpleHttpApi.RESPONSE_STATUS_200.getApi());
        } else if (get.equals(req.getHttpRequestType()) && !(queueMap.get(req.getSourceName()) == null)) {
            String text = Optional.ofNullable(queueMap.get(req.getSourceName()).poll()).orElse("");
            resp = text.isEmpty() ? resp : new Resp(text, SimpleHttpApi.RESPONSE_STATUS_200.getApi());
        }
        return resp;
    }
}
