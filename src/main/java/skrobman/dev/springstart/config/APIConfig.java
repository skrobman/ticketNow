package skrobman.dev.springstart.config;

import io.github.cdimascio.dotenv.Dotenv;

public class APIConfig {
    public static void LoadEnv(){
        Dotenv dotenv = Dotenv.configure().load();

        //Postgres Configuration
        String springDataSourceUrl = dotenv.get("SPRING_DATASOURCE_URL");
        String postgresUser = dotenv.get("POSTGRES_USER");
        String postgresPassword = dotenv.get("POSTGRES_PASSWORD");

        //Mail Configuration
        String emailName = dotenv.get("EMAIL_NAME");
        String emailPassword = dotenv.get("EMAIL_PASSWORD");

        if(springDataSourceUrl == null){
            throw new IllegalStateException("Data Source URL cannot be null");
        }
        if(postgresUser == null){
            throw new IllegalStateException("Database User cannot be null");
        }
        if(postgresPassword == null){
            throw new IllegalStateException("Password cannot be null");
        }
        if(emailName == null){
            throw new IllegalStateException("Email required");
        }
        if(emailPassword == null){
            throw new IllegalStateException("Password required");
        }

        System.setProperty("SPRING_DATASOURCE_URL", springDataSourceUrl);
        System.setProperty("POSTGRES_USER", postgresUser);
        System.setProperty("POSTGRES_PASSWORD", postgresPassword);
        System.setProperty("EMAIL_NAME", emailName);
        System.setProperty("EMAIL_PASSWORD", emailPassword);
    }
}
