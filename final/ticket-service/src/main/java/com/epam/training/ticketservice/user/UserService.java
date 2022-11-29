package com.epam.training.ticketservice.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void login(String name,String password){
        if(name.equals("admin") && password.equals("admin")){
            userRepository.save(new User(name,password));
        }else System.out.println("Login failed due to incorrect credentials");
    }

    public void signOut(){
        if(userRepository.findByNameAndPassword("admin","admin").isPresent()){
            userRepository.delete(userRepository.findByNameAndPassword("admin","admin").get());
        }
    }


    public String describeAccount(){
        if(userRepository.findByNameAndPassword("admin","admin").isPresent()){
            return "Signed in with privileged account admin";
        }else return "You are not signed in";
    }

    public boolean isAdminLoggedIn(){
        return userRepository.findByNameAndPassword("admin","admin").isPresent();
    }
}