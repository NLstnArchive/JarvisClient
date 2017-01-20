package com.jarvis.utils;

public class MathUtils {

	private MathUtils() {
	}

	public static int clamp(int value, int min, int max) {
		if (value > max)
			return max;
		if (value < min)
			return min;
		return value;
	}

	public static float clamp(float value, float min, float max) {
		if (value > max)
			return max;
		if (value < min)
			return min;
		return value;
	}

	public static double clamp(double value, double min, double max) {
		if (value > max)
			return max;
		if (value < min)
			return min;
		return value;
	}

	public static long clamp(long value, long min, long max) {
		if (value > max)
			return max;
		if (value < min)
			return min;
		return value;
	}

	// ////////////////////////////////////////////////////////////////////////

	public static int max(int x0, int x1) {
		return x0 > x1 ? x0 : x1;
	}

	public static float max(float x0, float x1) {
		return x0 > x1 ? x0 : x1;
	}

	public static double max(double x0, double x1) {
		return x0 > x1 ? x0 : x1;
	}

	public static long max(long x0, long x1) {
		return x0 > x1 ? x0 : x1;
	}

	// ////////////////////////////////////////////////////////////////////////

	public static int min(int x0, int x1) {
		return x0 < x1 ? x0 : x1;
	}

	public static float min(float x0, float x1) {
		return x0 < x1 ? x0 : x1;
	}

	public static double min(double x0, double x1) {
		return x0 < x1 ? x0 : x1;
	}

	public static long min(long x0, long x1) {
		return x0 < x1 ? x0 : x1;
	}

}
