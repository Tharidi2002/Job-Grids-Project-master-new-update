package lk.ijse.backend.service.imple;

import lk.ijse.backend.dto.formDTO.ProfileDTO;
import lk.ijse.backend.dto.formDTO.ProfileDataDTO;
import lk.ijse.backend.entity.Profile;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.ProfileRepo;
import lk.ijse.backend.repository.UserRepo;
import lk.ijse.backend.service.FileStorageService;
import lk.ijse.backend.service.ProfileService;
import lk.ijse.backend.util.ResponseUtil;
import lk.ijse.backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {


    private final ProfileRepo profileRepo;
    private final UserRepo userRepo;
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepo profileRepo,
                              UserRepo userRepo,
                              FileStorageService fileStorageService,
                              ModelMapper modelMapper) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
        this.fileStorageService = fileStorageService;
        this.modelMapper = modelMapper;

        // Configure ModelMapper for Profile to ProfileDTO mapping
        this.modelMapper.typeMap(Profile.class, ProfileDTO.class)
                .addMapping(Profile::getFirstName, ProfileDTO::setFirstName)
                .addMapping(Profile::getLastName, ProfileDTO::setLastName);
    }

    @Override
    public ResponseUtil saveProfile(ProfileDataDTO profileDataDTO) {
        /*if (profileRepo.existsByUserEmail(profileDataDTO.getEmail())) {
            return new ResponseUtil(VarList.Bad_Request, "Profile already exists", null);
        }

        User user = userRepo.findByEmail(profileDataDTO.getEmail());
        if (user == null) {
            return new ResponseUtil(VarList.Not_Found, "User not found", null);
        }

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFirstName(profileDataDTO.getFirstName());
        profile.setLastName(profileDataDTO.getLastName());
        profile.setAddress(profileDataDTO.getAddress());
        profile.setContact(profileDataDTO.getContact());

        // Handle image upload
        MultipartFile image = profileDataDTO.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                String imagePath = fileStorageService.storeProfileImage(image);
                profile.setImage(imagePath);
            } catch (Exception e) {
                return new ResponseUtil(VarList.Internal_Server_Error, "Image upload failed", null);
            }
        }

        profileRepo.save(profile);

        // Create response DTO
        ProfileDTO responseDto = new ProfileDTO();
        responseDto.setEmail(profileDataDTO.getEmail());
        responseDto.setFirstName(profile.getFirstName());
        responseDto.setLastName(profile.getLastName());
        responseDto.setAddress(profile.getAddress());
        responseDto.setContact(profile.getContact());
        responseDto.setImage(profile.getImage());

        return new ResponseUtil(VarList.Created, "Success", responseDto);*/
        if (profileRepo.existsByUserEmail(profileDataDTO.getEmail())) {
            return new ResponseUtil(VarList.Bad_Request, "Profile already exists", null);
        }

        User user = userRepo.findByEmail(profileDataDTO.getEmail());
        if (user == null) {
            return new ResponseUtil(VarList.Not_Found, "User not found", null);
        }

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFirstName(profileDataDTO.getFirstName());
        profile.setLastName(profileDataDTO.getLastName());
        profile.setAddress(profileDataDTO.getAddress());
        profile.setContact(profileDataDTO.getContact());

        // Handle image upload
        MultipartFile image = profileDataDTO.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                String imagePath = fileStorageService.storeProfileImage(image);
                profile.setImage(imagePath);
            } catch (Exception e) {
                return new ResponseUtil(VarList.Internal_Server_Error, "Image upload failed", null);
            }
        }

        profileRepo.save(profile);

        // Create response DTO with joinDate
        ProfileDTO responseDto = modelMapper.map(profile, ProfileDTO.class);
        responseDto.setEmail(profileDataDTO.getEmail());
        responseDto.setJoinDate(user.getJoinDate()); // Set joinDate from user

        return new ResponseUtil(VarList.Created, "Success", responseDto);
    }


    @Override
    public ResponseUtil updateProfile(ProfileDataDTO profileDataDTO) {
        /*Profile existingProfile = profileRepo.findByUserEmail(profileDataDTO.getEmail());
        if (existingProfile == null) {
            return new ResponseUtil(VarList.Not_Found, "Profile not found", null);
        }

        // Update fields
        existingProfile.setFirstName(profileDataDTO.getFirstName());
        existingProfile.setLastName(profileDataDTO.getLastName());
        existingProfile.setAddress(profileDataDTO.getAddress());
        existingProfile.setContact(profileDataDTO.getContact());

        // Handle image update
        MultipartFile image = profileDataDTO.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                // Delete old image if exists
                if (existingProfile.getImage() != null) {
                    fileStorageService.deleteProfileImage(existingProfile.getImage());
                }

                String imagePath = fileStorageService.storeProfileImage(image);
                existingProfile.setImage(imagePath);
            } catch (Exception e) {
                return new ResponseUtil(VarList.Internal_Server_Error, "Image upload failed", null);
            }
        }

        profileRepo.save(existingProfile);
        ProfileDTO responseDto = modelMapper.map(existingProfile, ProfileDTO.class);
        responseDto.setEmail(profileDataDTO.getEmail());
        return new ResponseUtil(VarList.OK, "Success", responseDto);*/

        Profile existingProfile = profileRepo.findByUserEmail(profileDataDTO.getEmail());
        if (existingProfile == null) {
            return new ResponseUtil(VarList.Not_Found, "Profile not found", null);
        }

        // Update fields
        existingProfile.setFirstName(profileDataDTO.getFirstName());
        existingProfile.setLastName(profileDataDTO.getLastName());
        existingProfile.setAddress(profileDataDTO.getAddress());
        existingProfile.setContact(profileDataDTO.getContact());

        // Handle image update
        MultipartFile image = profileDataDTO.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                // Delete old image if exists
                if (existingProfile.getImage() != null) {
                    fileStorageService.deleteProfileImage(existingProfile.getImage());
                }

                String imagePath = fileStorageService.storeProfileImage(image);
                existingProfile.setImage(imagePath);
            } catch (Exception e) {
                return new ResponseUtil(VarList.Internal_Server_Error, "Image upload failed", null);
            }
        }

        profileRepo.save(existingProfile);

        // Get user to include joinDate in response
        User user = userRepo.findByEmail(profileDataDTO.getEmail());
        ProfileDTO responseDto = modelMapper.map(existingProfile, ProfileDTO.class);
        responseDto.setEmail(profileDataDTO.getEmail());
        responseDto.setJoinDate(user.getJoinDate()); // Set joinDate from user

        return new ResponseUtil(VarList.OK, "Success", responseDto);
    }

    @Override
    public ResponseUtil getProfileByEmail(String email) {
        /*Profile profile = profileRepo.findByUserEmail(email);
        if (profile == null) {
            return new ResponseUtil(VarList.Not_Found, "Profile not found", null);
        }
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);
        profileDTO.setEmail(email);
        return new ResponseUtil(VarList.OK, "Success", profileDTO);*/
        Profile profile = profileRepo.findByUserEmail(email);
        if (profile == null) {
            return new ResponseUtil(VarList.Not_Found, "Profile not found", null);
        }

        // Get user to include joinDate in response
        User user = userRepo.findByEmail(email);
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);
        profileDTO.setEmail(email);
        profileDTO.setJoinDate(user.getJoinDate()); // Set joinDate from user

        return new ResponseUtil(VarList.OK, "Success", profileDTO);
    }

    @Override
    public ResponseUtil deleteProfile(String email) {
        /*Profile profile = profileRepo.findByUserEmail(email);
        if (profile == null) {
            return new ResponseUtil(VarList.Not_Found, "Profile not found", null);
        }

        // Delete associated image
        if (profile.getImage() != null) {
            fileStorageService.deleteProfileImage(profile.getImage());
        }

        profileRepo.delete(profile);
        return new ResponseUtil(VarList.OK, "Success", null);*/
        Profile profile = profileRepo.findByUserEmail(email);
        if (profile == null) {
            return new ResponseUtil(VarList.Not_Found, "Profile not found", null);
        }

        // Delete associated image
        if (profile.getImage() != null) {
            fileStorageService.deleteProfileImage(profile.getImage());
        }

        profileRepo.delete(profile);
        return new ResponseUtil(VarList.OK, "Success", null);
    }
}