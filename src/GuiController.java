import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GuiController
{
    private List<Sound> sounds;
    private List<SoundButton> soundButtons;
    private EditPrompt editPrompt;
    private Gui gui;

    public GuiController()
    {
        getSounds();
        getSoundButtons();
        this.gui = new Gui();
        addSoundListener();
        gui.display(soundButtons);
    }

    private void getSounds()
    {
        Connection con = ConnectionFactory.getInstance().getConnection();
        SoundJDBCDao soundJDBCDao = new SoundJDBCDao(con);
        this.sounds = soundJDBCDao.getSounds();
    }

    private void getSoundButtons()
    {
        soundButtons = new ArrayList<>();

        int rowNumber = 1;
        int columnNumber = 1;
        for (Sound sound : sounds)
        {
            SoundButton soundButton = new SoundButton(sound, rowNumber, columnNumber);
            soundButtons.add(soundButton);
            addSoundButtonListeners(soundButton);
            columnNumber ++;

            if(columnNumber == 4)
            {
                columnNumber = 1;
                rowNumber ++;
            }
        }
    }

    private void addSoundButtonListeners(SoundButton soundButton)
    {
        Connection con = ConnectionFactory.getInstance().getConnection();
        SoundJDBCDao soundJDBCDao = new SoundJDBCDao(con);

        soundButton.getDeleteButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                soundButton.getSound().delete();
                reload();
            }
        });

        soundButton.getFavoriteButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                soundButton.getSound().setFavorite(!soundButton.getSound().isFavorite());
                soundJDBCDao.updateSound(soundButton.getSound());
                reload();
            }
        });

        GuiController currentGuiController = this;

        soundButton.getEditButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                editPrompt = new EditPrompt(soundButton.getSound(), currentGuiController);
            }
        });

        soundButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                soundButton.getSound().playSound();
            }
        });
    }


    private void addSoundListener()
    {
        gui.getAddButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addSound();
            }
        });
    }

    private void addSound()
    {
        File file = FileChooser.getSound(gui.getFrame());
        if(file == null) { return; }
        String path = file.getAbsolutePath();
        if (path.endsWith(".wav"))
        {
            Sound sound = new Sound();
            sound.setTitle(file.getName().substring(0, file.getName().length() - 4));
            Connection con = ConnectionFactory.getInstance().getConnection();
            SoundJDBCDao soundJDBCDao = new SoundJDBCDao(con);

            int id = soundJDBCDao.insertSound(sound);
            sound.setId(id);
            String srcPath = file.getAbsolutePath();
            String desPath = "./sounds/" + sound.getId() + ".wav";

            Path source = Paths.get(srcPath);
            Path target = Paths.get(desPath);
            try
            {
                Files.copy(source, target, REPLACE_EXISTING);
                sound.playSound();
            } catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Beim hinzufügen des Sounds ist ein Fehler aufgetreten.");
                sound.delete();
                System.out.println("Error adding sound");
            }

            reload();
        }
        else {
            JOptionPane.showMessageDialog(null, "Die Datei muss eine \".wav\" datei sein!");
            addSound();
        }
    }

    public void reload()
    {
        getSounds();
        getSoundButtons();
        gui.display(soundButtons);
    }
}
