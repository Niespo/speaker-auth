package pl.androidland.controllers;


import com.bitsinharmony.recognito.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.androidland.io.VoiceUploader;
import pl.androidland.recognition.RecognitionService;
import pl.androidland.responses.RegisterResponse;
import pl.androidland.responses.Response;
import pl.androidland.responses.ResponseType;
import pl.androidland.responses.SpeakerInfo;

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
    ResponseEntity<Response> recognize(@RequestParam("voice") MultipartFile file) throws IOException, UnsupportedAudioFileException {
        VoiceUploader uploader = new VoiceUploader((file));
        if (!uploader.isUploaded())
            return getFailUploadResponse();

        String pathname = uploader.getFilePath();
        List<MatchResult<String>> result = recognitionService.recognize(new File(pathname));

        if (result.isEmpty())
            return getFailRecognitionResponse();

        MatchResult<String> bestResult = result.get(0);
        SpeakerInfo speakerInfo = createSpeakerInfo(bestResult);

        return getSpeakerInfoResponse(speakerInfo);
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Response> recognizeAll(@RequestParam("voice") MultipartFile file) throws IOException, UnsupportedAudioFileException {
        VoiceUploader uploader = new VoiceUploader((file));
        if (!uploader.isUploaded())
            return getFailUploadResponse();

        String pathname = uploader.getFilePath();
        List<MatchResult<String>> results = recognitionService.recognize(new File(pathname));

        if (results.isEmpty())
            return getFailRecognitionResponse();

        List<SpeakerInfo> speakersInfo = results
                .stream()
                .map(this::createSpeakerInfo)
                .collect(Collectors.toList());

        return getSpeakerInfoResponse(speakersInfo);
    }

    private <T> ResponseEntity<Response> getSpeakerInfoResponse(T speakerInfo) {
        return new ResponseEntity<>(RegisterResponse.Builder.
                newResponse(ResponseType.SUCCESSFUL)
                .withMessage("Results of voice recognition have been found")
                .withContent(speakerInfo)
                .build(), HttpStatus.OK);
    }

    private SpeakerInfo createSpeakerInfo(MatchResult<String> result) {
        return SpeakerInfo.Builder.
                newInfo()
                .withName(result.getKey())
                .withLikehoodRatio(result.getLikelihoodRatio())
                .build();
    }

    private ResponseEntity<Response> getFailUploadResponse() {
        return new ResponseEntity<>(RegisterResponse.Builder.
                newResponse(ResponseType.FAIL)
                .withMessage("File has been not upload")
                .build(), HttpStatus.OK);
    }

    private ResponseEntity<Response> getFailRecognitionResponse() {
        return new ResponseEntity<>(RegisterResponse.Builder.
                newResponse(ResponseType.FAIL)
                .withMessage("There is no chance to recognize this voice")
                .build(), HttpStatus.OK);
    }


}
