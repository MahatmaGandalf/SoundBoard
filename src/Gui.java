import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Gui
{
    private JFrame frame;


    private JPanel soundPanel = new JPanel();
    private JButton addButton = new JButton("Hinzufügen");
    private JScrollPane scrollPane = new JScrollPane(soundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private int x = 50;
    private int y = 50;
    private int scrollpaneHeight;

    public static int BUTTONWIDTH = 467;
    public static int BUTTONHEIGHT = 200;
    public static final int BACKBUTTONHEIGHT = 35;
    public static final int BACKBUTTONWIDTH = 200;


    public Gui()
    {
        this.frame = new JFrame("Sound Board");
    }


    public void display(List<SoundButton> soundButtons)
    {
        this.frame.remove(soundPanel);
        this.frame.remove(scrollPane);
        this.soundPanel = new JPanel();
        this.scrollPane = new JScrollPane(soundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.frame.setSize(1600, 900);
        this.frame.setResizable(false);
        addButtons(soundButtons);
        this.frame.repaint();
        this.frame.setLocationRelativeTo(null);
        this.frame.setIconImage(new ImageIcon(Util.pathToAbsolutePath("./res/Icon.png")).getImage());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.frame.setVisible(true);
        this.frame.repaint();
    }


    private void addButtons(List<SoundButton> soundButtons)
    {
        x = 50;
        y = 50;
        scrollPane.removeAll();
        frame.setBackground(Color.BLACK);
        soundPanel = new JPanel();
        soundPanel.setLayout(null);
        soundPanel.setBackground(Color.decode("#383838"));

        if (soundButtons.size() % 3 != 0) {
            scrollpaneHeight = ((BUTTONHEIGHT + 50) * ((int) (soundButtons.size() / 3) + 1) + 50);
        } else {
            scrollpaneHeight = ((BUTTONHEIGHT + 50) * (soundButtons.size() / 3) + 50);
        }
        soundPanel.setPreferredSize(new Dimension(1600, scrollpaneHeight));

        for (SoundButton button : soundButtons) {

            if (x > 3 * (BUTTONWIDTH + 50)) {
                x = 50; // x auf standardabstand setzen
                y += 50 + BUTTONHEIGHT; // 2mal lücke zwischen Bild + Bildbreite
            }

            button.setBounds(x, y, Gui.BUTTONWIDTH, Gui.BUTTONHEIGHT);

            if(button.getSound().isFavorite())
            {
                button.setBackground(Color.decode("#FFD700"));
            }
            else
            {
                button.setBackground(Color.WHITE);
            }

            button.display(x, y);

            soundPanel.add(button);
            soundPanel.add(button.getFavoriteButton());
            soundPanel.add(button.getDeleteButton());
            soundPanel.add(button.getEditButton());


            x += 50 + BUTTONWIDTH; // 2mal lücke zwischen Bild + Bildbreite
        }

        scrollPane = new JScrollPane(soundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set höhe damit der Button sichbar ist
        soundPanel.setPreferredSize(new Dimension(scrollPane.getWidth(), scrollpaneHeight + 70));

        // addet scrollpane dem frame
        this.frame.add(scrollPane);

        // setzt die srollgeschwindigkeit
        scrollPane.getVerticalScrollBar().setUnitIncrement(79);

        addButton.setBackground(Color.decode("#960a0a"));
        addButton.setForeground(Color.WHITE);
        addButton.setLocation((this.frame.getWidth() / 2) - (BACKBUTTONWIDTH / 2), y + BUTTONHEIGHT + 50);
        addButton.setSize(BACKBUTTONWIDTH, BACKBUTTONHEIGHT);
        soundPanel.add(addButton);
    }

    public JFrame getFrame()
    {
        return this.frame;
    }


    public JButton getAddButton()
    {
        return this.addButton;
    }
}
