import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class EditPrompt
{
    private Sound sound;
    private JFrame editFrame = new JFrame("Bearbeiten");
    private GuiController guiController;

    private JButton saveButton = new JButton("Speichern");
    private JButton cancelButton = new JButton("Abbrechen");
    private JButton pictureButton = new JButton("Bild hinzufügen");
    private JCheckBox isFavoriteBox = new JCheckBox("Ist ein Favorit");
    private JTextField titleTextField;

    public EditPrompt(Sound sound, GuiController guiController)
    {
        this.sound = sound;
        this.guiController = guiController;
        this.display();
    }


    private void setTextFieldSettings() {
        this.titleTextField = new JTextField(sound.getTitle());
        this.isFavoriteBox.setSelected(sound.isFavorite());

        this.titleTextField.setBounds(20, 20, 200, 30);
        this.titleTextField.setBackground(Color.decode("#ffffff"));

        this.isFavoriteBox.setBounds(20, 55, 200, 30);

        this.pictureButton.setBounds(20, 90, 200, 30);
        this.pictureButton.setBackground(Color.decode("#FFD700"));

        this.saveButton.setBounds(20, 130, 200, 30);
        this.saveButton.setBackground(Color.decode("#FFD700"));

        this.cancelButton.setBounds(20, 170, 200, 30);
        this.cancelButton.setBackground(Color.decode("#FFD700"));


        this.editFrame.setLayout(null);

        this.editFrame.add(titleTextField);
        this.editFrame.add(isFavoriteBox);
        this.editFrame.add(pictureButton);
        this.editFrame.add(saveButton);
        this.editFrame.add(cancelButton);
        this.editFrame.repaint();
    }


    private void addButtonListeners()
    {
        this.pictureButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addImage();
            }
        });

        this.saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) { save(); }
        });

        this.cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) { close(); }
        });
    }


    private void addImage()
    {
        File file = FileChooser.getImage(editFrame);
        if(file == null) { return; }
        String path = file.getAbsolutePath();
        if (path.endsWith(".png"))
        {
            String desPath = "./images/" + sound.getId() + ".png";

            Path source = Paths.get(path);
            Path target = Paths.get(desPath);
            try { Files.copy(source, target, REPLACE_EXISTING); }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Es gab ein Fehler beim hinzufügen des Bildes.");
                System.out.println("Error adding image");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Es gab ein Fehler beim laden des Bildes.");
            System.out.println("Error: File does not end with .png");
            addImage();
        }
    }

    private void display()
    {
        this.editFrame.setSize(260, 260);
        this.editFrame.setResizable(false);
        this.editFrame.setBackground(Color.decode("#383838"));
        this.editFrame.setLocationRelativeTo(null);

        this.setTextFieldSettings();
        this.addButtonListeners();
        this.editFrame.setVisible(true);
    }

    private void save()
    {
        sound.setFavorite(isFavoriteBox.isSelected());
        if(titleTextField.getText() != "")
        {
            sound.setTitle(titleTextField.getText());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Der Titel darf nicht leer sein.");
            return;
        }
        sound.update();
        close();
    }

    private void close()
    {
        this.editFrame.setVisible(false);
        guiController.reload();
    }
}
