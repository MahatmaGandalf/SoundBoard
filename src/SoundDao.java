import java.util.List;

public abstract interface SoundDao
{
    public abstract List<Sound> getSounds();
    public abstract int insertSound(Sound sound);
    public abstract boolean isDouble(Sound sound);
    public abstract void updateSound(Sound sound);
    public abstract void deleteSound(Sound sound);
}
