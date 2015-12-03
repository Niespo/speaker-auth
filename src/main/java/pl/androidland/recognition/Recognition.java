package pl.androidland.recognition;

import com.bitsinharmony.recognito.MatchResult;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Recognition<T> {
    List<MatchResult<T>> recognize(File file) throws IOException, UnsupportedAudioFileException;

    void addVoice(T user, File voice) throws IOException, UnsupportedAudioFileException;
}
