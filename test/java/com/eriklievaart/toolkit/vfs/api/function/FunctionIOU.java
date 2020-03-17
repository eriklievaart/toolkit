package com.eriklievaart.toolkit.vfs.api.function;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FunctionIOU {

	@Test
	public void store() {
		List<Integer> numbers = Arrays.asList(2, 3);

		FunctionIO<Integer> io = new FunctionIO<>(v -> v.toString(), v -> Integer.parseInt(v));
		io.store(numbers);

		Assertions.assertThat(io.load()).containsExactly(2, 3);
	}
}