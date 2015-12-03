package pl.androidland.persistance;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class Speaker {

    @Id
    private String id;

    private String name;

    private char[] password;

    private Date registerDate;

    private List<String> voiceFilesPaths = new ArrayList<>();

    public Speaker(String name, char[] password) {
        voiceFilesPaths = new ArrayList<>();
        this.name = name;
        registerDate = new Date();
        this.password = password;
    }

    public List<String> getVoiceFilesPaths() {
        return voiceFilesPaths;
    }

    public String getName() {
        return name;
    }

    public Speaker addVoicePath(String voicePath) {
        voiceFilesPaths.add(voicePath);
        return this;
    }

    public String getId() {
        return id;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public char[] getPassword() {
        return password;
    }
}
