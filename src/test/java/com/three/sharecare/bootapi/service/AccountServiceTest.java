package com.three.sharecare.bootapi.service;

import com.three.sharecare.bootapi.dto.EventDto;
import com.three.sharecare.bootapi.dto.PaymentDto;
import com.three.sharecare.bootapi.dto.SearchShareByConditionDto;
import com.three.sharecare.bootapi.utils.UUIDUtils;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springside.modules.utils.mapper.JsonMapper;

import java.util.UUID;

public class AccountServiceTest {

	@Test
	public void hash() throws Exception {
		System.out.println("hashPassword:" + AccountService.hashPassword("springside"));
	}

	@Test
	public void test(){
		JsonMapper jsonMapper = new JsonMapper();
		EventDto eventDto = new EventDto();
		System.out.println(jsonMapper.toJson(eventDto));
	}

	@Test
	public void testSer(){
		SearchShareByConditionDto searchShareByConditionDto = new SearchShareByConditionDto();
		JsonMapper jsonMapper = new JsonMapper();
		System.out.println(jsonMapper.toJson(searchShareByConditionDto));
	}

	@Test
	public void test4(){
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://front.dev.cn/index.php?g=Xian4&m=Xian4Control&a=queue";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		String body = "{\"card_no\":\"0000042573\",\"summary\":\"前面还有10人，您的序号是23号\",\"docName\":\"张三\",\"deptName\":\"眼科\",\"remark\":\"remark\"}";
		HttpEntity<String> httpEntity = new HttpEntity<>(body,httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
		System.out.println(response.getBody());
	}


	private static final String SEQUENCE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	@Test
	public void test5(){
		System.out.println(UUID.nameUUIDFromBytes(SEQUENCE.getBytes()).toString());
		System.out.println(UUIDUtils.getUUID().toUpperCase());
	}

	private JsonMapper jsonMapper = new JsonMapper();

	@Test
	public void test6(){
		PaymentDto paymentDto = new PaymentDto();
		System.out.println(jsonMapper.toJson(paymentDto));
	}
}
