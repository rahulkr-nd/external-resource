package com.netradyne.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * AppMode
 */
public enum AppMode {
	DEVELOPMENT("DEV"), STAGING("STAGE"), PRODUCTION("PROD"), TESTING("TEST"), PERF("PERF"), JENKINS("JENK"), QA("QA"),
	QA2("QA2"), DOCKER("DOCK"), UNIT_TESTING("UNIT_TESTING");

	private final String shortName;

	private static final Map<String, AppMode> lookup = new HashMap<>();

	static {
		for (AppMode d : AppMode.values()) {
			lookup.put(d.getShortName(), d);
		}
	}

	private AppMode(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

	public static AppMode get(String shortName) {
		return lookup.get(shortName);
	}
}
