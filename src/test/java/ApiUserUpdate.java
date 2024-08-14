import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiUserUpdate {
    private boolean success;
    private String message;
    private ApiUser user;
}
