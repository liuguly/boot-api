package com.three.sharecare.bootapi.service;

import com.fasterxml.jackson.databind.JavaType;
import com.three.sharecare.bootapi.domain.Account;
import com.three.sharecare.bootapi.domain.UserInfo;
import com.three.sharecare.bootapi.domain.UserType;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.LoginRequest;
import com.three.sharecare.bootapi.dto.RegisterRequest;
import com.three.sharecare.bootapi.dto.UserInfoDto;
import com.three.sharecare.bootapi.repository.AccountDao;
import com.three.sharecare.bootapi.repository.UserInfoDao;
import com.three.sharecare.bootapi.service.exception.ErrorCode;
import com.three.sharecare.bootapi.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.misc.IdGenerator;
import org.springside.modules.utils.text.EncodeUtil;
import org.springside.modules.utils.text.HashUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// Spring Bean的标识.
@Service
@Transactional
public class AccountService {

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * Json map
	 */
	private JsonMapper jsonMapper = new JsonMapper();


	/**
	 * 登录后缓存令牌
	 * @param request email和密码
	 * @return token
	 */
	@CachePut(cacheNames = {"accountCache"},key="#result.token")
	public AccountDto login(LoginRequest request) {
		//登录类型
		Integer loginType = request.getLoginType();

		Account account = null;

		if(UserType.PLATFORM.ordinal() == loginType){
			account = platFormLogin(request);
		}else if(UserType.FACEBOOK.ordinal() == loginType){
			account = faceBookLogin(request);
		}else if(UserType.INSTAGRAM.ordinal() == loginType){
			account = instagramLogin(request);
		}

		//生成token
		if(StringUtils.isBlank(account.getToken())){
			account.setToken(IdGenerator.uuid2());
			accountDao.save(account);
		}
		AccountDto result = BeanMapper.map(account, AccountDto.class);

		//转换为DTO
		UserInfo userInfo = account.getUserInfo();
		String children = userInfo.getChildren();
		String shareCareCertificateInfo = userInfo.getShareCareCertificateInfo();
		String babysittingCertificationInfo = userInfo.getBabysittingCertificateInfo();

		JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
		List<UserInfoDto.ChildrenInfo> childrenInfo = jsonMapper.fromJson(children,javaType);
		UserInfoDto.CertificateInfo shareCareCerficate = jsonMapper.fromJson(shareCareCertificateInfo,UserInfoDto.CertificateInfo.class);
		UserInfoDto.CertificateInfo babysittingCertificateInfo = jsonMapper.fromJson(babysittingCertificationInfo,UserInfoDto.CertificateInfo.class);

		UserInfoDto userInfoDto = new UserInfoDto();
		BeanUtils.copyProperties(userInfo,userInfoDto);
		userInfoDto.setChildren(childrenInfo);
		userInfoDto.setShareCareCertificateInfo(shareCareCerficate);
		userInfoDto.setBabysittingCertificateInfo(babysittingCertificateInfo);
		result.setUserInfoDto(userInfoDto);
		return result;
	}


	private Account faceBookLogin(LoginRequest request){
		String userId = request.getUserId();
		//查询账户
		Account account = accountDao.findByEmail(userId);
		if(account == null){
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setEmail(request.getUserId());
			registerRequest.setUserName(request.getUserName());
			registerRequest.setPassword(request.getPassword());
			registerRequest.setUserIcon(request.getUserIcon());
			registerRequest.setLoginType(UserType.FACEBOOK);
			account = register(registerRequest);
		}
		return account;
	}

	private Account instagramLogin(LoginRequest request){
		String userId = request.getUserId();
		//查询账户
		Account account = accountDao.findByEmail(userId);
		if(account == null){
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setEmail(request.getUserId());
			registerRequest.setUserName(request.getUserName());
			registerRequest.setPassword(request.getPassword());
			registerRequest.setUserIcon(request.getUserIcon());
			registerRequest.setLoginType(UserType.INSTAGRAM);
			account = register(registerRequest);
		}
		return account;
	}

	private Account platFormLogin(LoginRequest request){
		String userName = request.getUserName();
		//查询账户
		Account account = accountDao.findByEmail(userName);
		if(account == null){
			throw new ServiceException("User not exist", ErrorCode.UNAUTHORIZED);
		}
		if (!account.getHashPassword().equals(request.getPassword())) {
			throw new ServiceException("Password wrong", ErrorCode.UNAUTHORIZED);
		}
		return account;
	}


	/**
	 * 退出的时候清除当前用户缓存
	 * @param token 令牌
	 */
	@CacheEvict(cacheNames = {"accountCache"},key = "#token")
	public void logout(String token) {

	}

	/**
	 * 从缓存中获取用户信息
	 * @param token token
	 * @return 账户
	 */
	@Cacheable(cacheNames = {"accountCache"},key = "#token")
	public AccountDto getLoginUser(String token) {
		throw new ServiceException("User doesn't login", ErrorCode.UNAUTHORIZED);
	}

	/**
	 * 注册
	 * @param registerRequest 注册信息
	 * @return 帐号
	 */
	public Account register(RegisterRequest registerRequest) {
		String email = registerRequest.getEmail();
		String password = registerRequest.getPassword();
		String userName = registerRequest.getUserName();
		UserType userType = registerRequest.getLoginType();
		String userIcon = registerRequest.getUserIcon();
		String telephone = registerRequest.getTelephone();

		if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
			throw new ServiceException("Invalid parameter", ErrorCode.BAD_REQUEST);
		}

		Account account = accountDao.findByEmail(email);
		if (account != null) {
			throw new ServiceException("the email has registered!", ErrorCode.ACCOUNT_HAS_REGISTERED);
		}
		account = new Account();
		account.setEmail(email);
		account.setUserName(userName);
		account.setTelePhone(telephone);
		account.setHashPassword(password);
		account.setUserType(userType);
		UserInfo userInfo = new UserInfo();
		userInfo.setAccount(account);
		userInfo.setUserIcon(userIcon);
		accountDao.save(account);
		account.setUserInfo(userInfo);
		return account;
	}

	/**
	 * 更新操作，直接更新缓存
	 * @param token 令牌
	 * @param updateAccount 用户信息
	 * @return 更新后的用户信息
	 */
	@CachePut(cacheNames = {"accountCache"}, key = "#token")
	public AccountDto updateAccount(String token, AccountDto updateAccount){
		Account account = BeanMapper.map(updateAccount, Account.class);
		account.setHashPassword(updateAccount.getHashPassword());
		account.setToken(token);
		accountDao.save(account);
		return updateAccount;
	}

	/**
	 * 检查token
	 * @param token 令牌
	 * @return 获取缓存中的用户信息
	 */
	@Cacheable(cacheNames = {"accountCache"}, key = "#token")
	public AccountDto checkToken(String token){
		if(StringUtils.isBlank(token)){
			throw new ServiceException("Token not exist!", ErrorCode.UNAUTHORIZED);
		}
		throw new ServiceException("User doesn't login", ErrorCode.UNAUTHORIZED);
	}

	/**
	 * 编辑个人信息
	 * @param currAccount 当前用户
	 * @param userInfoDto 用户信息
	 * @return 更新后的实体
	 */
	public AccountDto updateUserInfo(AccountDto currAccount , UserInfoDto userInfoDto){
		UserInfoDto currUserInfo = currAccount.getUserInfoDto();
		Long id = currUserInfo.getId();
		//转换成entity
		UserInfo userInfo = userInfoDao.findOne(id);
		userInfo.setAboutMe(StringUtils.isBlank(userInfoDto.getAboutMe())? userInfo.getAboutMe() : userInfoDto.getAboutMe());
		userInfo.setFullName(StringUtils.isBlank(userInfoDto.getFullName())? userInfo.getFullName() : userInfoDto.getFullName());
		userInfo.setContactNumber(StringUtils.isBlank(userInfoDto.getContactNumber())? userInfo.getContactNumber():userInfoDto.getContactNumber());
		userInfo.setEmergencyContact(StringUtils.isBlank(userInfoDto.getEmergencyContact())?userInfo.getEmergencyContact():userInfoDto.getEmergencyContact());
		String children = jsonMapper.toJson(userInfoDto.getChildren());
		userInfo.setChildren(StringUtils.isBlank(children)?userInfo.getChildren():children);
		String shareCareCertificateInfo = jsonMapper.toJson((userInfoDto.getShareCareCertificateInfo()));
		userInfo.setShareCareCertificateInfo(StringUtils.isBlank(shareCareCertificateInfo)?userInfo.getShareCareCertificateInfo():shareCareCertificateInfo);
		String babysittingCertificateInfo = jsonMapper.toJson(userInfoDto.getBabysittingCertificateInfo());
		userInfo.setBabysittingCertificateInfo(StringUtils.isBlank(babysittingCertificateInfo)?userInfo.getBabysittingCertificateInfo():babysittingCertificateInfo);
		userInfo.setUserIcon(StringUtils.isBlank(userInfoDto.getUserIcon())? userInfo.getUserIcon() : userInfoDto.getUserIcon());
		userInfo.setAddress(StringUtils.isBlank(userInfoDto.getAddress())? userInfo.getAddress() : userInfoDto.getAddress());
		userInfo.setAddressLat(StringUtils.isBlank(userInfoDto.getAddressLat())?userInfo.getAddressLat() : userInfoDto.getAddressLat());
		userInfo.setAddressLon(StringUtils.isBlank(userInfoDto.getAddressLon())?userInfo.getAddressLon() : userInfoDto.getAddressLon());
		userInfoDao.save(userInfo);

		//转换为dto
		userInfoDto = convertUserInfoToDto(userInfo,userInfoDto);
		currAccount.setUserInfoDto(userInfoDto);
		return currAccount;
	}

	/**
	 * 更新孩子信息
	 * @param currAccount 当前账户
	 * @param childrenInfoList 孩子信息
	 * @return 用户信息
	 */
	public UserInfoDto updateChildInfo(AccountDto currAccount, List<UserInfoDto.ChildrenInfo> childrenInfoList){
		String childInfoList = jsonMapper.toJson(childrenInfoList);
		UserInfo userInfo = userInfoDao.findOne(currAccount.getId());
		userInfo.setChildren(childInfoList);
		UserInfoDto userInfoDto = currAccount.getUserInfoDto();
		userInfoDto = convertUserInfoToDto(userInfo,userInfoDto);
		//更新缓存里的信息
		currAccount.setUserInfoDto(userInfoDto);
		return userInfoDto;
	}

	/**
	 * 领域转dto
	 * @param userInfo 领域
	 * @param userInfoDto dto
	 * @return dto
	 */
	private UserInfoDto convertUserInfoToDto(UserInfo userInfo,UserInfoDto userInfoDto){
		//转换为dto
		String children4dto = userInfo.getChildren();
		String shareCareCertificateInfo4dto = userInfo.getShareCareCertificateInfo();
		String babysittingCertificationInfo = userInfo.getBabysittingCertificateInfo();
		JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
		List<UserInfoDto.ChildrenInfo> childrenInfo = jsonMapper.fromJson(children4dto,javaType);
		UserInfoDto.CertificateInfo shareCareCerficate = jsonMapper.fromJson(shareCareCertificateInfo4dto,UserInfoDto.CertificateInfo.class);
		UserInfoDto.CertificateInfo babysittingCertificateInfo4dto = jsonMapper.fromJson(babysittingCertificationInfo,UserInfoDto.CertificateInfo.class);

		BeanUtils.copyProperties(userInfo, userInfoDto);
		userInfoDto.setChildren(childrenInfo);
		userInfoDto.setShareCareCertificateInfo(shareCareCerficate);
		userInfoDto.setBabysittingCertificateInfo(babysittingCertificateInfo4dto);
		return userInfoDto;
	}


	/**
	 * 上传个人图片
	 * @param request http request
	 * @param currAccount 账户
	 * @param multipartFile 单个文件
	 * @return 路径
	 */
	public String uploadProfileImage(HttpServletRequest request,AccountDto currAccount , MultipartFile multipartFile){
		String userIcon = fileUploadService.getPhotoPath(request,multipartFile,currAccount.getId()+"","profile");
		return userIcon;
	}


	@Value(value = "${spring.mail.username}")
	private String shareCareMail;

	/**
	 * 发送邮件
	 * @param email 邮件地址
	 */
	@Transactional(readOnly = true)
	public void sendMail4ForgetPwd(String email){
		Account account = accountDao.findByEmail(email);
		if (account == null) {
			throw new ServiceException("Email not exist!", ErrorCode.UNAUTHORIZED);
		}

		String password = account.getHashPassword();
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(shareCareMail);
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("ShareCare password service");
		simpleMailMessage.setText("This is your password: "+password);
		javaMailSender.send(simpleMailMessage);
	}

	public static String hashPassword(String password) {
		return EncodeUtil.encodeBase64(HashUtil.sha1(password));
	}
}
