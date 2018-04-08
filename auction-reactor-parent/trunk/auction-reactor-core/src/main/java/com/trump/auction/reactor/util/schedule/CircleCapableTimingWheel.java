package com.trump.auction.reactor.util.schedule;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Timing wheel that capable of circle.
 *
 * @see TimingWheel
 * @author <a href="mailto:sowen1023@gmail.com">Owen.Yuan</a>
 * @since 2017/11/23
 */
@Slf4j
@ToString(callSuper = true)
public class CircleCapableTimingWheel<E> extends TimingWheel<E> {

	private final Map<E, Circle> circleMap  = new ConcurrentHashMap<>();

	public CircleCapableTimingWheel(int tickDuration, int ticksPerWheel, TimeUnit timeUnit) {
		super(tickDuration, ticksPerWheel, timeUnit);
	}

	@Override
	public long add(E e) {
		return this.add(e, 1);
	}

	public long add(E e, int circle) {
		synchronized(e) {
			this.add(e, circle * getElapsedPerWheel());
			circleMap.put(e, new Circle(circle));
			return getElapsedPerWheel() * circle;
		}
	}

	/**
	 * 添加元素
	 * <p>
	 *     根据<code> elapsed </code> 计算 <code> e </code> 在{@link super#wheel} 中下标，
	 *     并将元素添加至 {@link super#wheel} 中。
	 * </p>
	 * @param e 要添加的元素
	 * @param elapsed 元素超时时间
	 * @return 元素超时时间
	 */
	public long add(E e, long elapsed) {

		// timeout per wheel
		final long elapsedPerWheel = getElapsedPerWheel();
		// circle count
		int circle = (int) (elapsed / elapsedPerWheel);
		if (elapsed % elapsedPerWheel != 0) {
			circle = circle + 1;
		}

		// offset relative to current ticket index
		int offset = (int) (elapsed % elapsedPerWheel / tickDuration);
		if (offset != 0 && elapsed % elapsedPerWheel % tickDuration != 0) {
			offset = offset + 1;
		}

		synchronized(e) {
			checkAdd(e);
			int addIndex = getAddIndex(offset);
			Slot<E> slot = getWheel().get(addIndex);
			slot.add(e);
			indicator.put(e, slot);
			circleMap.put(e, new Circle(circle));
			return elapsed;
		}
	}

	/**
	 * 每一圈的时间
	 */
	private long getElapsedPerWheel() {
		return (ticksPerWheel - 1) * tickDuration;
	}

	private int getAddIndex(int offset) {
		lock.readLock().lock();
		try {
			int addIndex = currentTickIndex + offset;
			if (addIndex >= wheel.size()) {
				addIndex = addIndex - wheel.size();
			}
			return addIndex;
		} finally {
			lock.readLock().unlock();
		}
	}

	public boolean remove(E e) {
		synchronized (e) {
			boolean result = super.remove(e);

			circleMap.remove(e);

			return result;
		}
	}

	@Override
	protected void notifyExpired(int idx) {
		Slot<E> slot = getWheel().get(idx);
		Set<E> elements = slot.elements();
		for (E e : elements) {
			synchronized (e) {
				Circle circle = circleMap.get(e);
				if (circle != null && circle.decrementAndGet() > 0) {
					continue;
				}

				circleMap.remove(e);

				slot.remove(e);
				Slot<E> latestSlot = getIndicator().get(e);
				if (slot.equals(latestSlot)) {
					getIndicator().remove(e);
				}
			}

			for (ExpirationListener<E> listener : getExpirationListeners()) {
				listener.expired(e);
			}
		}
	}

	private static class Circle {

		private final AtomicInteger count;

		public Circle(int count) {
			this.count = new AtomicInteger(count <= 0 ? 1 : count);
		}

		public AtomicInteger get() {
			return count;
		}

		public int decrementAndGet() {
			return count.decrementAndGet();
		}
	}
}
