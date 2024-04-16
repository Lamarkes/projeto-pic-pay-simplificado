package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validatedTransaction(User sender, BigDecimal amount){
        if (sender.getUserType() == UserType.MERCHANT){
            throw new RuntimeException("Este usuario não esta autorizado a realizar transaçoes");
        }
        if (sender.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Saldo Insuficiente!");
        }
    }

    public User findUserById(Long id){
       return this.repository.findUserById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }


    public void saveUser(User user){
        this.repository.save(user);
    }

}
