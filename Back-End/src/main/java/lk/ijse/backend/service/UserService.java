package lk.ijse.backend.service;



import lk.ijse.backend.dto.UserDTO;
import lk.ijse.backend.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO getCurrentUser(String email);
    UserDTO getUserByEmail(String email);
    int updateUser(UserDTO userDTO);
    UserDTO getUserEmailByToken(String token);


    UserDTO getUserDTOByToken(String token);
}
