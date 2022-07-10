package net.javaguides.springboot.controller;

import java.util.List;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.entity.Users;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserControler {
   @Autowired
   private UserRepository userRepository;
   
   //get all users
   @GetMapping
   public List<Users> getAllUsers()
   {
	   return this.userRepository.findAll();
   }
   //get by user id
   @GetMapping("/{id}")
   public Users getUsersById(@PathVariable(value= "id")long userId)
   {
	   return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found Exce" + userId));
   }
   //create users
   @PostMapping
   public Users creatuser(@RequestBody Users user) {
	   return this.userRepository.save(user);
   }
   @PutMapping("/{id}")
   public Users updateuser(@RequestBody Users user,@PathVariable("id") long userID)
   {
	   Users existingUSer = this.userRepository.findById(userID).
			   orElseThrow(()-> new ResourceNotFoundException("user not foumd exce : " + userID ));
	   existingUSer.setFirstname(user.getFirstname());
	   existingUSer.setLastname(user.getLastname());
	   existingUSer.setEmail(user.getEmail());
	   return this.userRepository.save(existingUSer);
   }
   
   //delete User
   @DeleteMapping("/{id}")
   public ResponseEntity<Users> deleteUser (@PathVariable("id") long userID)
   {
	   Users existingUSer = this.userRepository.findById(userID).
			   orElseThrow(()-> new ResourceNotFoundException("user not foumd exce : " + userID ));
   this.userRepository.delete(existingUSer);
   return ResponseEntity.ok().build();
   }
   
}
