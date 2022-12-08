package easv_MTunes.gui.Model;

import easv_MTunes.BE.AllPlaylists;
import easv_MTunes.BE.Song;
import easv_MTunes.BLL.PlaylistManager;
import easv_MTunes.BLL.SongManager;
import easv_MTunes.gui.Controller.SongViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class PlaylistModel {
    private ObservableList<Song> songsToBeViewed;
    private PlaylistManager playlistManager;
    private AllPlaylists selectedPlaylist;
    public PlaylistModel() throws Exception {
        playlistManager = new PlaylistManager();
        songsToBeViewed = FXCollections.observableArrayList();
        if(getSelectedPlaylist()!=null)
        {songsToBeViewed.addAll(playlistManager.getAllPlaylistSongs(getSelectedPlaylist().getPlaylistName()));
        }
    }
    public AllPlaylists getSelectedPlaylist()
    {
        return selectedPlaylist;
    }


    public ObservableList<Song> getObservableSongs(String playlistName) throws Exception {
        songsToBeViewed.clear();
        songsToBeViewed.addAll(playlistManager.getAllPlaylistSongs(playlistName));
        return songsToBeViewed;
    }


    public void addSongToPlaylist(Song addedSong, String playlistName) throws Exception {
        Song song = playlistManager.addSongToPlaylist(addedSong, playlistName);
        songsToBeViewed.add(song);


    }
    public void deleteSongFromPlaylist(Song song, String playListName) throws Exception {
        playlistManager.deleteSongFromPlaylist(song, playListName);
        songsToBeViewed.remove(song);
    }


}
