package testask2;

public class SQLQuery {
    public static final String SELECT_LAST_UPDATE = "SELECT processed_time FROM vacancies ORDER BY processed_time DESC LIMIT 1";
    public static final String ADD_VACANCY = "INSERT INTO vacancies (header, url, description, create_time, processed_time) VALUES (?, ?, ?, ?, ?)";
    public static final String CREATE_TABLE_VACANCIES = "CREATE TABLE IF NOT EXISTS vacancies (id SERIAL PRIMARY KEY NOT NULL," +
            "header VARCHAR NOT NULL, url VARCHAR UNIQUE NOT NULL, " +
            "description VARCHAR, create_time TIMESTAMP NOT NULL, processed_time TIMESTAMP NOT NULL)";

    private SQLQuery() {
    }
}
