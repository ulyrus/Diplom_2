import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiTokens {
    private String accessToken;
    private String refreshToken;
    private ApiUser user;
}
