package VotingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import VotingSystem.entities.Candidate;
import VotingSystem.entities.VoteRecord;
import VotingSystem.entities.Voter;

public class VotingSystem {
    private static volatile VotingSystem instance;
    private final Map<String, Voter> voters;
    private final Map<String, Candidate> candidates;
    private final ReadWriteLock votingLock;
    private volatile boolean isVotingOpen;
    private final Set<String> votedVoters;
    private final Map<String, AtomicInteger> voteCount;
    private final BlockingQueue<VoteRecord> voteAuditLog;

    private VotingSystem() {
        this.voters = new ConcurrentHashMap<>();
        this.candidates = new ConcurrentHashMap<>();
        this.voteCount = new ConcurrentHashMap<>();
        this.votedVoters = ConcurrentHashMap.newKeySet();
        this.votingLock = new ReentrantReadWriteLock();
        this.voteAuditLog = new LinkedBlockingQueue<>();
        this.isVotingOpen = false;

    }

    public static VotingSystem getInstance() {
        if (instance == null) {
            synchronized (VotingSystem.class) {
                if (instance == null) {
                    instance = new VotingSystem();
                }
            }
        }
        return instance;
    }

    public void registerVoter(String voterId, String voterName, String voterPassword) {
        System.out.println("Registered Voter: " + voterName + " with ID: " + voterId);
        Voter voter = new Voter(voterId, voterName, voterPassword);
        voters.putIfAbsent(voterId, voter);
    }

    public void registerCandidate(String candidateId, String candidateName, String candidateParty) {
        System.out.println("Registered Candidate: " + candidateName + " with ID: "
                + candidateId + " from Party: " + candidateParty);
        Candidate candidate = new Candidate(candidateId, candidateName, candidateParty);
        candidates.putIfAbsent(candidateId, candidate);
        voteCount.putIfAbsent(candidateId, new AtomicInteger(0));

    }

    void startVoting() {
        votingLock.writeLock().lock();
        try {
            isVotingOpen = true;
            System.out.println("Voting has started.");
        } finally {
            votingLock.writeLock().unlock();
        }
    }

    public void endVoting() {
        votingLock.writeLock().lock();
        try {
            isVotingOpen = false;
            System.out.println("Voting has ended!");
        } finally {
            votingLock.writeLock().unlock();
        }
    }

    boolean castVote(String voterId, String candidateId) {
        votingLock.readLock().lock();
        try {
            if (!isVotingOpen) {
                System.out.println("Voting is not open. Cannot cast vote.");
                return false;
            }

            Voter voter = voters.get(voterId);
            Candidate candidate = candidates.get(candidateId);

            if (voter == null) {
                System.out.println("Voter ID " + voterId + " not found.");
                return false;
            }

            if (candidate == null) {
                System.out.println("Candidate ID " + candidateId + " not found.");
                return false;
            }

            if (!votedVoters.add(voterId)) {
                System.out.println("Voter ID " + voterId + " has already voted.");
                return false;
            }

            voteCount.get(candidateId).incrementAndGet();
            voteAuditLog.add(new VoteRecord(voterId, candidateId, System.currentTimeMillis()));
            System.out.println("Voter ID " + voterId + " successfully voted for Candidate ID " + candidateId + ".");
            return true;

        } finally {
            votingLock.readLock().unlock();
        }
    }

    public Map<String, Integer> getCurrentResults() {
        Map<String, Integer> results = new HashMap<>();
        votingLock.readLock().lock();
        try {
            for (Map.Entry<String, AtomicInteger> entry : voteCount.entrySet()) {
                results.put(candidates.get(entry.getKey()).getCandidateName(),
                        entry.getValue().get());
            }
            return results;
        } finally {
            votingLock.readLock().unlock();
        }
    }

    public List<VoteRecord> getAuditLog() {
        return new ArrayList<>(voteAuditLog);
    }

}
