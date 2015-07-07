package net.spookysquad.spookster.mod;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public interface HasValues {

	public class Value {
		public Value(String name, Object min, Object max, float increasement) {
			super();
			assert min.getClass().equals(max.getClass());
			isEditable = true;
			this.increasement = increasement;
			this.name = name;
			this.min = min;
			this.max = max;
		}

		public Value(String name, Object min, Object max) {
			super();
			assert min.getClass().equals(max.getClass());
			isEditable = true;
			this.name = name;
			this.min = min;
			this.max = max;
		}
		
		public Value(String name, Collection list, Object type) {
			super();
			this.name = name;
			this.list = list;
			this.type = type;
		}

		private final String name;

		private boolean isEditable = false;

		private float increasement = 0.1F;

		private Collection list;

		private Object type, min, max;

		public boolean isEditable() {
			return isEditable;
		}

		public float getIncreasement() {
			return increasement;
		}

		public String getName() {
			return name;
		}

		public Object getMin() {
			return min;
		}

		public Object getMax() {
			return max;
		}

		public Class getVClass() {
			return max.getClass();
		}
	}

	List<Value> getValues();

	Object getValue(String n);

	void setValue(String n, Object v);
}
