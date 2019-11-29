import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoundJDBCDao implements SoundDao
{
    public Connection con = null;

    public SoundJDBCDao(Connection connection) {
        con = connection;
    }

    public List<Sound> getSounds()
    {
        List<Sound> sounds = new ArrayList<>();
        try
        {
            String sql = "SELECT isFavorite, title, id FROM sound ORDER BY isFavorite DESC, title ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int i = 1;
            if (rs.last())
            {
                i = rs.getRow();
                rs.beforeFirst();
            }

            while (rs.next())
            {
                Sound sound = new Sound();
                sound.setFavorite(rs.getBoolean(1));
                sound.setTitle(rs.getString(2));
                sound.setId(rs.getInt(3));
                sounds.add(sound);
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return sounds;
    }

    @Override
    public int insertSound(Sound sound)
    {
        try
        {
            String sql = "INSERT INTO sound (title, isFavorite) VALUES ((?), (?))";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sound.getTitle());
            ps.setBoolean(2, sound.isFavorite());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        int id = 0;
        try
        {
            String sql = "select LAST_INSERT_ID() as id";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                id = rs.getInt("id");
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return id;
    }


    public boolean isDouble(Sound sound)
    {
        try
        {
            String sql = "SELECT COUNT(title) as anzahl FROM sound where title = (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sound.getTitle());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                if(rs.getInt("anzahl")>0)
                {
                    return true;
                }

            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return false;
    }


    public void updateSound(Sound sound)
    {
        try
        {
            int isFavorite = sound.isFavorite() ? 1 : 0;
            PreparedStatement updateEXP = con.prepareStatement("UPDATE sound SET title='" + sound.getTitle() + "' , isFavorite='" + isFavorite + "' WHERE id ='" + sound.getId() + "'");
            updateEXP.executeUpdate();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteSound(Sound sound)
    {
        try
        {
            String SQL = "DELETE FROM sound WHERE id = (?)";
            PreparedStatement pstmt = null;
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, sound.getId());
            pstmt.executeUpdate();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
