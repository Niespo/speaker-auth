package pl.androidland.controllers;


import com.bitsinharmony.recognito.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.androidland.io.VoiceUploader;
import pl.androidland.recognition.RecognitionService;
import pl.androidland.responses.Response;
import pl.androidland.responses.ResponseMessageFactory;
import pl.androidland.responses.RecognitionInfo;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recognize")
public class RecognizeRestController {

    @Autowired
    private RecognitionService recognitionService;

    @RequestMapping(value = "/best", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Response> recognize(
            @RequestParam("voice") MultipartFile file)
            throws IOException, UnsupportedAudioFileException {

        VoiceUploader uploader = new VoiceUploader(file);
        if (!uploader.isUploaded())
            return ResponseMessageFactory.getFailUploadResponse();

        String pathname = uploader.getFilePath();
        List<MatchResult<String>> result = recognitionService.recognize(new File(pathname));

        if (result.isEmpty())
            return ResponseMessageFactory.getFailRecognitionResponse();

        MatchResult<String> bestResult = result.get(0);
        RecognitionInfo recognitionInfo = createRecognitionInfo(bestResult);

        return ResponseMessageFactory.getSpeakerInfoResponse(recognitionInfo);
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Response> recognizeAll(@RequestParam("voice") MultipartFile file) throws IOException, UnsupportedAudioFileException {
        VoiceUploader uploader = new VoiceUploader((file));
        if (!uploader.isUploaded())
            return ResponseMessageFactory.getFailUploadResponse();

        String pathname = uploader.getFilePath();
        List<MatchResult<String>> results = recognitionService.recognize(new File(pathname));

        if (results.isEmpty())
            return ResponseMessageFactory.getFailRecognitionResponse();

        List<RecognitionInfo> speakersInfo = results
                .stream()
                .map(this::createRecognitionInfo)
                .collect(Collectors.toList());

        return ResponseMessageFactory.getSpeakerInfoResponse(speakersInfo);
    }

    private RecognitionInfo createRecognitionInfo(MatchResult<String> result) {
        return RecognitionInfo.Builder.
                newInfo()
                .withName(result.getKey())
                .withLikehoodRatio(result.getLikelihoodRatio())
                .build();
    }
}
