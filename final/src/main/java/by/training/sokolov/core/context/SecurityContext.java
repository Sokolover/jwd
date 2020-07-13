package by.training.sokolov.core.context;

import by.training.sokolov.command.constants.CommandType;
import by.training.sokolov.entity.role.model.UserRole;
import by.training.sokolov.entity.user.model.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityContext {

    private static final SecurityContext SECURITY_CONTEXT = new SecurityContext();
    private final Map<String, User> userMap = new ConcurrentHashMap<>(1000);
    private Properties properties = new Properties();

    public static SecurityContext getInstance() {
        return SECURITY_CONTEXT;
    }

    public void initialize(ServletContext servletContext) {

        try (InputStream propertyStream = SecurityContext.class.getResourceAsStream("/security.properties")) {
            properties.load(propertyStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read security properties", e);
        }
    }

    public void login(User user, String sessionId) {

        userMap.put(sessionId, user);
    }

    public void logout(String sessionId) {

        userMap.remove(sessionId);
    }

    public User getCurrentUser(String sessionId) {

        return sessionId != null ? userMap.get(sessionId) : null;
    }

    public boolean canExecute(CommandType commandType, String sessionId) {

        User currentUser = getCurrentUser(sessionId);
        return canExecute(currentUser, commandType);
    }

    private boolean canExecute(User user, CommandType commandType) {

        String commandToRoles = properties.getProperty("command." + commandType.name());
        List<String> roles = Optional.ofNullable(commandToRoles)
                .map(s -> Arrays.asList(s.split(",")))
                .orElseGet(ArrayList::new);
        return (user != null && rolesMatch(roles, user)) || roles.isEmpty();
    }

    private boolean rolesMatch(List<String> roles, User user) {

        for (String role : roles) {
            for (UserRole userRole : user.getRoles()) {
                if (role.equalsIgnoreCase(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isUserLoggedIn(HttpServletRequest req) {

        return getCurrentUser(req.getSession().getId()) != null;
    }

}
