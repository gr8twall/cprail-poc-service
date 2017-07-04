package cprail.ptc.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cprail.ptc.service.beans.SwitchView;
import cprail.ptc.service.beans.Track;
import cprail.ptc.service.beans.TrackView;

@Component
public class TrackServiceImpl implements TrackService {
	
	private final String DB_TRACKS_SUBDIV_DUMMY = "tracks_subdiv_dummy";
	private final String DB_TRACKS_SEG_NODE_TYPE = "track_seg_node_type_config";

	@Override
	public String getConfig() {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		HttpGet get = null;

		String couchdbResp = null;
		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");
		StringBuilder db = new StringBuilder(DB_TRACKS_SEG_NODE_TYPE);

		httpClient = HttpClients.createDefault();

		db.append("/");
		db.append("_all_docs");
		url.append(db);
		get = new HttpGet(url.toString());
		get.setHeader("Content-Type", "application/json");
		get.setHeader("Accept", "application/json");

		try {

			response = httpClient.execute(get);
			couchdbResp = EntityUtils.toString(response.getEntity());

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
		}

		return couchdbResp;

	}

	@Override
	public String getTrackDim(String segNodeType) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpGet get = null;
		
		TrackView view = new TrackView();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = null;

		String couchdbResp = null;
		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");
		String db = null;

		httpClient = HttpClients.createDefault();

		db = DB_TRACKS_SEG_NODE_TYPE +"/"+ segNodeType +"/";
		url.append(db);
		get = new HttpGet(url.toString());
		get.setHeader("Content-Type", "application/json");
		get.setHeader("Accept", "application/json");

		try {

			response = httpClient.execute(get);
			couchdbResp = EntityUtils.toString(response.getEntity());
			node = mapper.readValue(couchdbResp, ObjectNode.class);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
		}
		
		view.setDim(node.get("dim").asText());
		
//		return view;
		return node.get("dim").asText();
	}

	@Override
	public String create(Track track) {
		
		
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpPost post = null;
		ObjectMapper mapper = new ObjectMapper();
		
		String couchdbResp = null;
		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");

		httpClient = HttpClients.createDefault();

		url.append(DB_TRACKS_SUBDIV_DUMMY);
		String body = "";
		
		post = new HttpPost(url.toString());
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Accept", "application/json");
		
		try {
			
			body = mapper.writeValueAsString(track);
			post.setEntity((HttpEntity) new StringEntity(body, "UTF-8"));
			
			response = httpClient.execute(post);
			couchdbResp = EntityUtils.toString(response.getEntity());

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}

		return couchdbResp;

	}

	@Override
	public String configTrack(Track track) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpPut put = null;
		httpClient = HttpClients.createDefault();

		String couchdbResp = null;
		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");
		ObjectMapper mapper = new ObjectMapper();

		url.append(DB_TRACKS_SUBDIV_DUMMY);
		url.append("/");
		url.append(track.get_id());
		url.append("/");
		put = new HttpPut(url.toString());
		put.setHeader("Content-Type", "application/json");
		put.setHeader("Accept", "application/json");
		
		String rev = getRevision(track);
		
		put.setHeader("If-Match", rev);

		String body;
		track.setState("ACTIVE");

		try {

			body = mapper.writeValueAsString(track);
			put.setEntity((HttpEntity) new StringEntity(body, "UTF-8"));

			response = httpClient.execute(put);
			couchdbResp = EntityUtils.toString(response.getEntity());

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
		}

		return couchdbResp;

	}

	private String getRevision(Track track) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpGet get = null;
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = null;

		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");
		StringBuilder db = new StringBuilder(DB_TRACKS_SUBDIV_DUMMY);
		db.append("/");
		db.append(track.get_id());
		db.append("/");

		httpClient = HttpClients.createDefault();
		String couchdbResp ="";
		
		url.append(db);
		get = new HttpGet(url.toString());
		get.setHeader("Content-Type", "application/json");
		get.setHeader("Accept", "application/json");

		try {

			response = httpClient.execute(get);
			couchdbResp = EntityUtils.toString(response.getEntity());
			node = mapper.readValue(couchdbResp, ObjectNode.class);
			
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
		}

		return node.get("_rev").toString();
	}

	@Override
	public String getSubdivTracks(String subdiv) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		HttpGet get = null;

		String couchdbResp = null;
		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");
		StringBuilder db = new StringBuilder(DB_TRACKS_SUBDIV_DUMMY);

		httpClient = HttpClients.createDefault();

		switch (subdiv) {
		case "ALL":
			db.append("/_all_docs");
			break;
		default:
			db.append("/");
			db.append(subdiv);
			break;
		} 
			
		
		url.append(db);
		get = new HttpGet(url.toString());
		get.setHeader("Content-Type", "application/json");
		get.setHeader("Accept", "application/json");

		try {

			response = httpClient.execute(get);
			couchdbResp = EntityUtils.toString(response.getEntity());

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
		}

		return couchdbResp;

	}

	@Override
	public String configSwitch(Track track) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpPut put = null;
		httpClient = HttpClients.createDefault();

		String couchdbResp = null;
		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");
		ObjectMapper mapper = new ObjectMapper();

		url.append(DB_TRACKS_SUBDIV_DUMMY);
		url.append("/");
		url.append(track.get_id());
		url.append("/");
		put = new HttpPut(url.toString());
		put.setHeader("Content-Type", "application/json");
		put.setHeader("Accept", "application/json");
		
		String rev = getRevision(track);
		
		put.setHeader("If-Match", rev);

		String body;
		track.setState("ACTIVE");

		try {

			body = mapper.writeValueAsString(track);
			put.setEntity((HttpEntity) new StringEntity(body, "UTF-8"));

			response = httpClient.execute(put);
			couchdbResp = EntityUtils.toString(response.getEntity());

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
		}

		return couchdbResp;
	}

	public SwitchView getSwitchDim(String switchType) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpGet get = null;
		
		SwitchView view = new SwitchView();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = null;

		String couchdbResp = null;
		StringBuilder url = new StringBuilder("http://127.0.0.1:5984/");
		String db = null;

		httpClient = HttpClients.createDefault();

		db = DB_TRACKS_SEG_NODE_TYPE +"/"+ switchType +"/";
		url.append(db);
		get = new HttpGet(url.toString());
		get.setHeader("Content-Type", "application/json");
		get.setHeader("Accept", "application/json");

		try {

			response = httpClient.execute(get);
			couchdbResp = EntityUtils.toString(response.getEntity());
			node = mapper.readValue(couchdbResp, ObjectNode.class);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
		}
		
		view.setCx(node.get("cx").asText());
		view.setCy(node.get("cy").asText());
		view.setRadius(node.get("r").asText());
		
		return view;
	}

}
