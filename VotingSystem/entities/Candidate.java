package VotingSystem.entities;

public class Candidate {
    private String id;
    private String name;
    private String party;

    public Candidate(String id, String name, String party) {
        this.id = id;
        this.name = name;
        this.party = party;
    }

    public String getCandidateId() {
        return id;
    }

    public String getCandidateName() {
        return name;
    }

    public String getCandidateParty() {
        return party;
    }   
}
