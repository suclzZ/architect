package com.mq.rabbitmq.depth;

public class JustConfig {

    public final static String QUEUE_PC = "q.depth";

    public final static String EXCHANGE_PC = "x.depth";
    /**
     * 没有绑定任何queue
     */
    public final static String EXCHANGE_PC_NOT = "x.depth.not";

    public final static String BINDKING_KEY_PC = "#.depth";
    public final static String ROUTING_KEY_PC = "rk.depth";
}
