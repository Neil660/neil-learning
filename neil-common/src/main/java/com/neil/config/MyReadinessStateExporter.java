package com.neil.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/19 16:46
 * @Version 1.0
 */
@Component
@Slf4j
public class MyReadinessStateExporter {
    /*@EventListener
    public void onStateChange(AvailabilityChangeEvent<ReadinessState> event) {
        switch (event.getState()) {
            case ACCEPTING_TRAFFIC:
                log.info("The application is ready to receive traffic.");
                break;
            case REFUSING_TRAFFIC:
                log.info("The application is not willing to receive traffic.");
                break;
        }
    }*/
}
