package ac.uk.bolton.globalhotelhub.service;

import ac.uk.bolton.globalhotelhub.dto.TokenDTO;
import ac.uk.bolton.globalhotelhub.dto.UserDTO;
import ac.uk.bolton.globalhotelhub.request.LoginRequest;
import ac.uk.bolton.globalhotelhub.request.SignupRequest;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface UserService {
    AjaxResponse save(SignupRequest signupRequest);
    TokenDTO login(Authentication authentication, HttpServletRequest request, LoginRequest loginDTO);
    UserDTO getCurrentUser();
}
