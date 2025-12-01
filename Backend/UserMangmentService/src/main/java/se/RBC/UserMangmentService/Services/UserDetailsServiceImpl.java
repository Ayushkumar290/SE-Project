package se.RBC.UserMangmentService.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.RBC.UserMangmentService.Impl.UserDetailsImpl;
import se.RBC.UserMangmentService.Models.User;
import se.RBC.UserMangmentService.Repository.UserRepository;



@Service

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;






    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Now uses the custom class and factory method
        return UserDetailsImpl.build(user);
    }



}
