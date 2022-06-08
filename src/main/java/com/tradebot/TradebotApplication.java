package com.tradebot;

import com.tradebot.model.Share;
import com.tradebot.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TradebotApplication implements CommandLineRunner {
	@Autowired
	ShareRepository shareRepository;

	public static void main(String[] args) {
		SpringApplication.run(TradebotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Share> shareList= new ArrayList<>();
		Share sptl=new Share(5.5,7.2,11.8);
		Share reliance=new Share(60.00,132.56,425.23);

		shareList.add(sptl);
		shareList.add(reliance);
		shareRepository.insert(shareList);
	}
}
