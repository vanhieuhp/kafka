package dev.hieunv.consumer.detector.infrastructure.repository;

import dev.hieunv.consumer.detector.domain.repository.RuleState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RuleStateImpl implements RuleState {

    private final RedissonClient redisClient;

    @SneakyThrows
    @Override
    public void addTransaction(String accountNumber, String transactionId, Long timestamp) {
        var transactionSet = redisClient.getScoredSortedSet(accountNumber);
        if (!transactionSet.add(timestamp, transactionId)) {
            throw new RuntimeException("Failed to add transaction");
        }
    }

    @Override
    public Integer countTransactionsInRange(String accountNumber, Long from, Long to) {
        var transactionSet = redisClient.getScoredSortedSet(accountNumber);
        return transactionSet.count(from, true, to, true);
    }

    @Override
    public void removeTransactionsBefore(String accountNumber, Long timestamp) {
        var transactionSet = redisClient.getScoredSortedSet(accountNumber);
        transactionSet.removeRangeByScoreAsync(0L, true, timestamp, true);
    }
}
