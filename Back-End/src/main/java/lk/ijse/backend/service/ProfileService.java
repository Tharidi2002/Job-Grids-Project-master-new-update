package lk.ijse.backend.service;

import lk.ijse.backend.dto.formDTO.ProfileDTO;
import lk.ijse.backend.dto.formDTO.ProfileDataDTO;
import lk.ijse.backend.util.ResponseUtil;

public interface ProfileService {
    ResponseUtil saveProfile(ProfileDataDTO profileDataDTO);  // Changed from ProfileDTO to ProfileDataDTO
    ResponseUtil updateProfile(ProfileDataDTO profileDataDTO); // Changed from ProfileDTO to ProfileDataDTO
    ResponseUtil getProfileByEmail(String email);
    ResponseUtil deleteProfile(String email);
}