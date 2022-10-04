package br.com.segment.leosmusicstoreapi.services;

import br.com.segment.leosmusicstoreapi.dtos.UserPostDto;
import br.com.segment.leosmusicstoreapi.dtos.outputs.UserOutput;
import br.com.segment.leosmusicstoreapi.models.User;
import br.com.segment.leosmusicstoreapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return new UserOutput(user.getEmail(), user.getPasswordHash());
    }

    public User createNewUser(UserPostDto userPostDto) {
        User user = modelMapper.map(userPostDto, User.class);
        user.setPasswordHash(bcryptEncoder.encode(userPostDto.getPassword()));
        return userRepository.save(user);
    }
}
