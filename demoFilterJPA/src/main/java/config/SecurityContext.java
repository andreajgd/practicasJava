package config;

public class SecurityContext {
    private static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

    public static String getCurrentUser() {
        return CURRENT_USER.get();
    }

    public static void setCurrentUser(String currentUser) {
        CURRENT_USER.set(currentUser);
    }
}
