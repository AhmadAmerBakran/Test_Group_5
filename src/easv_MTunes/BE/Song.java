package easv_MTunes.BE;

import java.io.File;

public class Song {

    private int id;
    private String title, artist;
    private File songFile;


    public Song(int id, String title, String artist, File songFile) {
        this.id = id;
        this.title = title;
        this.songFile = songFile;
        this.artist = artist;

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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return id + "       " +'\'' + title + '\'' + "      " + artist;
    }
}
