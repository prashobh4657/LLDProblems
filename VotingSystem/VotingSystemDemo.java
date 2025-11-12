package VotingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VotingSystemDemo {

    public static void run() {
        System.out.println("Starting VotingSystem Demo...");
        VotingSystem votingSystem = VotingSystem.getInstance();

        for (int i = 1; i <= 100; i++) {
            votingSystem.registerVoter("V" + i, "Voter " + i, "pass" + i);
        }

        for (int i = 1; i <= 26; i++) {
            votingSystem.registerCandidate("C" + i, "Candidate " + i, "Party " + (char) ('A' + (i - 1)));
        }

        votingSystem.startVoting();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Boolean>> futureList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            final String voterId = "V" + i;
            final String candidateId = "C" + ((i % 26) + 1);
            Future<Boolean> future = executorService.submit(
                    () -> {
                        try {
                            Thread.sleep(new Random().nextInt(1000));
                            return votingSystem.castVote(voterId, candidateId);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return false;
                        }
                    });
            futureList.add(future);
        }

        for (Future<Boolean> future : futureList) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        votingSystem.endVoting();

        Map<String, Integer> results = votingSystem.getCurrentResults();
        System.out.println("\nVoting Results:");
        results.forEach((candidate, votes) -> System.out.println(candidate + ": " + votes + " votes"));

        System.out.println("\nAudit Log:");
        votingSystem.getAuditLog().forEach(System.out::println);

        executorService.shutdown();

        System.out.println("Ending VotingSystem Demo...");
    }
}
