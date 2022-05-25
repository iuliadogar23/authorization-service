package lucrare.dizertatie.centruautorizare.cont.detalii;

import lombok.RequiredArgsConstructor;
import lucrare.dizertatie.centruautorizare.cont.Cont;
import lucrare.dizertatie.centruautorizare.cont.ContRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ContRepository contRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Cont cont = contRepository.findByUsername(username).orElse(null);

        if (cont!=null)
        {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(cont.getRol()));
            return new User(
                    cont.getUsername(),
                    cont.getPassword(),
                    grantedAuthorities
            );
        }

        return null;
    }
}
