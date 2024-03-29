package ac.uk.bolton.globalhotelhub.controller;

import ac.uk.bolton.globalhotelhub.service.UserService;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public AjaxResponse<Object> getCurrentUser() {
        return AjaxResponse.success(userService.getCurrentUser());
    }
}
