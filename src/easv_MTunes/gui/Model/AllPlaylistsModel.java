package easv_MTunes.gui.Model;

import easv_MTunes.BE.AllPlaylists;
import easv_MTunes.BE.Song;
import easv_MTunes.BLL.AllPlaylistsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;


public class AllPlaylistsModel {
    private AllPlaylistsManager allPlaylistsManager;
    private ObservableList<AllPlaylists> allPlaylistsToBeViewed;
    private AllPlaylists selectedPlaylist;

    public AllPlaylists getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(AllPlaylists selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    public AllPlaylistsModel() throws SQLException {
        allPlaylistsManager = new AllPlaylistsManager();
        allPlaylistsToBeViewed = FXCollections.observableArrayList();
        allPlaylistsToBeViewed.addAll(allPlaylistsManager.getAllPlaylists());
    }
    public ObservableList<AllPlaylists> getObservableAllPlaylists()
    {
        return allPlaylistsToBeViewed;
    }
    public void deletePlaylist(AllPlaylists deletedPlaylist) throws Exception {
        allPlaylistsManager.deletePlaylist(deletedPlaylist);
        allPlaylistsToBeViewed.remove(deletedPlaylist);
    }
    public void updatePlaylist(AllPlaylists updatedPlaylist) throws Exception {
        allPlaylistsManager.updatePlaylist(updatedPlaylist);

        // update ListView
        allPlaylistsToBeViewed.clear();
        allPlaylistsToBeViewed.addAll(allPlaylistsManager.getAllPlaylists());
    }
    public void createNewPlaylist(String name) throws Exception {
        AllPlaylists allPlaylists = allPlaylistsManager.createNewPlaylist(name);
        allPlaylistsToBeViewed.add(allPlaylists);

    }

}
