package main.utils;

import java.util.Comparator;

import models.LogicalCoupling;

public class Comparators {

	public static Comparator<LogicalCoupling> LOGICAL_COUPLING_SCORE = new Comparator<LogicalCoupling>() {
		
		@Override
		public int compare(LogicalCoupling o1, LogicalCoupling o2) {
			return new Integer(o1.getScore()).compareTo(new Integer(o2.getScore()));
		}
	};
	
	
}
