package com.cybage.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.constants.ConstantVars;
import com.cybage.model.Doctor;
import com.cybage.model.Users;
import com.cybage.service.NewProductService;
import com.cybage.service.UsersService;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {
	
	@Autowired
	UsersService usersService;
	
	
	@GetMapping("/test")
	public String hello() {
		return "users controller from the test mapping";
	}

	@GetMapping("/get-all-users")
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> allUsers = usersService.getAllUsers();
		return new ResponseEntity<List<Users>>(allUsers, HttpStatus.OK);
	}
	
	@GetMapping("/get-user-by-id/{usersId}")
	public ResponseEntity<Users> getUserById(@PathVariable Long usersId) {
		Users user = usersService.getUserById(usersId);
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}
	
	@GetMapping("/users-login/{email}")
	public ResponseEntity<String> loginWithEmail(@PathVariable String email) {
		String loginUserReponse = usersService.loginUserByEmail(email);
		return new ResponseEntity<String>(loginUserReponse, HttpStatus.OK);
	}
	
	@GetMapping("/verify-otp/{email}/{otp}")
	public ResponseEntity<String> verifyLoginWithOTP(@PathVariable String email, @PathVariable int otp) {
		String verifyLoginWithOTPResponse = usersService.verifyLoginWithOTPResponse(email, otp);
		return new ResponseEntity<String>(verifyLoginWithOTPResponse, HttpStatus.OK);
	}
	
	
	@GetMapping("/get-all-patients")
	public ResponseEntity<List<Users>> getAllPatients() {
		List<Users> allUsers = usersService.getAllPatients(3);
		return new ResponseEntity<List<Users>>(allUsers, HttpStatus.OK);
	}
	
	@GetMapping("/activate-account/{usersId}")
	public ResponseEntity<String> activateUsersAccount(@PathVariable Long usersId) {
		boolean isAccountVerified = usersService.activateAccount(usersId);
		if(isAccountVerified)
			return new ResponseEntity<String>(ConstantVars.EMAIL_VERIFIED_PAGE, HttpStatus.OK);
		else
			return new ResponseEntity<String>(ConstantVars.ERROR_RESPONSE, HttpStatus.OK);
	}
	
	
	
	
    @PostMapping("/add-doctor")
    public ResponseEntity<String> addUsers(@RequestBody Doctor doctor) {
//    	int usersRoleId, String firstname, String lastname, String email, String usersProfileUrl, String mobile, List<Integer> specializationId
//    	Users userr = new Users(1,"Chaitanya", "Deshmukh", "chaitanyadeshm@cybage.com",  "some url","1234567899", [1,4,6,2]);
          boolean isDoctorRegistered = usersService.addDoctor(doctor);
          if (isDoctorRegistered)
               return new ResponseEntity<String>("Doctor registered successfully\nCredentials has been sent to his email.", HttpStatus.CREATED);
          else
               return new ResponseEntity<String>(ConstantVars.ERROR_RESPONSE, HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/add-user")
    public ResponseEntity<String> addDoctor(@RequestBody Users user) {
//    	int usersRoleId, String firstname, String lastname, String email, String usersProfileUrl, String mobile
//    	Users userr = new Users(1,"Chaitanya", "Deshmukh", "chaitanyadeshm@cybage.com",  "some url","1234567899");
          boolean isUserAdded = usersService.addUser(user);
          if (isUserAdded)
               return new ResponseEntity<String>("User registered successfully\nVerification link has been sent to your email.", HttpStatus.CREATED);
          else
               return new ResponseEntity<String>(ConstantVars.ERROR_RESPONSE, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delete-user/{usersId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long usersId) {
    	String deleteUserRespone = usersService.deleteUserById(usersId);
    	return new ResponseEntity<String>(deleteUserRespone, HttpStatus.OK);
	
	
    }

}
