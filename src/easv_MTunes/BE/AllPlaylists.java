package easv_MTunes.BE;


public class AllPlaylists {
    private String playlistName;
    private int playlistId;
    private Playlist playlist;
    private int playlistSongsNumber;

    public AllPlaylists(int playlistId, String playlistName, int playlistSongsNumber){

        this.playlistName = playlistName;
        //playlistSongsNumber = playlist.getPlaylistSongs().size();
        this.playlistSongsNumber = playlistSongsNumber;
        this.playlistId = playlistId;

    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getPlaylistId() {
        return playlistId;
    }


    public int getPlaylistSongsNumber() {
        return playlistSongsNumber;
    }


}
