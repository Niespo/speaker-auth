package pl.androidland;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.androidland.persistance.Speaker;
import pl.androidland.persistance.SpeakersRepository;
import pl.androidland.persistance.SpeakersService;
import pl.androidland.recognition.RecognitionService;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;


@SpringBootApplication
public class SpeakerAuthApplication implements CommandLineRunner {

    @Autowired
    private SpeakersService speakersService;

    @Autowired
    private RecognitionService recognitionService;

    @Autowired
    private SpeakersRepository repository;

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        SpringApplication.run(SpeakerAuthApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException, UnsupportedAudioFileException {
        repository.deleteAll();
        addUser("Marcin");
        addUser("Aneta");
        System.out.println(System.getProperty("user.dir"));
        Collection<Speaker> speakers = speakersService.getAllUsers();
        System.out.println(speakers.size());

        for (Speaker speaker : speakers)
                speaker.getVoiceFilesPaths()
                        .stream()
                        .forEach((voicePath) -> recognitionService.addVoice(speaker.getName(),voicePath));

    }

    public void addUser(String name) {
        if (speakersService.isUserAdded(name)) return;

        String password = DigestUtils.sha1Hex(name + 1234);
        Speaker speaker = new Speaker(name, password.toCharArray());

        for (int index = 1; index <= 4; index++)
            speaker.addVoicePath("D:\\IdeaProjects\\SpeakerAuthService\\audio\\" + name.toLowerCase() + "-" + index + ".wav");

        speakersService.addSpeaker(speaker);

    }

}
