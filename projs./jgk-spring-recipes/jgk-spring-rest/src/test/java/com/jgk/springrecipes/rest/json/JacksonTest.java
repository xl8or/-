package com.jgk.springrecipes.rest.json;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jgk.springrecipes.rest.json.User.Gender;


/**
 * http://wiki.fasterxml.com/JacksonInFiveMinutes
 * @author jkroub
 *
 */
public class JacksonTest {
	private ObjectMapper mapper;
	private File userJsonFile;
	private File streamingUserJsonFile;
	@Before
	public void setup() {
		mapper = new ObjectMapper(); // can reuse, share globally
		URL url = JacksonTest.class.getClassLoader().getResource("testdata/user.json");
		System.out.println("FILE IS: " + url);
		userJsonFile = new File(url.getFile());
		url = JacksonTest.class.getClassLoader().getResource("testdata/streaming.user.json");
		streamingUserJsonFile = new File(url.getFile());
		
	}
	
	@Ignore
	@Test public void testStreaming() throws JsonGenerationException, IOException {
		JsonFactory f = new JsonFactory();
		JsonGenerator g = f.createJsonGenerator(streamingUserJsonFile,JsonEncoding.UTF8);

		g.writeStartObject();
		g.writeObjectFieldStart("name");
		g.writeStringField("first", "Joe");
		g.writeStringField("last", "Sixpack");
		g.writeEndObject(); // for field 'name'
		g.writeStringField("gender", Gender.MALE.toString());
		g.writeBooleanField("verified", false);
		g.writeFieldName("userImage"); // no 'writeBinaryField' (yet?)
//		byte[] binaryData = ...;
//		g.writeBinary(binaryData);
		g.writeEndObject();
		g.close(); // important: will force flushing of output, close underlying output stream
	}
	
	@Test public void testTree() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		// can either use mapper.readTree(JsonParser), or bind to JsonNode
		JsonNode rootNode = m.readValue(userJsonFile, JsonNode.class);
		// ensure that "last name" isn't "Xmler"; if is, change to "Jsoner"
		JsonNode nameNode = rootNode.path("name");
		String lastName = nameNode.path("last").getTextValue();
		if ("xmler".equalsIgnoreCase(lastName)) {
		  ((ObjectNode)nameNode).put("last", "Jsoner");
		}
		// and write it out:
		m.writeValue(new File("target/user-modified-tree.json"), rootNode);
	}

	@Test
	public void testSimpleBinding() {
		Map<String,Object> userData = new HashMap<String,Object>();
		Map<String,String> nameStruct = new HashMap<String,String>();
		nameStruct.put("first", "Joe");
		nameStruct.put("last", "Sixpack");
		userData.put("name", nameStruct);
		userData.put("gender", "MALE");
		userData.put("verified", Boolean.FALSE);
		userData.put("userImage", "Rm9vYmFyIQ==");
		try {
			mapper.writeValue(new File("target/user-modified-map.json"), userData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSimpleFromString() {
		//System.out.println(getJson());
		User user = null;
		try {
			user = mapper.readValue(getJson(), User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(user);
		try {
			mapper.writeValue(new File("target/user-modified.json"), user);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testSimpleFromFile() {
		//System.out.println(getJson());
		URL url = JacksonTest.class.getClassLoader().getResource("testdata/user.json");
		System.out.println("FILE IS: " + url);
		File theUserJsonFile = new File(url.getFile());

		User user = null;
		try {
			//System.out.println(userJsonFile.getAbsolutePath());
			user = mapper.readValue(theUserJsonFile, User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(user);
		try {
			mapper.writeValue(new File("target/user-modified.json"), user);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String getJson() {
		StringBuilder sb = new StringBuilder();
		String SEP = "\n";
		sb.append("{").append(SEP);
		sb.append("\"name\" : { \"first\" : \"Joe\", \"last\" : \"Sixpack\" },").append(SEP);
		sb.append("\"gender\" : \"MALE\",").append(SEP);
		sb.append("\"verified\" : false,").append(SEP);
		sb.append("\"userImage\" : \"Rm9vYmFyIQ==\"").append(SEP);
		sb.append("}").append(SEP);
		return sb.toString();
	}
}
