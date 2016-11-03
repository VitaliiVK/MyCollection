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

//адапетр между учетной записью нашего класса Collector и учетной записью библиотечного класса User из SpringSecurity

@Service // Spring анотация, производная от @Component, спринг атоматически создаст бин UserDetailsServiceImpl
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private DbService dbService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //с помощью обьекта collectorService вытаскиваем из базы учетную запись нашего класса Collector
        Collector collector = dbService.getCollectorByLogin(login);
        if (collector == null) //если пользователя с таким логином нет кидаем исключение как указано в документации
            throw new UsernameNotFoundException(login + " not found");

        //конструкция для передачи роли в обьект учетной записи Spring класса User передаем Set
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(collector.getRole().toString()));

        //возвращаем учетную запись библиотечного класса User из SpringSecurity
        return new User(collector.getLogin(), collector.getPassword(), roles);
    }
}