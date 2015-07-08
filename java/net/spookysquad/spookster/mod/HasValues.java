package net.spookysquad.spookster.mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface HasValues {

	public class Value {

		public Value(String name, Object min, Object max, float increasement) {
			assert min.getClass().equals(max.getClass());
			isSimpleValue = true;
			this.increasement = increasement;
			this.name = name;
			this.min = min;
			this.max = max;
		}

		public Value(String name, Object min, Object max) {
			assert min.getClass().equals(max.getClass());
			isSimpleValue = true;
			this.name = name;
			this.min = min;
			this.max = max;
		}

		public Value(String name, Collection list, Object type) {
			this.name = name;
			this.list = list;
			this.type = type;
		}

		public Value(String name, boolean shown, List<Value> values) {
			this.shown = shown;
			hasExtraValues = true;
			this.name = name;
//			this.otherValues = (ArrayList<Value>) values;
			this.otherValues.clear();
			for(Value value : values) {
				this.otherValues.add(value);
			}
		}

		private final String name;
		private boolean isSimpleValue = false;
		private float increasement = 0.1F;
		private Object type, min, max;

		private Collection list;

		private ArrayList<Value> otherValues = new ArrayList<Value>();
		private boolean hasExtraValues = false;
		private boolean shown = false;

		public boolean isShown() {
			return shown;
		}
		
		public void setShown(boolean shown) {
			this.shown = shown;
		}
		
		public boolean hasExtraValues() {
			return hasExtraValues;
		}

		public boolean isSimpleValue() {
			return isSimpleValue;
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

		public ArrayList<Value> getOtherValues() {
			return otherValues;
		}
	}

	List<Value> getValues();

	Object getValue(String n);

	void setValue(String n, Object v);
}
