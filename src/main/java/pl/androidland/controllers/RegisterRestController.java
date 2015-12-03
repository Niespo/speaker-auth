package pl.androidland.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.androidland.persistance.Speaker;
import pl.androidland.persistance.SpeakersService;
import pl.androidland.responses.Response;
import pl.androidland.responses.ResponseMessageFactory;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestController
@RequestMapping("/register")
public class RegisterRestController {

    @Autowired
    private SpeakersService speakersService;

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> user(
            @RequestParam("name") String name,
            @RequestParam("password") String password)
            throws IOException, UnsupportedAudioFileException {

        if (speakersService.isUserAdded(name))
            return ResponseMessageFactory.getNotRegisterResponse(name);

        Speaker speaker = new Speaker(name, password.toCharArray());
        speakersService.addSpeaker(speaker);

        return ResponseMessageFactory.getRegisterResponse(name);
    }

}
