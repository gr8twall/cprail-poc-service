package cprail.ptc.service;

import java.util.stream.Stream;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

@SpringBootApplication
public class CprailPtcService {

	public static void main(String[] args) {
		SpringApplication.run(CprailPtcService.class, args);
	}
	
}

//@Component
//class InitDataClr implements CommandLineRunner {
//
//	private final TestTrackRepository trackRepo;
//	
//		
//	public InitDataClr(TestTrackRepository trackRepo) {
//		super();
//		this.trackRepo = trackRepo;
//	}
//
//
//
//	@Override
//	public void run(String... arg0) throws Exception {
//
//		Stream.of("track1","track2","track3")
//			.forEach(track -> trackRepo.save(new TestTrack(track)));
//		
//		trackRepo.findAll().forEach(System.out::println);
//		
//	}
//	
//}
//
//@RepositoryRestResource
//interface TestTrackRepository extends JpaRepository<TestTrack, Long> {
//	
//}
//
//
//@Entity
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
//class TestTrack {
//
//	@Id
//	@GeneratedValue
//	private Long id;
//	
//	
//	private String trackName;
//	
//	TestTrack(String trackName) {
//		this.trackName = trackName;
//	}
//	
//	TestTrack() {
//		super();
//	}
////
////
////	public TestTrack(Long id, String trackName) {
////		super();
////		this.id = id;
////		this.trackName = trackName;
////	}
//
//
//	
//}
















