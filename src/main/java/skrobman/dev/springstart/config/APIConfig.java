package skrobman.dev.springstart.config;

import io.github.cdimascio.dotenv.Dotenv;

public class APIConfig {
    public static void LoadEnv() {
        Dotenv dotenv = Dotenv.configure().load();

        //Postgres Configuration
        String springDataSourceUrl = dotenv.get("SPRING_DATASOURCE_URL");
        String postgresUser = dotenv.get("POSTGRES_USER");
        String postgresPassword = dotenv.get("POSTGRES_PASSWORD");

        //Mail Configuration
        String emailName = dotenv.get("EMAIL_NAME");
        String emailPassword = dotenv.get("EMAIL_PASSWORD");

        //Oauth2 Google loading credentials
        String googleClientId = dotenv.get("GOOGLE_CLIENT_ID");
        String googleClientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");

        //Oauth2 GitHub loading credentials
        String githubClientId = dotenv.get("GITHUB_CLIENT_ID");
        String githubClientSecret = dotenv.get("GITHUB_CLIENT_SECRET");

        if (springDataSourceUrl == null) {
        //JWT Token
        String JWT_SECRET = dotenv.get("JWT_SECRET");

        if(springDataSourceUrl == null){
            throw new IllegalStateException("Data Source URL cannot be null");
        }
        if (postgresUser == null) {
            throw new IllegalStateException("Database User cannot be null");
        }
        if (postgresPassword == null) {
            throw new IllegalStateException("Password cannot be null");
        }
        if (emailName == null) {
            throw new IllegalStateException("Email required");
        }
        if (emailPassword == null) {
            throw new IllegalStateException("Password required");
        }
        if(JWT_SECRET == null){
            throw new IllegalStateException("JWT Secret required");
        }
        if (googleClientId == null || googleClientSecret == null) {
            throw new IllegalStateException("Google Client ID and Client Secret required!");
        }
        if (githubClientId == null || githubClientSecret == null) {
            throw new IllegalStateException("Github Client ID and Client Secret required!");
        }

        System.setProperty("SPRING_DATASOURCE_URL", springDataSourceUrl);
        System.setProperty("POSTGRES_USER", postgresUser);
        System.setProperty("POSTGRES_PASSWORD", postgresPassword);
        System.setProperty("EMAIL_NAME", emailName);
        System.setProperty("EMAIL_PASSWORD", emailPassword);
        System.setProperty("JWT_SECRET", JWT_SECRET);
        System.setProperty("GOOGLE_CLIENT_ID", googleClientId);
        System.setProperty("GOOGLE_CLIENT_SECRET", googleClientSecret);
        System.setProperty("GITHUB_CLIENT_ID", githubClientId);
        System.setProperty("GITHUB_CLIENT_SECRET", githubClientSecret);
    }
}
