package gui;

public enum Language {
	GERMAN(0,"Deutsch","de"),
	ENGLISH(1,"English","en"),
	SPANISH(2,"Español","es"),
	FRENCH(3,"Français","fr"),
	ITALIAN(4,"Italiano","it"),
	JAPANESE(5,"日本語","ja"),
	POLISH(6,"Polski","pl"),
	PORTUGUEASE(7,"Português","pt"),
	RUSSIAN(8,"Русский","ru"),
	SWEDISH(9,"Svenska","sv"),
	TURKISH(10,"Türkçe","tr"),
	CHINESE(11,"简体中文","zh");
	
	public int num;
	public String name;
	public String iso;
	
	Language(int num, String name, String iso) {
		this.num = num;
		this.name = name;
		this.iso = iso;
	}

	public int getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public String getIso() {
		return iso;
	}
	
	
}
