package com.samyak.components;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MediaPlayer {
    private JFrame frame;
    private JButton playButton;
    private JButton pauseButton;
    private JButton rewindButton;
    private JButton skipButton;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public MediaPlayer(PlayButton playButton) {
        new NativeDiscovery().discover();
        this.playButton = playButton;

        // added a new window listener that invokes mediaPlayerComponent.release() to release the media player component and associated native resources, before exiting the application
        frame = new JFrame(playButton.getVideoName());
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                frame.dispose();
            }
        });


        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch( keyCode ) {
                    case KeyEvent.VK_SPACE:
                        pauseButton.doClick();
                        break;
                    case KeyEvent.VK_LEFT:
                        rewindButton.doClick();
                        break;
//                    case KeyEvent.VK_RIGHT :
//                        skipButton.doClick();
//                        break;
                }
                mediaPlayerComponent.getVideoSurface().requestFocusInWindow();
            }
        };
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        JPanel controlsPane = new JPanel();
        pauseButton = new JButton("Pause");
        pauseButton.setFocusable(false);
        controlsPane.add(pauseButton);
        rewindButton = new JButton("Rewind");
        rewindButton.setFocusable(false);
        controlsPane.add(rewindButton);
//        skipButton = new JButton("Skip");
//        skipButton.setFocusable(false);
//        controlsPane.add(skipButton);
        contentPane.add(controlsPane, BorderLayout.SOUTH);
        // implementing listeners on buttons
        pauseButton.addActionListener(e -> mediaPlayerComponent.getMediaPlayer().pause());
        rewindButton.addActionListener(e -> mediaPlayerComponent.getMediaPlayer().skip(-10000));
//        skipButton.addActionListener(e -> mediaPlayerComponent.getMediaPlayer().skip(10000));

        // event handling on media player
        mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(() -> closeWindow());
            }

            @Override
            public void error(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Failed to play media",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    closeWindow();
                });
            }
        });

        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mediaPlayerComponent.getMediaPlayer().playMedia("http://localhost/" + playButton.getVideoPath());
    }

    private void closeWindow() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
