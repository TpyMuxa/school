package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    private final int port;

    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    public InfoService(@Value("${server.port}") int port) {
        this.port = port;
    }

    public int getPort() {
        logger.info("Was invoked method getPort class InfoService");
        return port;
    }
}
