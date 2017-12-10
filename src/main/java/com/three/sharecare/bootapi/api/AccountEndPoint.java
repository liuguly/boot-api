package com.three.sharecare.bootapi.api;

import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.LoginRequest;
import com.three.sharecare.bootapi.dto.RegisterRequest;
import com.three.sharecare.bootapi.dto.UserInfoDto;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@Api(value = "用户模块")
@RestController
@RequestMapping(value = "/v1/user/")
public class AccountEndPoint {

    private static Logger logger = LoggerFactory.getLogger(AccountEndPoint.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation(value = "用户登录", notes = "根据用户名密码登录")
    @RequestMapping(value = "/api/accounts/login",method = RequestMethod.POST)
    public ResponseResult<AccountDto> login(@RequestBody @Valid LoginRequest request) {
        AccountDto accountDto = accountService.login(request);
        ResponseResult<AccountDto> result = new ResponseResult<>(true, "get token!", accountDto, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "用户登出", notes = "用户登出")
    @ApiImplicitParam(name = "token", value = "用户令牌", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/api/accounts/logout", method = RequestMethod.GET)
    public void logout(@RequestParam(value = "token", required = false) String token) {
        accountService.logout(token);
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @RequestMapping(value = "/api/accounts/register", method = RequestMethod.POST)
    public ResponseResult register(@RequestBody @Valid RegisterRequest request) {
        accountService.register(request);
        ResponseResult result =
                new ResponseResult<>(true, "regist success!", "", HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @RequestMapping(value = "/api/accounts/forget", method = RequestMethod.GET)
    public ResponseResult<String> forgetPassword(@RequestParam(value = "email") String email) {
        accountService.sendMail4ForgetPwd(email);
        return new ResponseResult<>(true, "success", "please see your mail!", HttpStatus.OK.value());
    }

    @ApiOperation(value = "用户信息", notes = "获取登录用户信息")
    @RequestMapping(value = "/api/accounts/user/{token}", method = RequestMethod.GET)
    public ResponseResult<AccountDto> getLoginUser(@PathVariable(value = "token") String token){
        AccountDto account = accountService.getLoginUser(token);
        return new ResponseResult<>(true, "success", account, HttpStatus.OK.value());
    }

    @ApiOperation(value = "修改信息", notes = "修改密码、用户名接口")
    @RequestMapping(value = "/api/accounts/user/update/{token}", method = RequestMethod.POST)
    public ResponseResult<AccountDto> updateUserInfo(@PathVariable(value = "token") String token, @RequestBody AccountDto updateAccount){
        accountService.checkToken(token);
        AccountDto account = accountService.updateAccount(token, updateAccount);
        return new ResponseResult<>(true, "success", account, HttpStatus.OK.value());
    }

    @ApiOperation(value = "编辑个人信息", notes = "修改个人详细信息，最新接口")
    @RequestMapping(value = "/api/accounts/update/userinfo", method = RequestMethod.POST)
    public ResponseResult<AccountDto> udpdateUserInfo(@RequestHeader(value = "token") String token, @Valid @RequestBody UserInfoDto userInfoDto){
        AccountDto currAccount = accountService.checkToken(token);
        AccountDto result = accountService.updateUserInfo(currAccount, userInfoDto);
        return new ResponseResult<>(true, "success", result, HttpStatus.OK.value());
    }

    @ApiOperation(value = "上传个人证书", notes = "上传个人证书")
    @RequestMapping(value = "/api/accounts/upload/certificate", method = RequestMethod.POST)
    public ResponseResult<String> uploadCertificate(HttpServletRequest request,@RequestHeader(value = "token") String token, @RequestPart(name = "file") MultipartFile multipartFile){
        AccountDto accountDto = accountService.checkToken(token);
        String fileType =  request.getParameter("fileType");
        String result = fileUploadService.getPhotoPath(request,multipartFile,accountDto.getId()+"",fileType);
        return new ResponseResult<>(true, "success", result, HttpStatus.OK.value());
    }

    @ApiOperation(value = "profile上传个人图片", notes = "profile上传个人图片")
    @RequestMapping(value = "/api/accounts/upload/user/icon", method = RequestMethod.POST)
    public ResponseResult<String> uploadUserIcon(HttpServletRequest request,@RequestHeader(value = "token") String token, @RequestPart(name = "file") MultipartFile multipartFile){
        AccountDto currAccount  = accountService.checkToken(token);
        String userIcon = accountService.uploadProfileImage(request,currAccount, multipartFile);
        return new ResponseResult<>(true, "success", userIcon, HttpStatus.OK.value());
    }

    @ApiOperation(value = "profile上传孩子图片", notes = "profile上传孩子图片")
    @RequestMapping(value = "/api/accounts/upload/child/icon", method = RequestMethod.POST)
    public ResponseResult<String> uploadChildIcon(@RequestHeader(value = "token") String token, @RequestPart(name = "file") MultipartFile multipartFile){
        AccountDto currAccount  = accountService.checkToken(token);
        String userIcon = fileUploadService.getPhotoPath(multipartFile,currAccount.getId()+"","childInfo");
        return new ResponseResult<>(true, "success", userIcon, HttpStatus.OK.value());
    }


    @ApiOperation(value = "profile更新孩子信息", notes = "profile更新孩子信息")
    @RequestMapping(value = "/api/accounts/update/childInfo", method = RequestMethod.POST)
    public ResponseResult<UserInfoDto> updateChildInfo(@RequestHeader(value = "token") String token,@RequestBody @Valid List<UserInfoDto.ChildrenInfo> childrenInfoList){
        AccountDto currAccount  = accountService.checkToken(token);
        UserInfoDto userInfo = accountService.updateChildInfo(currAccount,childrenInfoList);
        return new ResponseResult<>(true, "success", userInfo, HttpStatus.OK.value());
    }

}
