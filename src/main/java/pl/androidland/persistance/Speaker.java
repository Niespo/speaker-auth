package pl.androidland.persistance;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document(collection = "users")
public class Speaker {

    @Id
    private String id;

    private String name;

    private char[] password;

    private Date registerDate;

    private Collection<String> voiceFilesPaths = new ArrayList<>();

    public Speaker(String name, char[] password) {
        voiceFilesPaths = new ArrayList<>();
        this.name = name;
        registerDate = new Date();
        this.password = password;
    }

    public Collection<String> getVoiceFilesPaths() {
        return voiceFilesPaths;
    }

    public String getName() {
        return name;
    }

    public Speaker addVoicePath(String voicePath) {
        voiceFilesPaths.add(voicePath);
        return this;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public char[] getPassword() {
        return password;
    }
}
