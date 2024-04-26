package cl.company.ecommerce.service.impl;


import cl.company.ecommerce.repository.UserRepository;
import cl.company.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean findUser(String user, String password) {
       return userRepository.findByUserPassword(user,password).isPresent();
    }


}
