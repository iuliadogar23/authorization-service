package lucrare.dizertatie.centruautorizare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContResponse {

    private long id;
    private String login;
    private String token;
}