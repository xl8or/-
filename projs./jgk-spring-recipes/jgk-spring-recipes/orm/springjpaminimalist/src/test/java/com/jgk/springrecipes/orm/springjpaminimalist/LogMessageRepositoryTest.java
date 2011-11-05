package com.jgk.springrecipes.orm.springjpaminimalist;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.orm.springjpaminimalist.domain.LogMessage;
import com.jgk.springrecipes.orm.springjpaminimalist.repository.LogMessageRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/com/jgk/springrecipes/orm/springjpaminimalist/LogMessageRepositoryTest-config.xml")
public class LogMessageRepositoryTest {
	
	@Autowired
	LogMessageRepository logMessageRepository;
	
	@Test
	public void jed() {
		LogMessage hemingway = LogMessage.createLogMessage("Old man and the Sea","Hemingway");
		LogMessage melville = LogMessage.createLogMessage("Moby Dick","Melville");
		List<LogMessage> msgs = new ArrayList<LogMessage>();
		msgs.add(hemingway);
		msgs.add(melville);
		for (LogMessage logMessage : msgs) {
			logMessageRepository.save(logMessage);
			assertNotNull(logMessage.getId());
		}
		
		List<LogMessage> stuff = logMessageRepository.findAll();
		assertNotNull(stuff);
		assertFalse(stuff.isEmpty());
		assertEquals(msgs.size(), stuff.size());
		
	}
}
