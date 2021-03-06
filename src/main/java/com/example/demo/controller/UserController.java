package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payloads.UserDto;
import com.example.demo.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/create-Users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.userService.createuser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

	}

	@PutMapping("/update-Users/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") Integer userId) {
		UserDto updateUserDto = this.userService.updateuser(userDto, userId);
		return ResponseEntity.ok(updateUserDto);

	}

	@DeleteMapping("/delete-Users/{userId}")
	public ResponseEntity<?> deleteuser(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId, (String) request.getSession().getAttribute("Email"));
		request.getSession().setAttribute("Email", null);
		return new ResponseEntity<>(Map.of("message", "user delete successfully"), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsers(HttpServletRequest request) {
		return ResponseEntity.ok(this.userService.getAllUsers((String) request.getSession().getAttribute("Email")));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUsersByID(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(this.userService.getUserbyId(userId));

	}

	@GetMapping("/userName/{name}")
	public ResponseEntity<List<String>> getUsersByName(@PathVariable("name") String userName,
			HttpServletRequest request) {
		return ResponseEntity
				.ok(this.userService.getUserByName(userName, (String) request.getSession().getAttribute("Email")));
	}

}
