package minesweeper.view;

import java.net.URL;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundMixer {
    private static Clip loadClip(final String path) {
        try {
            URL url = Panel.class.getClassLoader().getResource(path);
            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch(Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static final Map<SoundEvent, Clip> CLIPS = Map.ofEntries(
        Map.entry(SoundEvent.PLACED_FLAG, loadClip("sounds/flag.wav")),
        Map.entry(SoundEvent.REMOVED_FLAG, loadClip("sounds/flag.wav")),
        Map.entry(SoundEvent.REVEALED_CELL, loadClip("sounds/reveal.wav")),
        Map.entry(SoundEvent.REVEALED_MINE, loadClip("sounds/mine.wav"))
    );

    public static void playSound(SoundEvent sound) {
        Clip clip = CLIPS.get(sound);
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
