package cprail.ptc.service.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cprail.ptc.service.beans.SwitchView;
import cprail.ptc.service.beans.Track;
import cprail.ptc.service.beans.TrackView;
import cprail.ptc.service.beans.View;
import cprail.ptc.service.impl.TrackServiceImpl;

@RestController
public class TrackController {

	private static final String putJsonOp = "PUT http://admin:admin@127.0.0.1:5984/albums/{GUIID} -d {\"id\": \"%d\",\"name\": \"%s\" } ";
	private final AtomicLong counter = new AtomicLong();
	String couchdbResp = null;

	@Autowired
	private TrackServiceImpl svcImpl;

	@RequestMapping("/querySegNodeTypes")
	public String getSegNodeTypes() {

		couchdbResp = svcImpl.getConfig(); 

		return couchdbResp;

	}
	
	@RequestMapping("/querySegNodeTypeDim")
	public String getSegNodeTypeDim(
//	public TrackView getSegNodeTypeDim(
			@RequestParam(value = "segNodeType", defaultValue = "Please enter track type.") String trackSegNodeType) {

		return svcImpl.getTrackDim(trackSegNodeType); 

	}

	@RequestMapping("/createTrack")
	public String createTrack(
			@RequestParam(value = "trackId", defaultValue = "Please enter track code.") String trackCode,
			@RequestParam(value = "segNodeType", defaultValue = "Please enter track name.") String trackSegNodeType) {


		Track track = new Track();
		track.set_id(trackCode);
		track.setNodeType(trackSegNodeType);

		couchdbResp = svcImpl.create(track);

		return couchdbResp;

	}

	
	@RequestMapping("/querySubdivTracks")
	public String getSubdivTracks(@RequestParam(value="subdiv", required=true) String subdiv) {
		
		couchdbResp = svcImpl.getSubdivTracks(subdiv); 
		return couchdbResp;

	}

	
	@RequestMapping("/configTrack")
	public String configTrack(
			@RequestParam(value = "trackId", required=true) String id,
			@RequestParam(value="segNodeType", defaultValue = "") String segNodeType
			) {
		
//		TrackView trackView = svcImpl.getTrackDim(segNodeType);
		
		
		
		Track track = new Track();
		track.set_id(id);
		track.setDim(svcImpl.getTrackDim(segNodeType));
		track.setNodeType(segNodeType);
		track.setFill("#D7DBDD");
		track.setStroke("#000000");
		track.setStrokeWidth("2");
		
//		track.addView("TRACK", trackView);
		
		couchdbResp = svcImpl.configTrack(track); 
		return couchdbResp;
	}
	
	
	@RequestMapping("/configSwitch")
	public String configSwitch(@RequestParam(value = "trackId", required=true) String id,
			@RequestParam(value = "switchType", required=true) String switchType) {
		
		SwitchView switchView = svcImpl.getSwitchDim(switchType);
		
		Track track = new Track();
		track.set_id(id);
//		track.addView("SWITCH", switchView);
		
		couchdbResp = svcImpl.configSwitch(track); 
		return couchdbResp;
	}

}















