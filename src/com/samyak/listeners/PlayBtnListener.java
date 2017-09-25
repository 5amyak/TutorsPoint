package com.samyak.listeners;

import com.samyak.components.MediaPlayer;
import com.samyak.components.PlayButton;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayBtnListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> new MediaPlayer((PlayButton)e.getSource()));

    }
}
