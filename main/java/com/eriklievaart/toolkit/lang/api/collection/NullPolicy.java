package com.eriklievaart.toolkit.lang.api.collection;

import com.eriklievaart.toolkit.lang.api.check.Check;

public enum NullPolicy {

	ACCEPT {
		@Override
		public boolean accept(Object obj) {
			return true;
		}
	},
	REJECT {
		@Override
		public boolean accept(Object obj) {
			Check.notNull(obj);
			return true;
		}
	},
	FILTER {
		@Override
		public boolean accept(Object obj) {
			return obj != null;
		}
	};

	public abstract boolean accept(Object obj);
}
