package crm_app.service;

import crm_app.entity.RoleEntity;
import crm_app.entity.UserEntity;
import crm_app.entity.UserTaskStatusDTO;
import crm_app.repository.RoleRepository;
import crm_app.repository.UserRepository;
import crm_app.utils.MD5;

import java.util.List;

public class UserService {
    private UserRepository repository = new UserRepository();

    private UserRepository userRepository = new	UserRepository();


    private RoleRepository roleRepository = new RoleRepository();

    public List<UserEntity> getAllUser(){

        return repository.findAll();

    }

    public boolean deleteUserById(int id) {

        int count = userRepository.deleteById(id);

        return count > 0 ;
    }

    public List<RoleEntity> getAllRole(){
        return roleRepository.findAll();
    }

    public boolean insertUser(String email, String password, String fullname, int roleId){
        return userRepository.insert(email, MD5.getMd5(password), fullname, roleId) > 0;
    }

    public UserEntity getUserById(int id){
        return userRepository.updateById(id);
    }
    public void updateUser(int id, String email, String password, String fullname, int roleId) {
        userRepository.updateUser(id, email, MD5.getMd5(password), fullname, roleId);
    }

    public UserTaskStatusDTO getUserTaskStatus(int id){
        return repository.findUserTaskAndStatus(id);
    }
}
