package lucrare.dizertatie.centruautorizare.cont;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContRepository extends JpaRepository<Cont, Integer> {

    Optional<Cont> findByUsername(String username);

}
