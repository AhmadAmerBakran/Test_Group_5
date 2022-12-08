package easv_MTunes.gui.Controller;

import easv_MTunes.BE.AllPlaylists;
import easv_MTunes.BE.Playlist;
import easv_MTunes.BE.Song;
import easv_MTunes.gui.Model.AllPlaylistsModel;
import easv_MTunes.gui.Model.PlaylistModel;
import easv_MTunes.gui.Model.SongModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;


public class SongViewController extends ControllerManager implements Initializable {

    @FXML
    private TableColumn<AllPlaylists, Integer> cPListsSongs;
    @FXML
    private TableColumn<AllPlaylists, String> cPListsName;
    @FXML
    private TableView<AllPlaylists> pListsTable;
    @FXML
    private TableColumn<Song, Integer> cTime;
    @FXML
    private TableColumn<Song, String> cArtist;
    @FXML
    private TableColumn<Song, String> cTitle;
    @FXML
    private TableView<Song> songTable;
    @FXML
    private TableView<Song>  pListTable;
    @FXML
    private TableColumn<Playlist, Integer> cSongId;
    @FXML
    private TableColumn<Playlist, String> cSongTitle;
    @FXML
    private Label playedSong;
    @FXML
    private ImageView imgPlay;
    @FXML
    private ImageView soundOn;
    @FXML
    private Button btnVolume;
    @FXML
    Button btnPlay;
    @FXML
    private Slider slideVolume, slideTime;
    @FXML
    private Label lblStart, lblEnd;
    @FXML
    private TextField searchField;

    private Media media;
    private MediaPlayer mediaPlayer;
    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean playClicked = true;
    private boolean muteClicked = true;


    private SongModel songModel;
    private AllPlaylistsModel allPlaylistsModel;
    private PlaylistModel playlistModel;

    public SongViewController() {
        try {
            songModel = new SongModel();
            allPlaylistsModel = new AllPlaylistsModel();
            playlistModel = new PlaylistModel();
        }catch (Exception e){

            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        media = new Media(songModel.getObservableSongs().get(songNumber).getSongFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //showAllSongsAndPlaylists();
        volumeSlider();
        timeSlider();

    }
    @Override
    public void setup() throws Exception {
        songModel = getModel().getSongModel();
        allPlaylistsModel = getModel().getAllPlaylistsModel();
        showAllSongsAndPlaylists();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                songModel.searchSong(newValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
    public void play(ActionEvent actionEvent) {
        if(playClicked)
        {
            Image playing = new Image("/easv_MTunes/images/pause_button_96px.png");
            imgPlay.setImage(playing);
            playFunctions();
            mediaPlayer.play();
            playClicked = false;
        } else if (!playClicked) {
            Image pausing = new Image("/easv_MTunes/images/play_96px.png");
            imgPlay.setImage(pausing);
            mediaPlayer.pause();
            playClicked = true;

        }

    }
    public void showAllSongsAndPlaylists() throws Exception {
        songTable.setItems(songModel.getObservableSongs());
        cTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        cArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));


        pListsTable.setItems(allPlaylistsModel.getObservableAllPlaylists());
        cPListsName.setCellValueFactory(new PropertyValueFactory<AllPlaylists, String>("playlistName"));
        cPListsSongs.setCellValueFactory(new PropertyValueFactory<AllPlaylists, Integer>("playlistSongsNumber"));

        AllPlaylists selectedPlaylist = pListsTable.getSelectionModel().getSelectedItem();
        if(selectedPlaylist != null){

        pListTable.setItems(playlistModel.getObservableSongs(selectedPlaylist.getPlaylistName()));
        cSongId.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("id"));
        cSongTitle.setCellValueFactory(new PropertyValueFactory<Playlist, String>("title"));}



    }




    public void playNext(ActionEvent actionEvent) {

        if(songNumber < songModel.getObservableSongs().size()-1)

        {
            mediaPlayer.stop();
            songNumber++;
            playNextOrPrev();
            playFunctions();

        }else
        {
            mediaPlayer.stop();
            songNumber = 0;
            playNextOrPrev();
            playFunctions();
        }

    }


    public void playPrevious(ActionEvent actionEvent) {
        if(songNumber > 0)
        {
            mediaPlayer.stop();
            songNumber--;
            playNextOrPrev();
            playFunctions();
        } else
        {
            mediaPlayer.stop();
            songNumber = songModel.getObservableSongs().size()-1;
            playNextOrPrev();
            playFunctions();
        }
    }


    public void stopPlaying(ActionEvent actionEvent) {
        mediaPlayer.seek(Duration.seconds(0));
        mediaPlayer.stop();
        playClicked = false;
        if(playClicked == false){
        Image pausing = new Image("/easv_MTunes/images/play_96px.png");
        imgPlay.setImage(pausing);
        playClicked = true;
        }
    }


    public void getCurrentTimeSlider()
    {
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                slideTime.setMax(mediaPlayer.getTotalDuration().toSeconds());
                double current = mediaPlayer.getCurrentTime().toSeconds();
                slideTime.setValue(current);
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }


    private void bindCurrentTimeLabel()
    {
        lblStart.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mediaPlayer.getCurrentTime()) + " / ";
            }
        }, mediaPlayer.currentTimeProperty()));


    }
    public void playNextOrPrev()
    {
        media = new Media(songModel.getObservableSongs().get(songNumber).getSongFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        Image playing = new Image("/easv_MTunes/images/pause_button_96px.png");
        imgPlay.setImage(playing);
        playClicked = false;
    }

    private void bindTotalTimeLabel()
    {
        lblEnd.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mediaPlayer.getTotalDuration()) + " / ";
            }
        }, mediaPlayer.currentTimeProperty()));


    }
    private String getTime(Duration time)
    {

        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();
        if(seconds > 59)
            seconds = seconds % 60;
        if(minutes > 59)
            minutes = minutes % 60;
        if(hours > 59)
            hours = hours % 60;
        if(hours > 0)
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        else return String.format("%02d:%02d", minutes, seconds);
    }

    private void playFunctions()
    {
        bindCurrentTimeLabel();
        bindTotalTimeLabel();
        if(!muteClicked)
        {
            mediaPlayer.setVolume(0);
        }else
        {
            mediaPlayer.setVolume(slideVolume.getValue() * 0.01);
        }
        getCurrentTimeSlider();
        playedSong.setText(songModel.getObservableSongs().get(songNumber).toString().substring(7));



    }
    private void volumeSlider()
    {
        slideVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(slideVolume.getValue() * 0.01);
                Image unMuted = new Image("/easv_MTunes/images/voice_96px.png");
                soundOn.setImage(unMuted);
                muteClicked = true;
            }
        });

    }
    private void timeSlider()
    {

        slideTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    slideTime.setMax(mediaPlayer.getTotalDuration().toSeconds());

                    mediaPlayer.seek(Duration.seconds(slideTime.getValue()));
                }
            }
        });
        slideTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                if(Math.abs(currentTime - newValue.doubleValue()) > 0.5)
                {
                    mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
                }
            }
        });
    }

    public void muteVolume(ActionEvent actionEvent) {
        if(muteClicked)
        {
            mediaPlayer.setVolume(0);
            Image mute = new Image("/easv_MTunes/images/mute_96px.png");
            soundOn.setImage(mute);

            muteClicked = false;

        } else if (!muteClicked) {
            mediaPlayer.setVolume(slideVolume.getValue() * 0.01);
            Image unMuted = new Image("/easv_MTunes/images/voice_96px.png");
            soundOn.setImage(unMuted);
            muteClicked = true;

        }
    }

    public void editSong(ActionEvent actionEvent) throws IOException {
        Song selectedSong = songTable.getSelectionModel().getSelectedItem();
        if(selectedSong==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Needed Info");
            alert.setHeaderText("Please choose the song you would like to edit...");
            alert.show();
        }else{
        songModel.setSelectedSong(selectedSong);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/easv_MTunes/gui/View/SongCrud.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        SongCrud songCrud = loader.getController();
        songCrud.setModel(super.getModel());
        songCrud.setup();


        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Edit Song");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();}



    }
    /*public void editAndAddWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/easv_MTunes/gui/View/SongCrud.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("EditAdd");
        stage.show();
        stage.setResizable(false);
    }*/

    public void addSong(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/easv_MTunes/gui/View/SongCrud.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        SongCrud songCrud = loader.getController();
        songCrud.setModel(super.getModel());
        showAllSongsAndPlaylists();
        //songCrud.setup();


        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Add Song");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();

    }
    public Song getSelectedSong()
    {
        Song song;
        song = songTable.getSelectionModel().getSelectedItem();
        return song;
    }
    public AllPlaylists getSelectedplaylist()
    {
        AllPlaylists selectedPlaylist;
        selectedPlaylist = pListsTable.getSelectionModel().getSelectedItem();
        return selectedPlaylist;
    }

    public void deleteSong(ActionEvent actionEvent) throws Exception {
        Song selectedSong = songTable.getSelectionModel().getSelectedItem();
        if(selectedSong==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Needed Info");
            alert.setHeaderText("Please choose the song you would like to delete...");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedSong.getTitle().concat( " ?"));
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK)
            {
                songModel.deleteSong(getSelectedSong());
            }
        }
    }


    public void addPlaylist(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/easv_MTunes/gui/View/PlaylistsView.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        PlaylistsView playlistsView = loader.getController();
        playlistsView.setModel(super.getModel());
        showAllSongsAndPlaylists();
        //playlistsView.setup();


        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("New Playlist");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();
    }

    public void editPlaylist(ActionEvent actionEvent) throws IOException {
        AllPlaylists selectedPlaylist = pListsTable.getSelectionModel().getSelectedItem();
        if(selectedPlaylist==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Needed Info");
            alert.setHeaderText("Please choose the playlist you would like to edit...");
            alert.show();
        }else{
            allPlaylistsModel.setSelectedPlaylist(selectedPlaylist);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/easv_MTunes/gui/View/PlaylistsView.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            PlaylistsView playlistsView = loader.getController();
            playlistsView.setModel(super.getModel());
            playlistsView.setup();


            Stage dialogWindow = new Stage();
            dialogWindow.setTitle("Edit Song");
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);

            dialogWindow.showAndWait();}

    }

    public void deletePlaylist(ActionEvent actionEvent) {
        AllPlaylists selectedPlaylist = pListsTable.getSelectionModel().getSelectedItem();
        if(selectedPlaylist==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Needed Info");
            alert.setHeaderText("Please choose the playlist you would like to delete...");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedPlaylist.getPlaylistName().concat( " ?"));
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK)
            {
                try {
                    allPlaylistsModel.deletePlaylist(getSelectedplaylist());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void deleteFromPlaylist(ActionEvent actionEvent) {
        Song song = pListTable.getSelectionModel().getSelectedItem();
        AllPlaylists selectedPlaylist = pListsTable.getSelectionModel().getSelectedItem();

        try {
            if(selectedPlaylist !=null)
            playlistModel.deleteSongFromPlaylist(song, selectedPlaylist.getPlaylistName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addSongToPlaylist(ActionEvent actionEvent) {
        Song song = songTable.getSelectionModel().getSelectedItem();
        AllPlaylists selectedPlaylist = pListsTable.getSelectionModel().getSelectedItem();
        try {
            if(selectedPlaylist !=null)
            playlistModel.addSongToPlaylist(song, selectedPlaylist.getPlaylistName());
        } catch (Exception e) {
           // throw new RuntimeException(e);
        }
    }
}
