package ru.job4j.server;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.model.Request;
import ru.job4j.model.Resp;
import ru.job4j.service.QueueServiceImpl;
import ru.job4j.service.Service;
import ru.job4j.service.TopicServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class PoohServer {

    private final Map<String, Service> mapService = new HashMap<>();
    private final int poolThreads = Runtime.getRuntime().availableProcessors();
    private final String nextLine = System.lineSeparator();
    private final ExecutorService pool = Executors.newFixedThreadPool(poolThreads);

    public void init() {
        mapService.put("queue", new QueueServiceImpl());
        mapService.put("topic", new TopicServiceImpl());
        try (ServerSocket serverSocket = new ServerSocket(9000)) {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                log.info("connected to the server");
                pool.submit(() -> {
                    try (OutputStream out = socket.getOutputStream();
                         InputStream inputStream = socket.getInputStream()) {
                        byte[] buff = new byte[1_000_000];
                        int total = inputStream.read(buff);
                        String text = new String(Arrays.copyOfRange(buff, 0, total), StandardCharsets.UTF_8);
                        Request req = Request.of(text);
                        Resp resp = mapService.get(req.getPoohMode()).process(req);
                        out.write(("HTTP/1.1 " + resp.getStatus() + " OK" + nextLine + nextLine).getBytes());
                        out.write(resp.getText().getBytes());
                    } catch (IOException e) {
                        log.error("IOException: something with output or input", e);
                    }
                });
            }
        } catch (IOException e) {
            log.error("IOException: something with server", e);
        }
    }

}
