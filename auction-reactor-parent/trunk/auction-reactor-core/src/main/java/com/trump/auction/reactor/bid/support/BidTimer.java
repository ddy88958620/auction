package com.trump.auction.reactor.bid.support;

import com.trump.auction.reactor.util.schedule.CircleCapableTimingWheel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 竞拍延时任务
 *
 * @author Owen
 * @since 2018/1/8
 */
@Slf4j
@Component
public class BidTimer<T extends BidEvent> implements InitializingBean, DisposableBean {

    @Autowired
    private List<BidListener> listeners;

    private ExecutorService taskExecutor;

    @Setter @Getter private CircleCapableTimingWheel<T> timingWheel;

    @Getter private final int elapsedPerWheel;

    @Getter private final int tickDuration;

    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    private static final int DEFAULT_ELAPSED_PER_WHEEL = 30 * 1000;

    private static final int DEFAULT_TICK_DURATION = 100;

    public BidTimer() {
        this(DEFAULT_ELAPSED_PER_WHEEL, DEFAULT_TICK_DURATION);
    }

    public BidTimer(int elapsedPerWheel, int tickDuration) {
        this.elapsedPerWheel = elapsedPerWheel;
        this.tickDuration = tickDuration;
    }

    private void onEvent(T event) {
        try {
            onEvent0(event);
        } catch (Throwable e) {
            log.warn("[timer error]", e);
        }
    }

    private void onEvent0(T event) {
        taskExecutor.execute(() -> {
            for (BidListener listener : listeners) {
                listener.onEvent(event);
            }
        });
    }

    public void add(T event, long timeout) {
        add(event, timeout, TIME_UNIT);
    }

    public void add(T event, long timeout, TimeUnit timeUnit) {
        long elapsed = timingWheel.add(event, TIME_UNIT.convert(timeout, timeUnit));
        log.debug("[bid timer] event = {}, elapsed = {}", event, elapsed);
    }

    public void remove(T event) {
        timingWheel.remove(event);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskExecutor = Executors.newSingleThreadExecutor();
        this.timingWheel = new CircleCapableTimingWheel<>(this.tickDuration, getTicksPerWheel(), TIME_UNIT);
        this.timingWheel.addExpirationListener((event) -> onEvent(event));
        this.timingWheel.start();
    }

    public int getTicksPerWheel() {
        return elapsedPerWheel / tickDuration;
    }

    @Override
    public void destroy() throws Exception {
        if (taskExecutor == null || taskExecutor.isShutdown()) {
            return;
        }

        taskExecutor.shutdownNow();
    }
}
