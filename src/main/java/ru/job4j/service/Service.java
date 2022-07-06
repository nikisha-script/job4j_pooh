package ru.job4j.service;

import ru.job4j.model.Request;
import ru.job4j.model.Resp;

public interface Service {
    Resp process(Request req);
}
