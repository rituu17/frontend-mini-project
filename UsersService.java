package com.cybage.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cybage.constants.ConstantMethods;
import com.cybage.constants.ConstantVars;
import com.cybage.model.Doctor;
import com.cybage.model.DoctorsSpecialization;
import com.cybage.model.Users;
import com.cybage.repository.DoctorsSpecializationRepository;
import com.cybage.repository.UsersAccountStatusRepository;
import com.cybage.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private DoctorsSpecializationRepository doctorsSpecializationRepository;
//	@Autowired
//	private UsersAccountStatusRepository usersAccountStatusRepository;

	public List<Users> getAllUsers() {
		List<Users> allUsers = usersRepository.findAll();
		return allUsers;
	}

	// method to get list of all patients type of user from users table
	public List<Users> getAllPatients(int usersRoleId) {
		List<Users> allPatients = usersRepository.findUsersByUsersRoleId(usersRoleId);
		return allPatients;
	}

	// method to register user into our database
	public boolean addUser(Users user) {
		user.setAccountCreated(new Timestamp(new Date().getTime()));
		user.setAccountStatus(2);
		user.setUsersOTP(null);
		user.setLoginAttempts(null);

		Users newRegisteredUser = usersRepository.save(user);

		if (newRegisteredUser != null) {
			return sendVerificationLinkToUsersMailId(newRegisteredUser);
		} else {
			return false;
		}
	}

	// method to send verification link to user's mail id which is just registered
	private boolean sendVerificationLinkToUsersMailId(Users user) {
		String toAddress = user.getEmail();
		String fromAddress = "Trng2@evolvingsols.com";
		String senderName = "Universal Health Services";
		String subject = "Verify your account ";

		String newContent = " <div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>"
				+ "  <div style='margin:50px auto;width:70%;padding:20px 0'>"
				+ "    <div style='border-bottom:1px solid #eee'>"
				+ "      <a href='' style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Universal Health Services</a>"
				+ "    </div>  <p style='font-size:1.1em'>Hi "+ user.getFirstname() + " " + user.getLastname()+",</p>"
				+ "    <p>Thank you for choosing Universal Health Services. Use the following button to verify your account before loggin in.</p>"
				+ "    <h2 style='background: #73c1d4;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'>"
				+ "    <a href='http://localhost:8085/users/activate-account/" + user.getUsersId()
				+ "'> Verify Your Account <a></h2>" + "    <p style='font-size:0.9em;'>Regards,<br/>Your UHS</p>"
				+ "    <hr style='border:none;border-top:1px solid #eee' />"
				+ "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>"
				+ "      <p>Universal Health Services Inc.</p>   <p>1600 Amphitheatre Parkway</p> <p>Pune</p>"
				+ "    </div> </div>	";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(newContent, true);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mailSender.send(message);

		return true;

	}

	public boolean activateAccount(Long usersId) {
		Users user = usersRepository.findUsersByUsersId(usersId);
		if (user != null) {
			user.setAccountStatus(1);
			usersRepository.save(user);
			return true;
		} else {
			System.out.println("this is else");
			return false;
		}

	}


	public boolean addDoctor(Doctor doctor) {
		Users userAsDoctor = new Users(2, doctor.getFirstname(), doctor.getLastname(), doctor.getEmail(),
				doctor.getUsersProfileUrl(), doctor.getMobile());
		userAsDoctor.setAccountCreated(new Timestamp(new Date().getTime()));
		userAsDoctor.setAccountStatus(1);
		userAsDoctor.setUsersOTP(null);
		userAsDoctor.setLoginAttempts(null);
		Users registeredDoctor = usersRepository.save(userAsDoctor);
		List<DoctorsSpecialization> allDoctorsSpecialization = new ArrayList<DoctorsSpecialization>();
		if (registeredDoctor != null) {
			// Add specialization of doctor to doctors_specialization table
			for (int i = 0; i < doctor.getSpecializationsId().size(); i++) {
				allDoctorsSpecialization.add(
						new DoctorsSpecialization(doctor.getSpecializationsId().get(i), registeredDoctor.getUsersId()));
			}
			doctorsSpecializationRepository.saveAll(allDoctorsSpecialization);
			return true;
		}

		return false;
	}

//	public boolean sendDoctorsCredentialdo()
	public Users getUserById(Long usersId) {
		return usersRepository.findUsersByUsersId(usersId);
	}

	public String deleteUserById(Long usersId) {
		Users user = usersRepository.findUsersByUsersId(usersId);
//		int accountStatuForDelete = usersAccountStatusRepository.findUsersAccountStatusIdByStatus("Deleted");
		int accountStatuForDelete = 5;
		if (user.getAccountStatus() == accountStatuForDelete) {
			return ConstantVars.USER_DELETED_FAILED;
		} else {
			user.setAccountStatus(accountStatuForDelete);
			usersRepository.save(user);
			return ConstantVars.USER_DELETED_SUCCESS;
		}
	}

	
	public String loginUserByEmail(String email) {
		Users user = usersRepository.findUsersByEmail(email);
		if(user.getUsersId() != null) {
			if(user.getAccountStatus() == 2) {
				// account is inactive
				return ConstantVars.VERIFY_ACCOUNT_BEFORE_LOGIN;
			}else if(user.getAccountStatus() == 3) {
				//account is blocked
				return ConstantVars.ACCOUNT_BLOCKED;
				
			}else if(user.getAccountStatus() == 4) {
				//account is blocked by admin
				return ConstantVars.ACCOUNT_BLOCKED_BY_ADMIN;
				
			}else if(user.getAccountStatus() == 5) {
				//account is deleted
				return ConstantVars.ACCOUNT_DELETED;
				
			}
			user.setUsersOTP(ConstantMethods.generateOtp());
			usersRepository.save(user);
			boolean isOTPSentSUccessfully = sendOTP(user);
			if(isOTPSentSUccessfully)
				return ConstantVars.OTP_SENT_SUCCESSFULLY;
		}else {
			return ConstantVars.USER_NOT_FOUND;
		}
		return null;
	}

	public String verifyLoginWithOTPResponse(String email, int otp) {
		Users user = usersRepository.findUsersByEmail(email);
		if(user.getAccountStatus() == 3) {
			//account is blocked
			return ConstantVars.ACCOUNT_BLOCKED;
		}
		if(user.getUsersOTP() == otp) {
			user.setUsersOTP(null);
			usersRepository.save(user);
			return ConstantVars.OTP_VERIFIED_SUCCESSFULLY;
		} else {
			int remainingAttempt;
			if(user.getLoginAttempts() == null) {
				remainingAttempt = ConstantVars.MAX_LOGIN_ATTEMPTS_ALLOWED - 1; 
				user.setLoginAttempts(remainingAttempt);
				usersRepository.save(user);
				return ConstantVars.WRONG_OTP_ENTERED + "\nYou have " + remainingAttempt + " attempts to enter correct OTP.";
				
			}else if(user.getLoginAttempts() == 0){ 
				user.setLoginAttempts(null);
				user.setAccountStatus(3);
				usersRepository.save(user);
				return ConstantVars.MAX_OTP_ENTERED_EXCEEDED;
			}else {
				remainingAttempt = user.getLoginAttempts() - 1; 
				user.setLoginAttempts(remainingAttempt);
				usersRepository.save(user);
				return ConstantVars.WRONG_OTP_ENTERED + "\nYou have " + remainingAttempt + " attempts to enter correct OTP.";
			}
		}
	}


	public boolean sendOTP(Users user) {

		String toAddress = user.getEmail();
		String fromAddress = "Trng2@evolvingsols.com";
		String senderName = "Universal Health Services";
		String subject = "OTP for Sign In to your account ";

		String newContent = " <div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>"
				+ "  <div style='margin:50px auto;width:70%;padding:20px 0'>"
				+ "    <div style='border-bottom:1px solid #eee'>"
				+ "      <a href='' style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Universal Health Services</a>"
				+ "    </div>  <p style='font-size:1.1em'>Hi,</p>"
				+ "    <p>Thank you for choosing UHS. Use the following OTP to login to your account. OTP is valid for 5 minutes</p>"
				+ "    <h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'>"+user.getUsersOTP()+"</h2>"
				+ "    <p style='font-size:0.9em;'>Regards,<br/>Universal Health Services</p>"
				+ "    <hr style='border:none;border-top:1px solid #eee' />"
				+ "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>"
				+ "      <p>Universal Health Services Inc.</p>   <p>1600 Amphitheatre Parkway</p> <p>Pune</p>"
				+ "    </div> </div>	";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(newContent, true);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mailSender.send(message);

		return true;
	}

}
