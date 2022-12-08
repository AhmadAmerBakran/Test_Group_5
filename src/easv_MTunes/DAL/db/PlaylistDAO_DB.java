package easv_MTunes.DAL.db;


import easv_MTunes.BE.Song;
import easv_MTunes.DAL.IPlaylistDataAccess;
import easv_MTunes.DAL.ISongDataAccess;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class PlaylistDAO_DB implements IPlaylistDataAccess {
    private DBConnector dbConnector;

    public PlaylistDAO_DB() {
        dbConnector = new DBConnector();
    }

    public ArrayList<Song> getAllPlaylistSongs(String playlistName) throws SQLException {
        //Create and return songs
        ArrayList<Song> allSongList = new ArrayList<>();

        //Get connection to database
        try (Connection connection = dbConnector.getConnection())
        {
            //Create an SQL command
            String sql = "SELECT * FROM [" + playlistName + "];";

            //Create some statements
            Statement statement = connection.createStatement();

            //Do what you suppose to do
            if(statement.execute(sql))
            {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next())
                {
                    int id = resultSet.getInt("Id");
                    String title = resultSet.getString("Title");
                    String artist = resultSet.getString("Artist");
                    String songPath = resultSet.getString("Path");
                    File songFile = new File(songPath);

                    Song song = new Song(id, title,artist, songFile);
                    allSongList.add(song);
                }
            }
        }
        return allSongList;
    }

    public Song addSongToPlaylist(Song addedSong, String playlistName) throws Exception {
        // Dynamic SQL


        String sql = "INSERT INTO [" + playlistName + "] (Title,Artist,Path) VALUES (?,?,?);";

        try (Connection connection = dbConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String songPath = addedSong.getSongFile().toString();
            String title = addedSong.getTitle();
            String artist = addedSong.getArtist();

            // Bind parameters
            stmt.setString(1,title);
            stmt.setString(2, artist);
            stmt.setString(3, songPath);

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;


            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create song object and send up the layers
            Song song = new Song(id, title, artist, addedSong.getSongFile());
            return song;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not create a Song", ex);
        }

    }



    public void deleteSongFromPlaylist(Song deletedSong, String playlistName) throws Exception {

        try (Connection conn = dbConnector.getConnection()) {

            String sql = "DELETE FROM [" + playlistName + "] WHERE Title = (?) AND Artist = (?);";


            PreparedStatement stmt = conn.prepareStatement(sql);

            // Bind parameters
            stmt.setString(1, deletedSong.getTitle());
            stmt.setString(2, deletedSong.getArtist());


            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception( ex);
        }


    }
}
