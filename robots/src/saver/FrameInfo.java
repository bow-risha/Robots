package saver;

public class FrameInfo
{
    public FrameInfo(int x, int y, int width, int height, boolean isMaximum, boolean isIcon)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isMaximum = isMaximum;
        this.isIcon = isIcon;
    }

    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final boolean isMaximum;
    public final boolean isIcon;
}