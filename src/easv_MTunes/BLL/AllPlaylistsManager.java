package easv_MTunes.BLL;

import easv_MTunes.BE.AllPlaylists;
import easv_MTunes.BE.Song;
import easv_MTunes.DAL.IAllPlaylistsDataAccess;
import easv_MTunes.DAL.db.AllPlaylistsDAO_DB;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AllPlaylistsManager {

    IAllPlaylistsDataAccess allPlaylistsDAO_DB;

    public AllPlaylistsManager() {
        allPlaylistsDAO_DB = new AllPlaylistsDAO_DB();
    }
    public AllPlaylists createNewPlaylist(String name) throws Exception {
        return allPlaylistsDAO_DB.createPlaylist(name);
    }

    public List<AllPlaylists> getAllPlaylists() throws SQLException {
        return allPlaylistsDAO_DB.getAllPlaylists();
    }

    public void deletePlaylist (AllPlaylists deletedPlaylist) throws Exception {
        allPlaylistsDAO_DB.deletePlaylist(deletedPlaylist);
    }
    public void updatePlaylist(AllPlaylists updatedPlaylist) throws Exception {
        allPlaylistsDAO_DB.updatePlaylist(updatedPlaylist);
    }
}
