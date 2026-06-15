package sism.services;

import sism.models.*;
import sism.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UsersService {
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    @Autowired
    private MenusRepository menusRepository;
    
    @Autowired
    private RolesMappingRepository rolesMappingRepository;
    
    @Autowired
    private JwtService jwtService;
    
    public Map<String, Object> signup(Users user) {
        Map<String, Object> response = new HashMap<>();
        
        if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
            response.put("code", 400);
            response.put("message", "Email already exists");
            return response;
        }
        
        user.setRole(1); // Default role: Student
        user.setStatus(1);
        Users savedUser = usersRepository.save(user);
        
        response.put("code", 200);
        response.put("message", "User registered successfully");
        response.put("userId", savedUser.getId());
        return response;
    }
    
    public Map<String, Object> signin(String username, String password) {
        Map<String, Object> response = new HashMap<>();
        
        Optional<Users> userOpt = usersRepository.findByEmail(username);
        
        if (userOpt.isEmpty()) {
            response.put("code", 401);
            response.put("message", "Invalid credentials");
            return response;
        }
        
        Users user = userOpt.get();
        
        if (!user.getPassword().equals(password)) {
            response.put("code", 401);
            response.put("message", "Invalid credentials");
            return response;
        }
        
        if (user.getStatus() != 1) {
            response.put("code", 403);
            response.put("message", "Account is inactive");
            return response;
        }
        
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole());
        
        response.put("code", 200);
        response.put("jwt", token);
        response.put("role", user.getRole());
        return response;
    }
    
    public Map<String, Object> getUserInfo(String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer userId = jwtService.getUserIdFromToken(token);
            Optional<Users> userOpt = usersRepository.findById(userId);
            
            if (userOpt.isEmpty()) {
                response.put("code", 404);
                response.put("message", "User not found");
                return response;
            }
            
            Users user = userOpt.get();
            
            // Get menus for user's role - USE DISTINCT
            List<RolesMapping> roleMenus = rolesMappingRepository.findByRole(Long.valueOf(user.getRole()));
            
            // Use a Set to avoid duplicates
            Map<Long, Menus> uniqueMenus = new HashMap<>();
            
            for (RolesMapping rm : roleMenus) {
                Optional<Menus> menuOpt = menusRepository.findById(rm.getMid());
                if (menuOpt.isPresent()) {
                    Menus menu = menuOpt.get();
                    uniqueMenus.put(menu.getMid(), menu);
                }
            }
            
            List<Map<String, Object>> menuList = new ArrayList<>();
            for (Menus menu : uniqueMenus.values()) {
                Map<String, Object> menuItem = new HashMap<>();
                menuItem.put("mid", menu.getMid());
                menuItem.put("menu", menu.getMenu());
                menuItem.put("icon", menu.getIcon());
                menuList.add(menuItem);
            }
            
            // Sort by mid
            menuList.sort((a, b) -> Integer.compare((int)a.get("mid"), (int)b.get("mid")));
            
            response.put("code", 200);
            response.put("fullname", user.getFullname());
            response.put("role", user.getRole());
            response.put("menulist", menuList);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 401);
            response.put("message", "Invalid token: " + e.getMessage());
        }
        
        return response;
    }
    
    public Map<String, Object> getProfile(String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer userId = jwtService.getUserIdFromToken(token);
            Optional<Users> userOpt = usersRepository.findById(userId);
            
            if (userOpt.isEmpty()) {
                response.put("code", 404);
                return response;
            }
            
            Users user = userOpt.get();
            response.put("code", 200);
            response.put("id", user.getId());
            response.put("fullname", user.getFullname());
            response.put("phone", user.getPhone());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            
        } catch (Exception e) {
            response.put("code", 401);
        }
        
        return response;
    }
    
    public Map<String, Object> getAllUsers(int page, int size, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer userId = jwtService.getUserIdFromToken(token);
            int offset = (page - 1) * size;
            List<Users> users = usersRepository.findAllWithPagination(offset, size);
            long total = usersRepository.count();
            
            response.put("code", 200);
            response.put("users", users);
            response.put("total", total);
            response.put("page", page);
            response.put("size", size);
            
        } catch (Exception e) {
            response.put("code", 401);
        }
        
        return response;
    }
    
    public Map<String, Object> searchUser(String keyword, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            List<Users> users = usersRepository.searchUsers(keyword);
            response.put("code", 200);
            response.put("users", users);
            
        } catch (Exception e) {
            response.put("code", 401);
        }
        
        return response;
    }
    
    public Map<String, Object> saveUser(Users user, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            
            // Check if email already exists
            if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
                response.put("code", 400);
                response.put("message", "Email already exists");
                return response;
            }
            
            user.setStatus(1);
            Users saved = usersRepository.save(user);
            response.put("code", 200);
            response.put("message", "User saved successfully");
            response.put("userId", saved.getId());
            
        } catch (Exception e) {
            response.put("code", 401);
        }
        
        return response;
    }
    
    public Map<String, Object> updateUser(Integer id, Users user, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            
            Optional<Users> existingUser = usersRepository.findById(id);
            if (existingUser.isEmpty()) {
                response.put("code", 404);
                response.put("message", "User not found");
                return response;
            }
            
            user.setId(id);
            usersRepository.save(user);
            response.put("code", 200);
            response.put("message", "User updated successfully");
            
        } catch (Exception e) {
            response.put("code", 401);
        }
        
        return response;
    }
    
    public Map<String, Object> deleteUser(Integer id, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            
            Optional<Users> existingUser = usersRepository.findById(id);
            if (existingUser.isEmpty()) {
                response.put("code", 404);
                response.put("message", "User not found");
                return response;
            }
            
            usersRepository.deleteById(id);
            response.put("code", 200);
            response.put("message", "User deleted successfully");
            
        } catch (Exception e) {
            response.put("code", 401);
        }
        
        return response;
    }
}