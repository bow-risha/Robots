package saver;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.io.*;

public class PaneSaver {
    private static final String pathToSaves = System.getProperty("user.home");

    public void SavePaneState(JDesktopPane desktopPane) {
        var file = new File(pathToSaves + "\\all_frames.txt");
        try {
            if (!file.exists())
                file.createNewFile();

            var outputStream = new FileOutputStream(file);
            var dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));

            for (var frame : desktopPane.getAllFrames()) {
                dataOutputStream.writeUTF(frame.getTitle());
                dataOutputStream.writeInt(frame.getX());
                dataOutputStream.writeInt(frame.getY());
                dataOutputStream.writeInt(frame.getWidth());
                dataOutputStream.writeInt(frame.getHeight());
                dataOutputStream.writeBoolean(frame.isMaximum());
                dataOutputStream.writeBoolean(frame.isIcon());
            }

            dataOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
    }

    public static HashMap<String, FrameInfo> LoadPaneState() {
        var frameStates = new HashMap<String, FrameInfo>();
        var file = new File(pathToSaves + "\\all_frames.txt");
        try {
            if (!file.exists())
                return frameStates;

            var inputStream = new FileInputStream(file);
            var dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));

            while (dataInputStream.available() > 0) {
                var title = dataInputStream.readUTF();
                var x = dataInputStream.readInt();
                var y = dataInputStream.readInt();
                var width = dataInputStream.readInt();
                var height = dataInputStream.readInt();
                var isMaximum = dataInputStream.readBoolean();
                var isIcon = dataInputStream.readBoolean();
                frameStates.put(title, new FrameInfo(x, y, width, height, isMaximum, isIcon));
            }

            dataInputStream.close();
            inputStream.close();
        } catch (Exception e) {

        }

        return frameStates;
    }


    public void ApplySavedState(JDesktopPane desktopPane) {
        HashMap<String, FrameInfo> frameStates = LoadPaneState();
        for (var frame : desktopPane.getAllFrames()) {
            if (!frameStates.containsKey(frame.getTitle()))
                continue;

            var newFrameState = frameStates.get(frame.getTitle());
            frame.setLocation(new Point(newFrameState.x, newFrameState.y));
            frame.setSize(newFrameState.width, newFrameState.height);
            try {
                frame.setIcon(newFrameState.isIcon);
                frame.setMaximum(newFrameState.isMaximum);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }
}