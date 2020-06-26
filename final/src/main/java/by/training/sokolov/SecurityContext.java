package by.training.sokolov;

import by.training.sokolov.command.CommandType;
import by.training.sokolov.model.UserRole;
import by.training.sokolov.user.model.User;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityContext {

    private static final SecurityContext SECURITY_CONTEXT = new SecurityContext();
    private final Map<String, User> userDtoMap = new ConcurrentHashMap<>(1000);
    private final ThreadLocal<String> currentSessionIdStorage = new ThreadLocal<>();
    public Long sessionIdStorage;
    private Properties properties = new Properties();

    public static SecurityContext getInstance() {
        return SECURITY_CONTEXT;
    }

    public Map<String, User> getUserMap() {
        return userDtoMap;
    }

    public void initialize(ServletContext servletContext) {

        try (InputStream propertyStream = SecurityContext.class.getResourceAsStream("/security.properties")) {
            properties.load(propertyStream);
        } catch (IOException e) {
//            properties.setProperty("error", e.getMessage());
            throw new IllegalStateException("Failed to read security properties", e);
        }
    }

//    public String getCurrentSessionId() {
//
//        return currentSessionIdStorage.get();
//    }

//    public void setCurrentSessionId(String sessionId) {
//        currentSessionIdStorage.set(sessionId);
//    }

    public void login(User user, String sessionId) {
        userDtoMap.put(sessionId, user);
    }

    public void logout(String sessionId) {
        userDtoMap.remove(sessionId);
    }

    public User getCurrentUser(String sessionId) {

        return sessionId != null ? userDtoMap.get(sessionId) : null;
    }

    public boolean canExecute(CommandType commandType, String sessionId) {

        User currentUser = getCurrentUser(sessionId);
        return canExecute(currentUser, commandType);
    }

    public boolean canExecute(User user, CommandType commandType) {

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

//    public boolean isLoggedIn(String sessionId) {
//
//        return getCurrentUser(sessionId) != null;
//    }

}
