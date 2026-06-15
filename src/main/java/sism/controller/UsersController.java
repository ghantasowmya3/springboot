package sism.controller;

import sism.models.Users;
import sism.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UsersController {
    
    @Autowired
    private UsersService usersService;
    
    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody Users user) {
        return usersService.signup(user);
    }
    
    @PostMapping("/signin")
    public Map<String, Object> signin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        return usersService.signin(username, password);
    }
    
    @GetMapping("/uinfo")
    public Map<String, Object> getUserInfo(@RequestHeader("Token") String token) {
        return usersService.getUserInfo(token);
    }
    
    @GetMapping("/profile")
    public Map<String, Object> getProfile(@RequestHeader("Token") String token) {
        return usersService.getProfile(token);
    }
    
    @GetMapping("/getallusers/{page}/{size}")
    public Map<String, Object> getAllUsers(@PathVariable int page, @PathVariable int size, 
                                           @RequestHeader("Token") String token) {
        return usersService.getAllUsers(page, size, token);
    }
    
    @GetMapping("/searchuser/{keyword}")
    public Map<String, Object> searchUser(@PathVariable String keyword, 
                                          @RequestHeader("Token") String token) {
        return usersService.searchUser(keyword, token);
    }
    
    @PostMapping("/saveuser")
    public Map<String, Object> saveUser(@RequestBody Users user, 
                                        @RequestHeader("Token") String token) {
        return usersService.saveUser(user, token);
    }
    
    @PutMapping("/updateuser/{id}")
    public Map<String, Object> updateUser(@PathVariable Integer id, 
                                          @RequestBody Users user,
                                          @RequestHeader("Token") String token) {
        return usersService.updateUser(id, user, token);
    }
    
    @DeleteMapping("/deleteuser/{id}")
    public Map<String, Object> deleteUser(@PathVariable Integer id,
                                          @RequestHeader("Token") String token) {
        return usersService.deleteUser(id, token);
    }
    
}