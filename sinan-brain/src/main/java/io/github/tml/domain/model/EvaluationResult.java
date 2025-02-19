package io.github.tml.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述: 评估结果对象
 * @author suifeng
 * 日期: 2025/2/19
 */
@Builder
@Data
public class EvaluationResult {

    private final String proxyId;
    private final double score;
    private final EvaluationStatus status;
    private final LocalDateTime evaluatedAt;
    
    public enum EvaluationStatus {
        ACTIVE, CANDIDATE, DISCARDED
    }

    public boolean isQualifiedForPool(double threshold) {
        return status == EvaluationStatus.ACTIVE && score >= threshold;
    }

    public boolean shouldDemoteToCandidate(double demoteThreshold) {
        return score < demoteThreshold && score >= demoteThreshold * 0.6;
    }
}