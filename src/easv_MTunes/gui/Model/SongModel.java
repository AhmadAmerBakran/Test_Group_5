package easv_MTunes.gui.Model;

import easv_MTunes.BE.Song;
import easv_MTunes.BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class SongModel {
    private ObservableList<Song> songsToBeViewed;
    private SongManager songManager;
    private Song selectedSong;

    public SongModel() throws SQLException {
        songManager = new SongManager();
        songsToBeViewed = FXCollections.observableArrayList();
        songsToBeViewed.addAll(songManager.getAllSongs());
    }

    public ObservableList<Song> getObservableSongs()
    {
        return songsToBeViewed;
    }
    public void searchSong(String query) throws Exception {
        List<Song> searchResults = songManager.searchMovies(query);
        songsToBeViewed.clear();
        songsToBeViewed.addAll(searchResults);
    }
    public void createNewSong(String title, String artist, File songFile) throws Exception {
        Song song = songManager.createNewSong(title, artist, songFile);
        songsToBeViewed.add(song);

    }

    public void deleteSong(Song song) throws Exception {
        songManager.deleteSong(song);
        songsToBeViewed.remove(song);
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong;
    }

    public void updateSong(Song updatedSong) throws Exception {
        songManager.updateSong(updatedSong);

        // update ListView
        songsToBeViewed.clear();
        songsToBeViewed.addAll(songManager.getAllSongs());
    }
}
