package com.bilal.meetingplanner;

import com.bilal.meetingplanner.entity.Equipment;
import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.entity.MeetingType;
import com.bilal.meetingplanner.repository.EquipmentRepository;
import com.bilal.meetingplanner.repository.MeetingRepository;
import com.bilal.meetingplanner.repository.MeetingRoomRepository;
import com.bilal.meetingplanner.repository.MeetingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
public class MeetingPlannerApplication {


	public static void main(String[] args) {
		SpringApplication.run(MeetingPlannerApplication.class, args);
	}


	@Bean
	@Transactional
	public CommandLineRunner persistData(MeetingRoomRepository meetingRoomRepository, MeetingTypeRepository meetingTypeRepository, EquipmentRepository equipmentRepository){
		return args -> {

			// save the equipments
			Equipment ecran = equipmentRepository.save(new Equipment(null,"Ecran"));
			Equipment pieuvre = equipmentRepository.save(new Equipment(null,"Pieuvre"));
			Equipment tableau =  equipmentRepository.save(new Equipment(null,"Tableau"));
			Equipment webcam = equipmentRepository.save(new Equipment(null,"Webcam"));

			// create meeting rooms
			meetingRoomRepository.save(new MeetingRoom(null,"E1001",23, null));
			meetingRoomRepository.save(new MeetingRoom(null,"E1002",10, Set.of(ecran)));
			meetingRoomRepository.save(new MeetingRoom(null,"E1003",8, Set.of(pieuvre)));
			meetingRoomRepository.save(new MeetingRoom(null,"E1004",4, Set.of(tableau)));
			meetingRoomRepository.save(new MeetingRoom(null,"E2001",4, null));
			meetingRoomRepository.save(new MeetingRoom(null,"E2002",15, Set.of(ecran,webcam)));
			meetingRoomRepository.save(new MeetingRoom(null,"E2003",7, null));
			meetingRoomRepository.save(new MeetingRoom(null,"E2004",9, Set.of(tableau)));
			meetingRoomRepository.save(new MeetingRoom(null,"E3001",13, Set.of(ecran,webcam,pieuvre)));
			meetingRoomRepository.save(new MeetingRoom(null,"E3002",8, null));
			meetingRoomRepository.save(new MeetingRoom(null,"E3003",9, Set.of(ecran,pieuvre)));
			meetingRoomRepository.save(new MeetingRoom(null,"E3004",4, null));

			// meeting types
			meetingTypeRepository.save(new MeetingType(null ,"VC", Set.of(ecran,webcam,pieuvre)));
			meetingTypeRepository.save(new MeetingType(null ,"SPEC", Set.of(tableau)));
			meetingTypeRepository.save(new MeetingType(null ,"RS", null));
			meetingTypeRepository.save(new MeetingType(null ,"RC", Set.of(ecran,tableau,pieuvre)));


		};
	}
}
