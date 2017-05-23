package gui;

import org.freedesktop.icons.ActionIcon;
import org.gnome.gtk.Box;
import org.gnome.gtk.Button;
import org.gnome.gtk.IconSize;
import org.gnome.gtk.Image;
import org.gnome.gtk.Orientation;
import org.gnome.gtk.Button.Clicked;

class ControlButtons extends Box {
	private Button toggle;
	private Button next;
	private Button prev;

	public ControlButtons() {
		super(Orientation.HORIZONTAL, 3);
		Clicked activateToggle = new Button.Clicked() {

			@Override
			public void onClicked(Button arg0) {
				MyApp.getOpenLastfm().togglePlayer();
				toggle.setImage(getPlaybackImage());
			}
			
		};
		
		this.toggle = new Button();
		toggle.connect(activateToggle);
		toggle.setImage(this.getPlaybackImage());
		
		this.next = new Button();
		this.next.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button arg0) {
				MyApp.getOpenLastfm().nextTrack();
				toggle.setImage(getPlaybackImage());
			}
		});
		next.setImage(new Image(ActionIcon.MEDIA_SKIP_FORWARD, IconSize.BUTTON));
		
		this.prev = new Button();
		this.prev.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button arg0) {
				MyApp.getOpenLastfm().previousTrack();
				toggle.setImage(getPlaybackImage());
			}
		});
		prev.setImage(new Image(ActionIcon.MEDIA_SKIP_BACKWARD, IconSize.BUTTON));
		
		this.packStart(prev, false, false, 1);
		this.packStart(toggle, false, false, 1);
		this.packStart(next, false, false, 1);
		this.setBorderWidth(5);
		
	}

	private Image getPlaybackImage() {
		switch (MyApp.getOpenLastfm().getStatus()) {
		case STATUS_PLAYING:
			return new Image(ActionIcon.MEDIA_PLAYBACK_PAUSE, IconSize.BUTTON);
		case STATUS_STOPPED:
			return new Image(ActionIcon.MEDIA_PLAYBACK_START, IconSize.BUTTON);
		case STATUS_PAUSED:
			return new Image(ActionIcon.MEDIA_PLAYBACK_START, IconSize.BUTTON);
		}
		return new Image(ActionIcon.MEDIA_PLAYBACK_START, IconSize.BUTTON);
	}
}