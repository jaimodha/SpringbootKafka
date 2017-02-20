package com.transcendinsights

import com.transcendinsights.Model.KafkaModel
import com.transcendinsights.config.Receiver
import com.transcendinsights.config.Sender

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import groovy.json.*

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaApplicationTests {

	@Autowired
	private Sender sender;

	@Autowired
	private Receiver receiver;

	@Test
	public void testReceiver() throws Exception {
		KafkaModel kafkaModel = new KafkaModel()
		kafkaModel.with {
			id = 100 as Long
			name = 'Jai'
		}

		sender.sendMessage("test", new JsonBuilder(kafkaModel).toPrettyString());

		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	}
}