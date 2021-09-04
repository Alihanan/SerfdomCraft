package pl.kalibrov.main.subsystems;

import java.util.HashMap;

public abstract class RPGClassSystem {
	
	
	static int pclass_id = 0;
	private static HashMap<Integer, PClass> all_ids = new HashMap<>();
	public enum PClass {
		
		MINER("Шахтер", pclass_id++), 
		TRADER("Торговец", pclass_id++), 
		HUNTER("Охотник", pclass_id++), 
		ENGINEER("Инженер", pclass_id++), 
		NONE("Тунеядец", pclass_id++);	
		
		
		private final String text;
		private final int my_id;
		PClass(final String text, int id) {
	        this.text = text;
	        this.my_id = id;
	        all_ids.put(id, this);
	    }
		@Override
		public String toString() {
			return this.text;
		}
		
		public int toInt() {
			return this.my_id;
		}
		public static PClass fromInt(int id) {
			return all_ids.get(id);
		}
		
		public static int getNumber() {
			return values().length;
		}
	}
	
	public class PClassInfo {
		private final int[] skills = new int[PClass.getNumber()];
		private PClass first_selected = PClass.NONE;
		private PClass second_selected = PClass.NONE;
		
		public PClassInfo() {
			for(int i = 0; i < skills.length; i++) {
				skills[i] = 0;
			}
		}

		public PClass getFirst_selected() {
			return first_selected;
		}

		public void setFirst_selected(PClass first_selected) {
			this.first_selected = first_selected;
		}

		public PClass getSecond_selected() {
			return second_selected;
		}

		public void setSecond_selected(PClass second_selected) {
			this.second_selected = second_selected;
		}

		public int[] getSkills() {
			return skills;
		}
		
		@Override
		public String toString() {
			String ret = "[\n";
			for(int i = 0; i < skills.length; i++) {
				ret += PClass.fromInt(i).toString() + ": " + skills[i] + "\n";
			}
			ret += "]\n";
			ret += "Первая профессия: " + first_selected.toString() + "\n";
			ret += "Вторая профессия: " + second_selected.toString() + "\n";
			return ret;
		}
	}
	
	/**
	 * My custom exception class.
	 */
	class BadClassDefinitionException extends Exception
	{
	  public BadClassDefinitionException(String message)
	  {
	    super(message);
	  }
	}
	
}
