package cprail.ptc.service.impl;

import cprail.ptc.service.beans.Track;
import cprail.ptc.service.beans.TrackView;

interface TrackService {

	public String getConfig() ;
	
//	public TrackView getTrackDim(String type) ;
	public String getTrackDim(String type) ;
	
	public String create(Track track) ;
	
	public String configTrack(Track track);
	
	public String getSubdivTracks(String subdiv);

	public String configSwitch(Track track);
	

	
}
