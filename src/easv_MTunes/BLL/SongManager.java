package easv_MTunes.BLL;

import easv_MTunes.BE.Song;
import easv_MTunes.BLL.util.SongSearcher;
import easv_MTunes.DAL.ISongDataAccess;
import easv_MTunes.DAL.SongDAO_Files;
import easv_MTunes.DAL.db.SongDAO_DB;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongManager {
    private ISongDataAccess songDAO;
    private SongSearcher songSearcher = new SongSearcher();

    public SongManager() {
        songDAO = new SongDAO_DB();
    }

    public ArrayList<Song> getAllSongs() throws SQLException {
        return songDAO.getAllSongs();
    }
    public List<Song> searchMovies(String query) throws Exception {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }
    public Song createNewSong(String title, String artist, File songFile) throws Exception {
        return songDAO.createSong(title, artist, songFile);
    }
    public void deleteSong(Song song) throws Exception {
        songDAO.deleteSong(song);
    }

    public void updateSong(Song updatedSong) throws Exception {
        songDAO.updateSong(updatedSong);
    }
}
