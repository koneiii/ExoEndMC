package fr.koneiii.restapi.repository;


import fr.koneiii.restapi.models.ProfileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileModel, String> {
}
