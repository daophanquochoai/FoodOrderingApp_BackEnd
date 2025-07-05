package doctorhoai.learn.authservice.jwt.service.token;

public interface TokenService {
    void saveToken( String token , String username);
    boolean findToken( String token );
    void deleteToken( String token );
}
