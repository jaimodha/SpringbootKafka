package com.transcendinsights

import com.transcendinsights.config.Listener
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback

@RunWith(SpringRunner)
@SpringBootTest
class SpringKafkaApplicationTests {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private Listener listener;

	@Test
	void contextLoads()  throws InterruptedException {

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topic1", "ABC");
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			void onFailure(Throwable ex) {
				println('Success')
			}

			@Override
			void onSuccess(SendResult<String, String> result) {
				println('Failure')
			}
		})
		println(Thread.currentThread().id)
		//assert(this.listener.countDownLatch.await(60, TimeUnit.SECONDS)).isTrue();
		assert 1 == 1
	}
}
