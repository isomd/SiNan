package io.github.tml.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述: 评估结果对象
 * @author suifeng
 * 日期: 2025/2/19
 */
@Data
public class EvaluationResult {
    private final String proxyId;
    private final double score;
    private final EvaluationStatus status;
    private final LocalDateTime evaluatedAt;
    
    public enum EvaluationStatus {
        ACTIVE, CANDIDATE, DISCARDED
    }
}