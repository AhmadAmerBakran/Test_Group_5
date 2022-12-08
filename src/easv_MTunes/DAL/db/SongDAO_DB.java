package easv_MTunes.DAL.db;


import easv_MTunes.BE.Song;
import easv_MTunes.DAL.ISongDataAccess;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class SongDAO_DB implements ISongDataAccess {
    private DBConnector dbConnector;

    public SongDAO_DB() {
        dbConnector = new DBConnector();
    }

    @Override
    public ArrayList<Song> getAllSongs() throws SQLException {
        //Create and return songs
        ArrayList<Song> allSongList = new ArrayList<>();

        //Get connection to database
        try (Connection connection = dbConnector.getConnection())
        {
            //Create an SQL command
            String sql = "SELECT * FROM Song;";

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

    @Override
    public Song createSong(String title, String artist, File songFile) throws Exception {
        // Dynamic SQL


        String sql = "INSERT INTO Song (Title,Artist,Path) VALUES (?,?,?);";

        try (Connection connection = dbConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String songPath = songFile.toString();

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
            Song song = new Song(id, title, artist, songFile);
            return song;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not create a Song", ex);
        }

    }

    @Override
    public void updateSong(Song song) throws Exception {
        try (Connection connection = dbConnector.getConnection()) {

            String sql = "UPDATE Song SET Title = ?, Artist = ? WHERE Id = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            // Bind parameters
            stmt.setString(1, song.getTitle());
            stmt.setString(2, song.getArtist());
            stmt.setInt(3, song.getId());

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not update movie", ex);
        }
    }

    @Override
    public void deleteSong(Song song) throws Exception {

        try (Connection conn = dbConnector.getConnection()) {

            String sql = "DELETE FROM Song WHERE Title = (?) AND Artist = (?);";


            PreparedStatement stmt = conn.prepareStatement(sql);

            // Bind parameters
            stmt.setString(1, song.getTitle());
            stmt.setString(2, song.getArtist());


            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception( ex);
        }


    }
}
