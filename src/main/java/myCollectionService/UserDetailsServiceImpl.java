package myCollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import myCollectionService.dataBaseService.DbService;
import myCollectionService.dataBaseEntitys.Collector;

import java.util.HashSet;
import java.util.Set;

//Been adapter between account of our class and Collector and account of library class User of SpringSecurity

@Service // Spring annotation,  derived from @Component
// Spring will automatically create the bin UserDetailsService Impl
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired //auto initialization
    private DbService dbService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //via collector Service object from the database take our class account Collector
        Collector collector = dbService.getCollectorByLogin(login);
        if (collector == null) //if user with this login is no exist throw UsernameNotFoundException
            throw new UsernameNotFoundException(login + " not found");

        //Structure for transmitting of role to object Spring account if User class , pass as Set
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(collector.getRole().toString()));

        //Return the account of the User library class from SpringSecurity
        return new User(collector.getLogin(), collector.getPassword(), roles);
    }
}