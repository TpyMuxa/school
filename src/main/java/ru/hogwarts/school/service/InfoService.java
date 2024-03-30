package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class InfoService {

    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    private final int port;

    public InfoService(@Value("${server.port}") int port) {
        this.port = port;
    }

    public int getPort() {
        logger.info("Was invoked method getPort");
        return port;
    }

    public void step4() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("sequential stream");
        long sum = Stream.iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .reduce(0L, Long::sum);
        stopWatch.stop();
        stopWatch.start("stream");
        sum = LongStream.rangeClosed(1, 1_000_000).sum();
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }
}
