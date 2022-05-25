package lucrare.dizertatie.centruautorizare.cont;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Cont")
@Table(name="cont")
@Data
public class Cont implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String rol;

    @Transient
    private String token;

}
