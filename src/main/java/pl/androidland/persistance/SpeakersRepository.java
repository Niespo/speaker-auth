package pl.androidland.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpeakersRepository extends MongoRepository<Speaker, String> {
    public Speaker findByName(String name);
}
