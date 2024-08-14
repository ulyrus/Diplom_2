import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiAuthOrRegister {
    private boolean success;
    private String accessToken;
    private String refreshToken;
}