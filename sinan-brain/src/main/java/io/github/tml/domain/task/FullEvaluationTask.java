package io.github.tml.domain.task;

import io.github.tml.delegate.CandidateQueueManager;
import io.github.tml.delegate.ProxyEvaluator;
import io.github.tml.domain.gateway.PersistenceGateway;
import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FullEvaluationTask {
    private final ProxyEvaluator evaluator;
    private final CandidateQueueManager queueManager;
    private final PersistenceGateway persistenceGateway;

    /**
     * 执行完整复杂评估流程
     */
    public void execute(int batchSize, int refillThreshold) {
        // 1. 检查队列容量
        if (queueManager.queueSize() < refillThreshold) {
            refillFromPersistence(refillThreshold - queueManager.queueSize());
        }

        // 2. 获取候选代理
        List<ProxyWrapper> candidates = queueManager.pollProxies(batchSize);

        // 3. 执行深度评估
        List<ProxyWrapper> evaluated = evaluator.evaluateBatch(
            candidates, 
            ProxyEvaluator.EvaluationMode.FULL
        );

        // 4. 处理结果
        processEvaluatedProxies(evaluated);
    }

    private void refillFromPersistence(int needCount) {
        List<ProxyWrapper> newProxies = persistenceGateway.getCandidateProxies(
            0.0, 
            Double.MAX_VALUE, 
            needCount
        );
        queueManager.batchAddToCandidate(newProxies);
    }

    private void processEvaluatedProxies(List<ProxyWrapper> proxies) {
        proxies.forEach(p -> {
            if (p.getEvaluationResult().getStatus() == EvaluationResult.EvaluationStatus.ACTIVE) {
                queueManager.addToCandidate(p);
            }
        });
    }
}
