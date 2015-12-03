package pl.androidland.persistance;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakersService {
    private static final Logger LOG = Logger.getLogger(SpeakersService.class);

    @Autowired
    SpeakersRepository repository;

    public void addSpeaker(Speaker speaker) {
        repository.save(speaker);
        LOG.info("Speaker: " + speaker.getName() + " has been added to db.");
    }

    public void updateSpeaker(Speaker speaker) {
        repository.save(speaker);
        LOG.info("Speaker: " + speaker.getName() + " has been added to db. || ID: " + speaker.getId());
    }

    public Speaker getSpeakerByName(String name) {
        return repository.findByName(name);
    }

    public List<Speaker> getAllUsers() {
        return repository.findAll();
    }

    public boolean isUserAdded(String name) {
        return repository.findByName(name) != null;
    }
}
