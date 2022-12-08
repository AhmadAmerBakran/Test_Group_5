package easv_MTunes.DAL;

import easv_MTunes.BE.Song;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ISongDataAccess {
    public ArrayList<Song> getAllSongs() throws SQLException;

    public Song createSong(String title, String artist, File songFile) throws Exception;

    public void updateSong(Song song) throws Exception;

    public void deleteSong(Song song) throws Exception;
}
