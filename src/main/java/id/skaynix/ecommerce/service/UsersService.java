package id.skaynix.ecommerce.service;

import id.skaynix.ecommerce.entity.Users;
import id.skaynix.ecommerce.exception.BadRequestException;
import id.skaynix.ecommerce.exception.ResourceNotFoundException;
import id.skaynix.ecommerce.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Users findById(String id){
        return usersRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Pengguna dengan id "+ id + " tidak ditemukan."));
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users create(Users users){
        if (!StringUtils.hasText(users.getId())) {
            throw new BadRequestException("Username harus diisi.");
        }
        if (usersRepository.existsById(users.getId())) {
            throw new BadRequestException("Username " + users.getId() + " sudah terdaftar.");
        }
        if (!StringUtils.hasText(users.getEmail())) {
            throw new BadRequestException("Email harus diisi.");
        }
        if (usersRepository.existsByEmail(users.getEmail())){
            throw new BadRequestException("Email " + users.getEmail() + " sudah terdaftar.");
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setIsActive(true);
        return usersRepository.save(users);
    }

    public Users update(Users users){
        if (!StringUtils.hasText(users.getId())) {
            throw new BadRequestException("Username harus diisi.");
        }
        if (!StringUtils.hasText(users.getEmail())) {
            throw new BadRequestException("Email harus diisi.");
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepository.save(users);
    }

    public void deleteById(String id) {
        usersRepository.deleteById(id);
    }

}
