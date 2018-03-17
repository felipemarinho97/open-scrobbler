package gui;

public enum MusicInterface {
	MPD(0),
	SPOTIFY(1);
	
	int num;
	
	MusicInterface(int num) {
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
}
