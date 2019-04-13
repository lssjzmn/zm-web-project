package com.lssj.zmn.server.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AsynMsgWorker {

    static Logger logger = LoggerFactory.getLogger(AsynMsgWorker.class);

    private LinkedBlockingQueue<Object> msgQueue = new LinkedBlockingQueue<Object>(200);

    abstract protected String getName();

    private boolean running = true;

    public boolean onMsgReceived(Object recMsg) {

        try {
            if (msgQueue.size() > 200) {
                return true;
            }
            msgQueue.put(recMsg);
            if (msgQueue.size() > 120) {
                logger.info(getName() + " msg queue size: " + msgQueue.size());
            }
        } catch (InterruptedException e) {
            logger.error("put msg queue utils.exception", e);
            return false;
        }
        return true;
    }


    abstract protected void processMsg(Object object);


    @PostConstruct
    public void startWork() {
        final Thread workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("start process msg thread on " + getName());
                while (running) {
                    try {
                        Object recMsg = msgQueue.take();
                        processMsg(recMsg);
                    } catch (InterruptedException e) {
                        logger.error("take msg from queue utils.exception", e);
                    }
                }
                logger.info("start process exit");
            }
        });
        workThread.start();
    }


    @PreDestroy
    public void stopWork() {
        running = false;
    }

}
