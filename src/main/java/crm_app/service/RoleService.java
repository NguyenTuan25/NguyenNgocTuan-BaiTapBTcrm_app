package crm_app.service;

import crm_app.entity.RoleEntity;
import crm_app.repository.RoleRepository;

import java.util.List;

public class RoleService {
    private RoleRepository roleRepository = new RoleRepository();

    private RoleRepository deleteRepository = new RoleRepository();

    private RoleRepository AddRoleRepository = new RoleRepository();

    private RoleRepository UpdateRoleRepository = new RoleRepository();

    public List<RoleEntity> getAllRole(){
        return roleRepository.findAll();
    }

    public boolean deleteRoleById(int id) {
        int count = deleteRepository.deleteById(id) ;

        return count > 0;
    }

    public boolean insertRole(String name, String description) {
        return AddRoleRepository.insert(name, description) > 0;
    }

    public RoleEntity getRoleById(int id) {
        return roleRepository.updateRoleById(id);
    }
    public void updateRole(int id, String name, String description) {
        UpdateRoleRepository.updateRole(id, name, description);
    }
}
