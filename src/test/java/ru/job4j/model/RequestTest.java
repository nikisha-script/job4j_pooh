package ru.job4j.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RequestTest {

    @Test
    public void whenQueueModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /queue/weather HTTP/1.1" + ls +
                "Host: localhost:9000" + ls +
                "User-Agent: curl/7.72.0" + ls +
                "Accept: */*" + ls +
                "Content-Length: 14" + ls +
                "Content-Type: application/x-www-form-urlencoded" + ls +
                "" + ls +
                "temperature=18" + ls;
        Request req = Request.of(content);
        assertThat(req.getHttpRequestType(), is("POST"));
        assertThat(req.getPoohMode(), is("queue"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("temperature=18"));
    }

    @Test
    public void whenQueueModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Request req = Request.of(content);
        assertThat(req.getHttpRequestType(), is("GET"));
        assertThat(req.getPoohMode(), is("queue"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is(""));
    }

    @Test
    public void whenTopicModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /topic/weather HTTP/1.1" + ls +
                "Host: localhost:9000" + ls +
                "User-Agent: curl/7.72.0" + ls +
                "Accept: */*" + ls +
                "Content-Length: 14" + ls +
                "Content-Type: application/x-www-form-urlencoded" + ls +
                "" + ls +
                "temperature=18" + ls;
        Request req = Request.of(content);
        assertThat(req.getHttpRequestType(), is("POST"));
        assertThat(req.getPoohMode(), is("topic"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("temperature=18"));
    }

    @Test
    public void whenTopicModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /topic/weather/client407 HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Request req = Request.of(content);
        assertThat(req.getHttpRequestType(), is("GET"));
        assertThat(req.getPoohMode(), is("topic"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("client407"));
    }


}