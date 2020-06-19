package by.training.sokolov;

import by.training.sokolov.command.CommandType;
import by.training.sokolov.dto.user.UserDto;
import by.training.sokolov.model.UserRole;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityContext {

    private static final SecurityContext SECURITY_CONTEXT = new SecurityContext();
    private final Map<String, UserDto> userDtoMap = new ConcurrentHashMap<>(1000);
    private final ThreadLocal<String> currentSessionIdStorage = new ThreadLocal<>();
    private Properties properties = new Properties();

    public static SecurityContext getInstance() {
        return SECURITY_CONTEXT;
    }

    public void initialize(ServletContext servletContext) {

        try (InputStream propertyStream = SecurityContext.class.getResourceAsStream("/security.properties")) {
            properties.load(propertyStream);
        } catch (IOException e) {
            properties.setProperty("error", e.getMessage());
            //throw new IllegalStateException("Failed to read security properties", e);
        }
    }

    public String getCurrentSessionId() {
        return currentSessionIdStorage.get();
    }

    public void setCurrentSessionId(String sessionId) {
        currentSessionIdStorage.set(sessionId);
    }

    public void login(UserDto userDto, String sessionId) {
        userDtoMap.put(sessionId, userDto);
    }

    public UserDto getCurrentUser() {
        String currentSessionId = getCurrentSessionId();
        return currentSessionId != null ? userDtoMap.get(currentSessionId) : null;
    }

    public boolean canExecute(CommandType commandType) {
        UserDto currentUser = getCurrentUser();
        return canExecute(currentUser, commandType);
    }

    public boolean canExecute(UserDto userDto, CommandType commandType) {

        String commandToRoles = properties.getProperty("command." + commandType.name());
        List<String> roles = Optional.ofNullable(commandToRoles)
                .map(s -> Arrays.asList(s.split(",")))
                .orElseGet(ArrayList::new);
        return (userDto != null && rolesMatch(roles, userDto)) || roles.isEmpty();

    }

    private boolean rolesMatch(List<String> roles, UserDto userDto) {
        for (String role : roles) {
            for (UserRole userRole : userDto.getRoles()) {
                if (role.equalsIgnoreCase(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLoggedIn() {

        return getCurrentUser() != null;
    }


}
