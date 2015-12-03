package pl.androidland.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.androidland.io.VoiceUploader;
import pl.androidland.persistance.Speaker;
import pl.androidland.persistance.SpeakersService;
import pl.androidland.recognition.Recognition;
import pl.androidland.responses.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/speaker")
public class SpeakerRestController {

    @Autowired
    private SpeakersService speakersService;

    @Autowired
    private Recognition recognitionService;

    @RequestMapping(value = "/addVoice", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Response> addVoice(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("voice") MultipartFile file)
            throws IOException, UnsupportedAudioFileException {


        if(!speakersService.isUserAdded(name))
            return ResponseMessageFactory.getNotSpeakerFoundResponse(name);

        Speaker speaker = speakersService.getSpeakerByName(name);

        if(!Arrays.equals(speaker.getPassword(),password.toCharArray()))
            return ResponseMessageFactory.getIncorrectPasswordResponse(name);

        VoiceUploader uploader = new VoiceUploader(name, file);

        if (!uploader.isUploaded())
            return ResponseMessageFactory.getFailUploadResponse();

        String pathname = uploader.getFilePath();

        speaker.addVoicePath(pathname);
        speakersService.updateSpeaker(speaker);

        SpeakerInfo speakerInfo = SpeakerInfo.Builder
                .newInfo()
                .withName(speaker.getName())
                .withDate(speaker.getRegisterDate())
                .withVoiceFilesPaths(speaker.getVoiceFilesPaths())
                .build();

        return ResponseMessageFactory.getVoiceAddedResponse(speakerInfo);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Response> user(@RequestParam("name") String name) throws IOException, UnsupportedAudioFileException {

        Speaker speaker = speakersService.getSpeakerByName(name);

        if (speaker == null)
            return new ResponseEntity<>(ResponseMessage.Builder.
                    newResponse(ResponseType.FAIL)
                    .withMessage(String.format("There is no speaker with name %s", name))
                    .withContent(name)
                    .build(), HttpStatus.OK);

        return new ResponseEntity<>(ResponseMessage.Builder.
                newResponse(ResponseType.SUCCESSFUL)
                .withMessage(String.format("Speaker with name %s has found", name))
                .withContent(speaker)
                .build(), HttpStatus.OK);
    }


}
