package easv_MTunes.gui.Controller;

import easv_MTunes.BE.AllPlaylists;
import easv_MTunes.BE.Song;
import easv_MTunes.gui.Model.AllPlaylistsModel;
import easv_MTunes.gui.Model.PlaylistModel;
import easv_MTunes.gui.Model.SongModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlaylistsView extends ControllerManager {

    @FXML
    TextField txtPlaylistName;
    private AllPlaylistsModel allPlaylistsModel;
    private SongModel songModel;
    private PlaylistModel playlistModel;

    public PlaylistsView() {
        try {
           allPlaylistsModel = new AllPlaylistsModel();
           playlistModel = new PlaylistModel();
           songModel = new SongModel();
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public void updatePlaylist(ActionEvent actionEvent) throws Exception {
        String updatedPlaylistName = txtPlaylistName.getText();
        AllPlaylists updatedPlaylist = new AllPlaylists(allPlaylistsModel.getSelectedPlaylist().getPlaylistId(), updatedPlaylistName, allPlaylistsModel.getSelectedPlaylist().getPlaylistSongsNumber());
        allPlaylistsModel.updatePlaylist(updatedPlaylist);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void savePlaylist(ActionEvent actionEvent) {

        String playlistName = txtPlaylistName.getText();
        Song song =songModel.getSelectedSong();

        try {

            allPlaylistsModel.createNewPlaylist(playlistName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void cancelEditting(ActionEvent actionEvent) {
    }

    @Override
    public void setup() {
        allPlaylistsModel = getModel().getAllPlaylistsModel();

        txtPlaylistName.setText(allPlaylistsModel.getSelectedPlaylist().getPlaylistName());

    }
}
