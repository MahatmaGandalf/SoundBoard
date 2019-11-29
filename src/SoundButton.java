import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SoundButton extends JButton
{
    private Sound sound;
    private Icon icon;
    private JButton favoriteButton = new JButton("Favorite");
    private JButton editButton = new JButton("bearbeiten");
    private JButton deleteButton = new JButton("Entfernen");
    private int rowNumber;
    private int columnNumber;


    public SoundButton(Sound sound, int rowNumber, int columnNumber)
    {
        this.sound = sound;
        super.setText(sound.getTitle());
        setBGImage();
    }

    public void display(int x, int y)
    {
        int ButtonWidht = super.getWidth();
        int ButtonHeight = super.getHeight();

        deleteButton.setBounds(x + ButtonWidht/2, y + ButtonHeight, ButtonWidht/2, 20);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 2));
        deleteButton.setBackground(Color.decode("#960a0a"));
        deleteButton.setForeground(Color.decode("#FFFFFF"));
        deleteButton.setFocusable(false);


        editButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 2));
        editButton.setBounds(x, y + ButtonHeight+20, ButtonWidht, 20);
        editButton.setBackground(Color.decode("#960a0a"));
        editButton.setForeground(Color.decode("#FFFFFF"));
        editButton.setFocusable(false);

        favoriteButton.setBounds(x, y + ButtonHeight, ButtonWidht/2, 20);
        favoriteButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 2));
        favoriteButton.setBackground(Color.decode("#960a0a"));
        favoriteButton.setForeground(Color.decode("#FFFFFF"));
        favoriteButton.setFocusable(false);
    }

    private void setBGImage()
    {
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        File file;

        try { file = new File(Util.pathToAbsolutePath("./images/" + this.sound.getId() + ".png")); }
        catch(Exception e) { return; }

        if(file.exists())
        {
            if(sound.isFavorite())
            {
                super.setBorder(BorderFactory.createLineBorder(Color.decode("#FFD700"), 5));
            }

            BufferedImage image = new BufferedImage(Gui.BUTTONWIDTH, Gui.BUTTONHEIGHT, BufferedImage.TYPE_INT_ARGB);

            try
            {
                image = ImageIO.read(file);
            } catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Es gab ein Fehler beim laden des Bildes.");
                return;
            }

            Dimension imageSize = new Dimension(image.getWidth(), image.getHeight());
            Dimension boundary = new Dimension(Gui.BUTTONWIDTH, Gui.BUTTONHEIGHT);
            Dimension scaled = Util.getScaledDimension(imageSize, boundary);
            image = (Util.scaleImage(image, scaled.width, scaled.height));
            ImageIcon icon = new ImageIcon(image);
            super.setIcon(icon);
        }
    }


    public JButton getFavoriteButton() {
        return favoriteButton;
    }

    public void setFavoriteButton(JButton favoriteButton) {
        this.favoriteButton = favoriteButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Sound getSound()
    {
        return this.sound;
    }
}

