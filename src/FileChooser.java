import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileChooser
{
    public static File getSound(JFrame parentFrame)
    {
        FileDialog fd = new FileDialog(parentFrame, "Lade eine Sounddatei (.wav)", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setVisible(true);

        if (fd.getFile() == null || fd.getFile().isEmpty())
        {
            return null;
        }

        String path = fd.getDirectory() + fd.getFile();
        return new File(path);

    }

    public static File getImage(JFrame parentFrame)
    {
        FileDialog fd = new FileDialog(parentFrame, "Lade eine Bilddatei (.png)", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setVisible(true);

        if (fd.getFile() == null || fd.getFile().isEmpty())
        {
            return null;
        }

        String path = fd.getDirectory() + fd.getFile();
        return new File(path);
    }
}
