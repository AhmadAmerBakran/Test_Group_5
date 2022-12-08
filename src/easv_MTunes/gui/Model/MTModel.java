package easv_MTunes.gui.Model;

import java.sql.SQLException;

public class MTModel {
    private SongModel songModel;
    private AllPlaylistsModel allPlaylistsModel;
    private PlaylistModel playlistModel;

    public MTModel() throws Exception {
        songModel = new SongModel();
        allPlaylistsModel = new AllPlaylistsModel();
        playlistModel = new PlaylistModel();
    }

    public SongModel getSongModel()
    {
        return songModel;
    }

    public void setSongModel(SongModel songModel) {
        this.songModel = songModel;
    }

    public AllPlaylistsModel getAllPlaylistsModel() {
        return allPlaylistsModel;
    }

    public void setAllPlaylistsModel(AllPlaylistsModel allPlaylistsModel) {
        this.allPlaylistsModel = allPlaylistsModel;
    }

    public PlaylistModel getPlaylistModel() {
        return playlistModel;
    }

    public void setPlaylistModel(PlaylistModel playlistModel) {
        this.playlistModel = playlistModel;
    }
}
