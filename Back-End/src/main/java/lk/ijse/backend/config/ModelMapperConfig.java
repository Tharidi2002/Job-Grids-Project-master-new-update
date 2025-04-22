package lk.ijse.backend.config;

import lk.ijse.backend.dto.formDTO.ProfileDTO;
import lk.ijse.backend.entity.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        /*// Configure mappings here
        modelMapper.typeMap(Profile.class, ProfileDTO.class)
                .addMapping(Profile::getFirstName, ProfileDTO::setFirstName)
                .addMapping(Profile::getLastName, ProfileDTO::setLastName);
*/

        // Configure mappings for Profile to ProfileDTO
        modelMapper.typeMap(Profile.class, ProfileDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Profile::getFirstName, ProfileDTO::setFirstName);
                    mapper.map(Profile::getLastName, ProfileDTO::setLastName);
                    mapper.map(src -> src.getUser().getJoinDate(), ProfileDTO::setJoinDate);
                });

        return modelMapper;
    }
}
