package wasada;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveGame implements Serializable {

	private int _killCount, _experienceCount, _level;

	public SaveGame(int killCount, int experienceCount, int level) {
		_killCount = killCount;
		_experienceCount = experienceCount;
		_level = level;
	}
	
	public int getKillCount() {
		return _killCount;
	}
	
	public int getExperienceCount() {
		return _experienceCount;
	}
	
	public int getLevel() {
		return _level;
	}

}
