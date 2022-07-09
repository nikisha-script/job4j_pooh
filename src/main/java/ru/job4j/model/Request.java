package ru.job4j.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Request {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public static Request of(String content) {
        String[] detailRequest = content.split(System.lineSeparator());
        String[] firstLine = detailRequest[0].split("/");
        String type = firstLine[0].trim();
        String poohMode = firstLine[1];
        String source = firstLine[2].trim().split(" ")[0];
        String param = "POST".equals(type) ? detailRequest[detailRequest.length - 1] : firstLine.length > 4 ? firstLine[3].trim().split(" ")[0] : "";
        return new Request(type, poohMode, source, param);
    }


}
