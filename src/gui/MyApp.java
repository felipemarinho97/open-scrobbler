package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.freedesktop.icons.StateIcon;
import org.gnome.gdk.Pixbuf;
import org.gnome.gdk.RGBA;
import org.gnome.gtk.Align;
import org.gnome.gtk.Box;
import org.gnome.gtk.Button;
import org.gnome.gtk.EventBox;
import org.gnome.gtk.Image;
import org.gnome.gtk.Label;
import org.gnome.gtk.MenuBar;
import org.gnome.gtk.Notebook;
import org.gnome.gtk.Orientation;
import org.gnome.gtk.ProgressBar;
import org.gnome.gtk.ReliefStyle;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.StateFlags;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

import backend.InfoRequester;
import backend.OpenLastfm;
import backend.PlayerEvent;
import backend.PlayerListener;
import backend.player.MPDPlayerEventID;
import backend.player.Song;
import exceptions.PlayerException;
import exceptions.SongException;
import util.Util;

public class MyApp {
    private String artist;
	private Box similarArtistsBox;
    private Notebook tab;
    private ProgressBar musicBar;
	private Label actualLengh;
	private Label musicLengh;
	private String currentMusicTitle;
	private Song song;
	private Label playingTitle;
	private Label summary;
	private Image artistCover;
	private Window win;
	private Label content;
    private boolean stopped;
    private String album;
	private Image albumCover;
    private Button unloveButton;
	private Button loveButton;
	private EventBox unloveButtonBox;
	private EventBox loveButtonBox;
	private OpenLastfm openLastfm;
	private Label commentTitle;
    private Box actionButtonsBox;
	private int elapsed;
	private boolean playing = false;

    public MyApp() {
        openLastfm = OpenLastfm.getInstance();
        try {
			this.song = new Song();
		} catch (Exception e) {
		    e.printStackTrace();
		}
//        this.artist = song.getArtist().replace("&", "&amp;");
//        this.album = song.getAlbum().replace("&", "&amp;");
//        this.currentMusicTitle = song.getTitle().replace("&", "&amp;");
//        openLastfm.updateArtistRequester(artist);
//        openLastfm.updateTrack(currentMusicTitle);
//        openLastfm.getAlbumInfo(album);
        this.openLastfm.addEventListener(new PlayerListener() {

			@Override
			public void onPlayerChanged(PlayerEvent event) {
				String[] events = event.getEvents();
		        for (String e : events) {
		            if (e.equals("Metadata") ||
		                    e.equals(MPDPlayerEventID.SONG_CHANGED.name()) ||
		                    e.equals(MPDPlayerEventID.PLAYER_STARTED.name())) {
		            	updateArtist();
		            } else if (e.equals(MPDPlayerEventID.PLAYER_PAUSED.name()) ||
		            		e.equals(MPDPlayerEventID.PLAYER_STOPPED.name()) ||
		            		e.equals("Paused")) {
		            	playing = false;
		            } else if (e.equals(MPDPlayerEventID.PLAYER_UNPAUSED.name()) ||
		            		e.equals("Playing")) {
		            	playing = true;
		            }
		        }
			}
		});
	}

    private void displayError(Exception e) {
        Window errorWindow = new Window();
        errorWindow.setTitle(e.toString());
        errorWindow.setIcon(StateIcon.DIALOG_ERROR);
        Box errorBox = new Box(Orientation.VERTICAL, 5);
        errorBox.packStart(new Label(e.getMessage()), false, false, 5);
        errorBox.packStart(new Label(Util.stackTraceToString(e)), false, false, 5);
        errorWindow.add(errorBox);
        errorWindow.showAll();
    }

    public void buildStoppedState() {
		win.setTitle(Main.PROGRAM_NAME + " " + Main.VERSION);
        try {
            this.albumCover.setImage(new Pixbuf("blank_album.png",100,100,true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.artistCover.setImage(new Pixbuf("blank_artist.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.updateMusicLengh(0);
        this.updateActualLengh(0);
        this.playingTitle.setLabel("<span font_desc='sans 12'><b>" + Main.PROGRAM_NAME + "</b></span>\n");
        this.commentTitle.setLabel("Play some music on your preferred player to start scrobbling.");
        actionButtonsBox.hide();
        tab.hide();
        similarArtistsBox.hide();
        stopped = true;
	}
	
	public void buildElements(Window w) {
		this.win = w;
		w.setDefaultSize(800, 450);

//        updateTitle();

        Box conteiner = new Box(Orientation.VERTICAL, 0);
        Box artistInfoBox = new Box(Orientation.HORIZONTAL, 5);
        Box artistBioBox = new Box(Orientation.VERTICAL, 5);
        Box artistCoverBox = new Box(Orientation.VERTICAL, 5);
        Box playingBox = new Box(Orientation.HORIZONTAL, 5);
        Box musicBarBox = new Box(Orientation.HORIZONTAL, 1);
        this.actionButtonsBox = new Box(Orientation.HORIZONTAL, 3);
        MenuBar menuBar = new MyMenu();
        this.tab = new Notebook();
        this.tab.setBorderWidth(0);

        //// Music Bar Box ////
        this.musicBar = new ProgressBar();
        this.musicBar.setAlignVertical(Align.CENTER);
        this.musicBar.pulse();

        actualLengh = new Label();
        actualLengh.setAlignHorizontal(Align.END);
        actualLengh.setAlignVertical(Align.END);
//        updateActualLengh();

        musicLengh = new Label();
        musicLengh.setAlignHorizontal(Align.END);
        musicLengh.setAlignVertical(Align.END);
//        updateMusicLengh();

        //// Playing Info ////
        Box playingTitleBox = new Box(Orientation.VERTICAL, 5);
        this.playingTitle = new Label();
        this.playingTitle.setAlignHorizontal(Align.START);
        this.playingTitle.setAlignVertical(Align.START);
        this.playingTitle.setUseMarkup(true);
        this.playingTitle.setLineWrap(true);
        this.commentTitle = new Label();
        this.commentTitle.setUseMarkup(true);
        this.commentTitle.setLineWrap(true);

        playingTitleBox.packStart(playingTitle, true, true, 3);
        playingTitleBox.packStart(commentTitle, true, true, 3);

        // Summary
        summary = new Label();
        summary.setLineWrap(true);
        summary.setUseMarkup(true);
//        summary.setLabel(openLastfm.getBio("summary").replace("&", "&amp;"));

        Box textBoxSummary = new Box(Orientation.VERTICAL, 1);
        textBoxSummary.packStart(summary, false, false, 1);
        textBoxSummary.setSizeRequest(500, 200);

        // Content
        content = new Label();
        content.setLineWrap(true);
        content.setUseMarkup(true);
//        content.setLabel(openLastfm.getBio("content").replace("&", "&amp;"));

        Box textBoxContent = new Box(Orientation.VERTICAL, 1);
        textBoxContent.packStart(content, false, false, 1);
        textBoxContent.setSizeRequest(400, 200);

        ScrolledWindow sw = new ScrolledWindow();
        sw.addWithViewport(textBoxContent);

        Box scrollableContentBox = new Box(Orientation.VERTICAL, 1);
        scrollableContentBox.packStart((Widget) sw, true, true, 0);

        //// Similar Artists ////
        this.buildSimilarArtistsView();

        //// Cover Box ////
        artistCover = new Image();
//        updateArtistImage();

        //// Album Box ////
        albumCover = new Image();
//        updateAlbumImage();

        //// Playing Title Buttons ////
        this.buildPlayingTitleButtons();

        this.tab.setScrollable(true);
        this.tab.appendPage(textBoxSummary, new Label("Summary"));
        this.tab.appendPage(scrollableContentBox, new Label("Content"));
        // Numix Black new RGBA(0.2, 0.2, 0.2, 1)
        // Last.fm RED new RGBA(0.83, 0.12, 0.16, 1)

        musicBarBox.overrideBackground(StateFlags.NORMAL, new RGBA(0.2, 0.2, 0.2, 1));
        musicBarBox.overrideColor(StateFlags.NORMAL, new RGBA(1, 1, 1, 0.8));
        musicBarBox.setExpandVertical(false);
        musicBarBox.setExpandHorizontal(false);
        musicBarBox.packStart(new ControlButtons(), false, false, 1);
        musicBarBox.packStart(this.musicBar, true, true, 1);
        musicBarBox.packStart(actualLengh, false, false, 1);
        musicBarBox.packEnd(musicLengh, false, false, 1);

        artistCoverBox.packStart(artistCover, false, false, 0);
        artistCoverBox.packStart(this.similarArtistsBox, true, true, 0);

        playingBox.packEnd(actionButtonsBox, false, false, 0);
        playingBox.packEnd(playingTitleBox, true, true, 3);
        playingBox.packEnd(albumCover, false, false, 0);
        playingBox.setAlignHorizontal(Align.START);

        artistBioBox.setAlignVertical(Align.START);
        artistBioBox.packStart(playingBox, true, true, 3);
        artistBioBox.packStart(this.tab, true, true, 3);

        artistInfoBox.setBorderWidth(5);
        artistInfoBox.packStart(artistBioBox, true, true, 0);
        artistInfoBox.packStart(artistCoverBox, true, true, 0);

        conteiner.packStart(menuBar, false, false, 0);
        conteiner.packStart(artistInfoBox, false, false, 0);
        conteiner.packEnd(musicBarBox, false, true, 0);
        w.add(conteiner);
//        this.updatePlayingTitle();
//        this.updateButtons();
        this.updateMusicProgressMain();

       // this.update();
        buildStoppedState();
	}

    private void update() {
        updateTitle();
        updateActualLengh();
        updateMusicLengh();
        updateArtistImage();
        updateAlbumImage();
        summary.setLabel(openLastfm.getBio("summary").replace("&", "&amp;"));
        content.setLabel(openLastfm.getBio("content").replace("&", "&amp;"));
        this.updatePlayingTitle();
        this.updateButtons();
    }

    private void buildPlayingTitleButtons() {
		///// Playing Title Button ////
		loveButtonBox = new EventBox();
		unloveButtonBox = new EventBox();

		this.loveButton = new Button();
        try {
            loveButton.setImage(new Image(new Pixbuf("2661.png",16,16,true)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loveButton.setRelief(ReliefStyle.HALF);
		loveButton.setExpandVertical(false);
		loveButton.setAlignVertical(Align.START);
		loveButton.connect((Button.Clicked) arg0 -> {
			openLastfm.loveTrack(artist, currentMusicTitle);
			loveButtonBox.hide();
			unloveButtonBox.showAll();
		});

		this.unloveButton = new Button();
        try {
            unloveButton.setImage(new Image(new Pixbuf("2764.png",16,16,true)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        unloveButton.setRelief(ReliefStyle.HALF);
		unloveButton.setExpandVertical(false);
		unloveButton.setAlignVertical(Align.START);
		unloveButton.connect((Button.Clicked) arg0 -> {
			openLastfm.unloveTrack(artist, currentMusicTitle);
			unloveButtonBox.hide();
			loveButtonBox.showAll();
		});

		loveButtonBox.add(loveButton);
		loveButtonBox.connect((Widget.EnterNotifyEvent) (arg0, arg1) -> {
            try {
                loveButton.setImage(new Image(new Pixbuf("2764.png",16,16,true)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;
		});
		loveButtonBox.connect((Widget.LeaveNotifyEvent) (arg0, arg1) -> {
            try {
                loveButton.setImage(new Image(new Pixbuf("2661.png",16,16,true)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;
		});

		unloveButtonBox.add(unloveButton);
		unloveButtonBox.connect((Widget.EnterNotifyEvent) (arg0, arg1) -> {
            try {
                unloveButton.setImage(new Image(new Pixbuf("1F494.png",16,16,true)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;
		});
		unloveButtonBox.connect((Widget.LeaveNotifyEvent) (arg0, arg1) -> {
            try {
                unloveButton.setImage(new Image(new Pixbuf("2764.png",16,16,true)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;
		});

		actionButtonsBox.packStart(loveButtonBox,false,false,0);
        actionButtonsBox.packStart(unloveButtonBox,false,false,0);

	}

	private void updateAlbumImage() {
	    new Thread(() -> {
            Pixbuf myCover = null;
		    try {
                myCover = InfoRequester.downloadImage(openLastfm.getAlbumImageURL(2), 100, 100);
            } catch (IOException e) {
                e.printStackTrace();
            }
		albumCover.setImage(myCover);
        }).start();
	}

	private void updateActualLengh() {
		long elapsed = openLastfm.getElapsedTime();
		this.actualLengh.setLabel(String.format("%02d:%02d", elapsed/60,elapsed % 60));
	}
	
	private void updateActualLengh(long seg) {
		this.actualLengh.setLabel(String.format("%02d:%02d", seg/60,seg % 60));
	}

	private void updateMusicLengh() {
		long lengh = song.getLength();
		musicLengh.setLabel(String.format("/ %02d:%02d", lengh/60, lengh % 60));
	}
	
	private void updateMusicLengh(long seg) {
		musicLengh.setLabel(String.format("/ %02d:%02d", seg/60, seg % 60));
	}

	private void updateTitle() {
		win.setTitle(artist.replace("&amp;", "&") + " - "
				+  currentMusicTitle.replace("&amp;", "&")
				+ " | " + Main.PROGRAM_NAME + " " + Main.VERSION);
	}
	
	private String normalize(String nome) {
		if (nome.length() >= 22) {
			return (nome.substring(0, 18) + "...").replace("&", "&amp;");
		}
		return nome.replace("&", "&amp;");
	}

	private void buildSimilarArtistsView() {
		
		this.similarArtistsBox = new Box(Orientation.VERTICAL, 0);
		Label label = new Label();
		label.setUseMarkup(true);
		label.setLabel("<span font_desc='sans 12'><b>Similar Artists</b></span>");
		this.similarArtistsBox.packStart(label,true,true,3);
		this.similarArtistsBox.setExpandVertical(false);
		this.similarArtistsBox.setExpandHorizontal(false);
		this.similarArtistsBox.setAlignVertical(Align.START);
		
		Box artistBox = null;
		String url, name;
		
		for (int i = 0; i < 5; i++) {
			artistBox = new Box(Orientation.HORIZONTAL,5);
			artistBox.setAlignHorizontal(Align.START);
			
			try {
				url = openLastfm.getSimilarArtist(i,"url").replace("&", "&amp;");
				name = openLastfm.getSimilarArtist(i,"name");
			} catch (Exception e) {
				System.err.println("Similar artists for " + artist + " not found.");
				url = "";
				name = "";
			}
			
			label = new Label();
			label.setUseMarkup(true);
			label.setLabel("<a href='" + url + "'>" + normalize(name) + "</a>");
			label.setTooltipMarkup(name.replace("&", "&amp;"));
			label.setMaxWidthChars(5);
			Image image = new Image();

            int finalI = i;
            new Thread(() -> {
                Pixbuf myImage = null;
                try {
					String imageUrl = openLastfm.getSimilarArtist(finalI, "imageURL");
                	if (imageUrl == null || imageUrl.trim().isEmpty()) {
						myImage = new Pixbuf("blank_artist.png",34,34,true);
					} else {
						myImage = InfoRequester.downloadImage(imageUrl);
					}
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                image.setImage(myImage);
            }).start();

			artistBox.packStart(image, true, true, 0);
			artistBox.packStart(label, true, true, 0);
			
			similarArtistsBox.packStart(artistBox, true, true, 3);
		}

	}
	
	public void updateSimilarAtists() {
		Widget[] artistBoxes = this.similarArtistsBox.getChildren();
		
		String url, name;
		
		for (int i = 1; i < artistBoxes.length; i++) {
			Box widget = (Box) artistBoxes[i];
			Widget[] widgets = widget.getChildren();
			Image image = (Image) widgets[0];
			Label nome = (Label) widgets[1];
			
			try {
				url = openLastfm.getSimilarArtist(i-1,"url").replace("&", "&amp;");
				name = openLastfm.getSimilarArtist(i-1,"name");
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.err.println("Similar artists for " + artist + " not found.");
				url = "";
				name = "";
			}
			int j = i;
			new Thread(() -> {
                Pixbuf myImage = null;
                try {
					String imageUrl = openLastfm.getSimilarArtist(j-1,"imageURL");
					if (imageUrl == null || imageUrl.trim().isEmpty()) {
						System.out.println("xaran");
						myImage = new Pixbuf("blank_artist.png",34,34,true);
					} else {
						myImage = InfoRequester.downloadImage(imageUrl);
					}
                } catch (Exception e) {
                	displayError(e);
                    System.err.println(e.getMessage());
                }
                image.setImage(myImage);
			}).start();
			nome.setLabel("<a href='" + url + "'>" + normalize(name) + "</a>");
			nome.setTooltipMarkup(name.replace("&", "&amp;"));
		}
		System.gc();
		System.runFinalization();
	}
	
	public void updateMusicProgress() {
		float elapsed = openLastfm.getElapsedTime();
        float fraction = elapsed/song.getLength();
        if (fraction != fraction || Float.isInfinite(fraction)) {
            this.musicBar.setFraction(0.0);
        } else {
            this.musicBar.setFraction(fraction);
        }

		this.actualLengh.setLabel(String.format("%02d:%02d", (int)elapsed/60,(int)elapsed % 60));
	}
	
	public void updateMusicProgressMain() {
		Thread t0 = new Thread() {

			@Override
			public void run() {
				do {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						displayError(e);
						e.printStackTrace();
					}
					int length = -1;
					if (playing) {
						length = song.getLength();
					}
				
				while (elapsed < length && playing) {
					elapsed++;
			        float fraction = ((float) elapsed)/song.getLength();
			        if (fraction != fraction || Float.isInfinite(fraction)) {
			            musicBar.setFraction(0.0);
			        } else if (fraction <= 1.0) {
			            musicBar.setFraction(fraction);
			        }

					actualLengh.setLabel(String.format("%02d:%02d", (int)elapsed/60,(int)elapsed % 60));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				} while(true);
			}

		};
		t0.start();
	}
	
	public void updateArtist() {

        Song song = null;
        try {
            song = openLastfm.getCurrentSong();

        if (!this.song.equals(song)) {
            if (stopped) {
                stopped = false;
                tab.showAll();
                actionButtonsBox.show();
                similarArtistsBox.showAll();
            }
            if (!this.song.getArtist().equals(song.getArtist())) {
                this.artist = song.getArtist().replace("&", "&amp;");
                openLastfm.updateArtistRequester(artist);
                //updateTitle();

                this.summary.setLabel(openLastfm.getBio("summary").replace("&", "&amp;"));
                this.content.setLabel(openLastfm.getBio("content").replace("&", "&amp;"));
                this.updateArtistImage();
                this.updateSimilarAtists();
            }

            if (!this.song.getAlbum().equals(song.getAlbum())) {
                this.album = song.getAlbum().replace("&", "&amp;");
                openLastfm.getAlbumInfo(this.album);
                updateAlbumImage();
            }

            this.song = song;
            this.currentMusicTitle = song.getTitle().replace("&", "&amp;");
            openLastfm.updateTrack(this.currentMusicTitle);
            updateTitle();
            updateMusicLengh();

            this.updatePlayingTitle();
            playing = true;
            elapsed = (int) openLastfm.getElapsedTime();
        }

        } catch (PlayerException e) {
            if (!stopped) {
                buildStoppedState();
                this.song = new Song();
            }

        } catch (SongException e) {
            if (!stopped) {
                buildUnscrobabbleState();
                this.song = new Song();
            }
        }
	}

    private void buildUnscrobabbleState() {
        this.buildStoppedState();
        this.commentTitle.setLabel("Unable to scrobble due to incorrect tags.");

    }

    private void updatePlayingTitle() {
		int artistTimes = openLastfm.artistTimes(artist);
		int trackTimes= openLastfm.trackTimes(currentMusicTitle, artist);

		String artistLink = openLastfm.getArtistLink().replace("&","&amp;");
		String albumLink = openLastfm.getAlbumLink().replace("&","&amp;");

		this.playingTitle.setLabel("<span font_desc='sans 12'><b>" + currentMusicTitle + "</b></span><span font_desc='sans 10'>\n"
				+ "by <a href='" + artistLink + "'>" + artist + "</a>\n"
				+ "from <a href='" + albumLink + "'>" + album + "</a>\n"
				+ "</span>");

		this.commentTitle.setLabel("<span font_desc='sans 10'>"
				+ ((artistTimes != -1) ?
				("You have listened to <b>" + artist + "</b> " + artistTimes + " times and "
				+ ((trackTimes != -1) ?
				("<b>" + currentMusicTitle + "</b> " + trackTimes + " times.")
				: ("this is the first time you've listened to <b>" + currentMusicTitle + "</b>.")))
				: ("This is the first time you've listened to <b>" + artist + "</b>."))
				+ "</span>");

        this.updateButtons();
	}

    private void updateButtons() {
        if (openLastfm.isLoved(currentMusicTitle, artist)) {
            //System.out.println("Loved");
            unloveButtonBox.showAll();
            loveButtonBox.hide();
        } else {
            //System.out.println("UnLoved");
            loveButtonBox.showAll();
            unloveButtonBox.hide();
        }
    }

    public void updateArtistImage() {
	    new Thread(() -> {
            Pixbuf myCover = null;
            try {
            	String url = openLastfm.getArtistImageURL();
            	if (url != null || !url.trim().isEmpty()) {
            		myCover = InfoRequester.downloadImage(url);
				} else {
                    System.err.println("No artist image found");
                    myCover = new Pixbuf("blank_artist.png");
				}
            } catch (IOException e) {
            	displayError(e);
                e.printStackTrace();
            }
            artistCover.setImage(myCover);
        }).start();
	}

}
