package pl.androidland.io;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Immutable
public class VoiceUploader {
    private static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    private static final String AUDIO_DIRECTORY = "audio";
    private static final String DIRECTORY_SEPARATOR = "\\";
    private static final String TARGET_DIRECTORY = CURRENT_DIRECTORY + DIRECTORY_SEPARATOR + AUDIO_DIRECTORY + DIRECTORY_SEPARATOR;
    public static final String NO_VOICE_ID_PREFIX = "identity";

    private String filePath;
    private boolean isUploaded = false;

    public VoiceUploader(String voiceId, MultipartFile file) throws IOException {
        filePath = writeFile(voiceId, file);
    }

    public VoiceUploader(MultipartFile file) throws IOException {
        this(NO_VOICE_ID_PREFIX, file);
    }

    private String writeFile(String userId, MultipartFile file) {
        String pathname = TARGET_DIRECTORY + userId + DIRECTORY_SEPARATOR + file.getOriginalFilename();
        if (file.isEmpty())
            return pathname;
        BufferedOutputStream stream = null;
        try {
            byte[] bytes = file.getBytes();
            FileOutputStream fileOutput = new FileOutputStream(new File(pathname));
            stream = new BufferedOutputStream(fileOutput);
            stream.write(bytes);
            stream.close();
            isUploaded = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return pathname;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isUploaded() {
        return isUploaded;
    }
}