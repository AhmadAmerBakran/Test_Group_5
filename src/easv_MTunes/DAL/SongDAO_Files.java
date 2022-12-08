package easv_MTunes.DAL;

import easv_MTunes.BE.Song;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_Files implements ISongDataAccess {

    private File directory = new File("SongFiles");
    private File [] files;

    //Getting songs from "SongFiles" NOT DATABASE
    @Override
    public ArrayList<Song> getAllSongs() throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        List<String> allSongsInfo = new ArrayList<>();
        List<File> songFiles = new ArrayList<>();
        int i = -1;


        files = directory.listFiles();
        if(files != null)
        {
            for (File file: files) {
                allSongsInfo.add(file.toString().substring(10));
                songFiles.add(file);
                
            }
        }
        for(String singleSong:allSongsInfo)
        {
            String[] singleSongInfo = singleSong.split(",");
            i++;
            int id = Integer.parseInt(singleSongInfo[0]);
            String title = singleSongInfo[1];
            String artist = singleSongInfo[2];
            File songFile = files[i];

            Song newSong = new Song(id, title, artist, songFile);
            songs.add(newSong);

        }
        return songs;
    }

    @Override
    public Song createSong(String title, String artist, File songFile) throws Exception {
        return null;
    }


    @Override
    public void updateSong(Song song) {

    }

    @Override
    public void deleteSong(Song song) {

    }

}
