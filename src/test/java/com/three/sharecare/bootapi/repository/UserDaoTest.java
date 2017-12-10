package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.Event;
import com.three.sharecare.bootapi.domain.Favorite;
import com.three.sharecare.bootapi.domain.ShareCare;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDaoTest {

	@Autowired
	private AccountDao bookDao;

	@Autowired
	private ShareCareDao shareCareDao;

	@Autowired
	private FavoriteDao favoriteDao;

	RestTemplate restTemplate;

	@Test
	public void testDao(){
		PageRequest pageRequest = new PageRequest(1,50,new Sort(Sort.Direction.DESC,"create_time"));
//		Page<ShareCare> result = shareCareDao.findAllShare(pageRequest);
//		for(ShareCare shareCare : result){
//			System.out.println(shareCare.getHeadline());
//		}
//		System.out.println(result);

	}

	@Test
	public void testTop5(){
		PageRequest pageRequest = new PageRequest(1,5,new Sort(Sort.Direction.DESC,"createTime"));
		Page<ShareCare> result = shareCareDao.findAll(pageRequest);
		for(ShareCare shareCare : result){
			System.out.println(shareCare.getHeadline());
		}
		System.out.println(result);
	}

	@Autowired
	private EventDao eventDao;

	@Test
	public void testEvent(){
		PageRequest pageRequest = new PageRequest(0,5,new Sort(Sort.Direction.DESC,"createTime"));
		Page<Event> result = eventDao.findAll(pageRequest);
		for(Event shareCare : result){
			System.out.println(shareCare.getAddress());
		}
		System.out.println(result);
	}

	@Test
	public void testGetFavoriteList(){
		PageRequest pageRequest = new PageRequest(0,5,new Sort(Sort.Direction.DESC,"create_time"));
		Page<ShareCare> result = shareCareDao.findFavoriteShareCare(2L,0,pageRequest);
		for(ShareCare shareCare : result){
			System.out.println(shareCare.getAddress());
		}
	}

	@Test
	public void testG(){
		List<Favorite> result = favoriteDao.findById(0);
		for(Favorite favorite:result){
			System.out.println(favorite.getfTypeId());
		}
	}

	@Test
	public void testGetShare(){
		PageRequest pageRequest = new PageRequest(0,5,new Sort(Sort.Direction.DESC,"createTime"));
		List<ShareCare> result = shareCareDao.findAllWithReviews(pageRequest);
		System.out.println(result);
	}

}
