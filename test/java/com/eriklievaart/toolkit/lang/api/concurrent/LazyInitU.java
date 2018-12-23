package com.eriklievaart.toolkit.lang.api.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.FormattedException;

public class LazyInitU {
	private static final int EXPECTED = 1;

	@Test
	public void getCreateOnlyOnce() throws InterruptedException {
		AtomicInteger count = new AtomicInteger();
		Supplier<Integer> supplier = () -> count.incrementAndGet();
		LazyInit<Integer> init = new LazyInit<>(supplier);

		AtomicInteger result = new AtomicInteger(EXPECTED);
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executor.execute(() -> {
				Integer value = init.get();
				if (value != EXPECTED) {
					result.set(value);
				}
			});
		}
		executor.awaitTermination(100, TimeUnit.MILLISECONDS);

		if (result.get() != EXPECTED) {
			throw new FormattedException("result was %", result.get());
		}
	}
}