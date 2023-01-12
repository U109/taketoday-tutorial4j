package cn.tuyucheng.taketoday.metrics.core;

import com.codahale.metrics.CachedGauge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActiveUsersGauge extends CachedGauge<List<Long>> {
	public ActiveUsersGauge(long timeout, TimeUnit timeoutUnit) {
		super(timeout, timeoutUnit);
	}

	@Override
	protected List<Long> loadValue() {
		return getActiveUserCount();
	}

	private List<Long> getActiveUserCount() {
		// mock reading from database and count the active users, return a fixed value
		List<Long> result = new ArrayList<Long>();
		result.add(12L);
		return result;
	}
}
