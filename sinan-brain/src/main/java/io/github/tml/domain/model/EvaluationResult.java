package io.github.tml.domain.model;

import io.github.tml.domain.enums.EvaluationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 描述: 评估结果对象
 * @author suifeng
 * 日期: 2025/2/19
 */
@Builder
@Data
public class EvaluationResult {

    private EvaluationType evaluationType; // 新增评估类型
    private double score;
    private EvaluationStatus status;
    private LocalDateTime evaluatedAt;
    private Map<String, Object> metrics; // 详细指标数据

    public enum EvaluationStatus {
        ACTIVE, CANDIDATE, DISCARDED
    }

    public boolean isQualifiedForPool(double threshold) {
        return status == EvaluationStatus.ACTIVE
                && evaluationType == EvaluationType.QUICK_CHECK
                && score >= threshold;
    }

    public EvaluationResult transition(EvaluationStatus newStatus) {
        return EvaluationResult.builder().score(this.score).status(newStatus).evaluatedAt(LocalDateTime.now()).build();
    }
}