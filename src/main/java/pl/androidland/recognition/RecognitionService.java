package pl.androidland.recognition;

import com.bitsinharmony.recognito.MatchResult;
import com.bitsinharmony.recognito.Recognito;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "recognitionService")
public class RecognitionService implements Recognition<String> {
    private static final Logger LOG = Logger.getLogger(RecognitionService.class);
    private static final float SAMPLE_RATE = 44100.0f;
    private static Recognito<String> recognito = new Recognito<>(SAMPLE_RATE);
    private Set<String> usersNames = new HashSet<>();


    @Override
    public List<MatchResult<String>> recognize(File file) throws IOException, UnsupportedAudioFileException {
        List<MatchResult<String>> matches = recognito.identify(file);
        return matches;
    }

    @Override
    public void addVoice(String userName, File voice) throws UnsupportedAudioFileException {
        try {
            if (!usersNames.contains(userName)) {
                usersNames.add(userName);
                recognito.createVoicePrint(userName, voice);
            } else {
                recognito.mergeVoiceSample(userName, voice);
            }
        } catch (Exception e) {
            LOG.error("Exception: " + e.getMessage());
        }
    }


}
