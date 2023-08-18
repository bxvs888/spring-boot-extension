package com.livk.disruptor.event;

import com.livk.autoconfigure.disruptor.annotation.DisruptorEvent;
import lombok.Builder;
import lombok.Data;

/**
 * @author livk
 */
@Data
@Builder
@DisruptorEvent("messageDisruptorAuto")
public class MessageModel {
    private String message;
}