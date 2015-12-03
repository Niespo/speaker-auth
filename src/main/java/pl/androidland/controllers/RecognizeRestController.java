package pl.androidland.controllers;


import com.bitsinharmony.recognito.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.androidland.io.VoiceUploader;
import pl.androidland.recognition.RecognitionService;
import pl.androidland.responses.Response;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/recognize")
public class RecognizeRestController {

    @Autowired
    private RecognitionService recognitionService;

    @RequestMapping(value = "/best", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Response> recognize(@RequestParam("voice") MultipartFile file) throws IOException, UnsupportedAudioFileException {
        VoiceUploader uploader = new VoiceUploader((file));
        if (!uploader.isUploaded()) {
            return "You failed to upload  => ";
        }

        String pathname = uploader.getFilePath();

        MatchResult result = recognitionService.recognize(new File(pathname));

        return "I think it is " + result.getKey() + " with " + result.getLikelihoodRatio() + "% confidence.";
    }
}
