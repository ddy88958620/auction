package com.trump.auction.goods;

import com.trump.auction.goods.dao.ProductInfoDao;
import com.trump.auction.goods.domain.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsApplicationTests {
	@Autowired
	private ProductInfoDao productInfoDao;
	@Test
	public void contextLoads() {
        ProductInfo record = new ProductInfo();
        record.setId(1);
        productInfoDao.insert(record);
	}
}
