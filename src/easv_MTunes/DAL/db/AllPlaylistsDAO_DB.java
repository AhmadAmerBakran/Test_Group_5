package easv_MTunes.DAL.db;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import easv_MTunes.BE.AllPlaylists;
import easv_MTunes.DAL.IAllPlaylistsDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllPlaylistsDAO_DB implements IAllPlaylistsDataAccess {

    private DBConnector dbConnector;

    public AllPlaylistsDAO_DB() {
        dbConnector = new DBConnector();
    }

    @Override
    public List<AllPlaylists> getAllPlaylists() throws SQLServerException {

        List<AllPlaylists> playLists = new ArrayList<>();
//Create an SQL command
        String sql = "SELECT * FROM AllPlaylists;";
        //Get connection to database
        try (Connection connection = dbConnector.getConnection())
        {



            //Create some statements
            Statement statement = connection.createStatement();

            //Do what you suppose to do
            if(statement.execute(sql))
            {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next())
                {
                    int playlistId = resultSet.getInt("Id");
                    String playlistName = resultSet.getString("Name");
                    int playlistSongsNumber = resultSet.getInt("Songs");


                    AllPlaylists allPlaylists = new AllPlaylists(playlistId, playlistName, playlistSongsNumber);
                    playLists.add(allPlaylists);
                }
            }
        } catch (SQLException e) {
        }
        return playLists;
    }

    @Override
    public AllPlaylists createPlaylist(String name) throws Exception {
        String sql = "INSERT INTO AllPlaylists (Name) VALUES (?);";

        String sql2 = "CREATE TABLE [dbo].[" + name + "] (\n" +
                "     \n" +
                "    [Id]  INT IDENTITY(1, 1) NOT NULL,\n" +
                "    [Title]  NVARCHAR (MAX) NOT NULL,\n" +
                "    [Artist]  NVARCHAR (MAX) ,\n" +
                "    [Path]   NVARCHAR (MAX) NOT NULL,\n" +
                "    " +
                ");";

        try (Connection connection = dbConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmt2 = connection.prepareStatement(sql2);



            // Bind parameters
            stmt.setString(1,name);



            // Run the specified SQL statement
            stmt.executeUpdate();
            stmt2.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            int songsNumber = 0;


            if (rs.next()) {
                id = rs.getInt(1);
                //songsNumber = rs.getInt("Songs");

            }
            // Create song object and send up the layers
            AllPlaylists allPlaylists = new AllPlaylists(id,name, songsNumber);
            return allPlaylists;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not create a Song", ex);
        }
    }

    @Override
    public void deletePlaylist(AllPlaylists deletedPlaylist) throws Exception {
        String sql = "DELETE FROM AllPlaylists WHERE Name = (?) AND Id = (?);";
        String sql1 = "DROP TABLE [" + deletedPlaylist.getPlaylistName() + "];";

        try (Connection conn = dbConnector.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);
            PreparedStatement stmt1 = conn.prepareStatement(sql1);

            // Bind parameters
            stmt.setString(1, deletedPlaylist.getPlaylistName());
            stmt.setInt(2, deletedPlaylist.getPlaylistId());


            stmt.executeUpdate();
            stmt1.executeUpdate();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception( ex);
        }


    }


    @Override
    public void updatePlaylist(AllPlaylists updatedPlaylist) throws Exception {
        try (Connection connection = dbConnector.getConnection()) {

            String sql = "UPDATE AllPlaylists SET Name = ? WHERE Id = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            // Bind parameters
            stmt.setString(1, updatedPlaylist.getPlaylistName());
            stmt.setInt(2, updatedPlaylist.getPlaylistId());

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not update playlist", ex);
        }

    }
}
