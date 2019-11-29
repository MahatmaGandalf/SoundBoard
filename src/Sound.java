import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import static javax.sound.sampled.AudioSystem.getClip;

public class Sound
{
    private String title;

    private Clip clip;
    private AudioInputStream inputStream;
    private int id;
    private boolean isFavorite;


    public void playSound()
    {
        String path = Util.pathToAbsolutePath("./sounds/" + this.id + ".wav");

        try
        {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
        try
        {
            //music = AddSound.toAbsolutePath(music);
            inputStream = AudioSystem.getAudioInputStream(new File(path));

        } catch (UnsupportedAudioFileException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            clip.open(inputStream);
        } catch (LineUnavailableException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        clip.start();
    }

    public void delete()
    {
        Connection con = ConnectionFactory.getInstance().getConnection();
        new SoundJDBCDao(con).deleteSound(this);
        File soundFile = new File(Util.pathToAbsolutePath("./sounds/" + this.id + ".wav"));
        File imageFile = new File(Util.pathToAbsolutePath("./pictures/" + this.id + ".png"));
        soundFile.delete();
        imageFile.delete();
    }

    public void update()
    {

        Connection con = ConnectionFactory.getInstance().getConnection();
        new SoundJDBCDao(con).updateSound(this);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
