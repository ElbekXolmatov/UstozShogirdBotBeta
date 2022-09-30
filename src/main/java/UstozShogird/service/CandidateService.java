package UstozShogird.service;

import UstozShogird.db.Database;
import UstozShogird.entity.Announce;
import UstozShogird.files.WorkWithFiles;


import java.util.UUID;

public class CandidateService {
    public static Announce getCandidateById(String id){
        return Database.announceList.stream()
                .filter(candidate -> candidate.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Announce addCandidate(Announce reqCandidate){
        Announce candidate = new Announce(String.valueOf(UUID.randomUUID()),
                reqCandidate.getFullName(),
                reqCandidate.getTechnology(),
                reqCandidate.getPhoneNumber(),
                reqCandidate.getPlace(),
                reqCandidate.getPrice(),
                reqCandidate.getJob(),
                reqCandidate.getTimeToCall(),
                reqCandidate.getPurpose()

        );

        Database.announceList.add(candidate);
        WorkWithFiles.writeCandidateList();
        return candidate;
    }
}
