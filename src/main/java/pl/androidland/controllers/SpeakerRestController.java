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
import pl.androidland.responses.RegisterResponse;
import pl.androidland.responses.Response;
import pl.androidland.responses.ResponseType;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class SpeakerRestController {

    @Autowired
    private SpeakersService speakersService;

    @Autowired
    private Recognition recognitionService;

    @RequestMapping(value = "/addVoice", method = RequestMethod.POST)
    public
    @ResponseBody
    String addVoice(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("voice") MultipartFile file)
            throws IOException, UnsupportedAudioFileException {


        VoiceUploader uploader = new VoiceUploader(name, file);
        String pathname = uploader.getFilePath();

        Speaker speaker = speakersService.isUserAdded(name) ? speakersService.getSpeakerByName(name) : speakersService.addSpeaker(new Speaker(name, password.toCharArray()));
        speaker.addVoicePath(pathname);

        return "You successfully uploaded " + pathname + "!";
    }



    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Response> user(@RequestParam("name") String name) throws IOException, UnsupportedAudioFileException {

        Speaker speaker = speakersService.getSpeakerByName(name);

        if (speaker == null)
            return new ResponseEntity<>(RegisterResponse.Builder.
                    newResponse(ResponseType.FAIL)
                    .withMessage(String.format("There is no speaker with name %s", name))
                    .withContent(name)
                    .build(), HttpStatus.OK);

        return new ResponseEntity<>(RegisterResponse.Builder.
                newResponse(ResponseType.SUCCESSFUL)
                .withMessage(String.format("Speaker with name %s has found", name))
                .withContent(speaker)
                .build(), HttpStatus.OK);
    }


}
