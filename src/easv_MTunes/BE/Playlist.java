package easv_MTunes.BE;

import java.io.File;
import java.util.List;

public class Playlist {
    private String title, artist;
    private File songFile;
    int id;

    public Playlist(int id, String title, String artist, File songFile) {
        this.title = title;
        this.artist = artist;
        this.songFile = songFile;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public File getSongFile() {
        return songFile;
    }

    public void setSongFile(File songFile) {
        this.songFile = songFile;
    }
}

