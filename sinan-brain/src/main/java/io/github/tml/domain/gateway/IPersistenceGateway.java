package io.github.tml.domain.gateway;


import io.github.tml.common.ProxyIp;
import io.github.tml.domain.model.ProxyWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class IPersistenceGateway implements PersistenceGateway {

    @Override
    public List<ProxyWrapper> loadInitialProxies(int limit, int offset) {
        return null;
    }

    @Override
    public List<ProxyWrapper> getCandidateProxies(double minScore, double maxScore, int limit) {
        return null;
    }

    public ProxyWrapper convert(ProxyIp source) {
        return new ProxyWrapper(
                generateProxyId(source),
                LocalDateTime.now(),
                calculateInitialResponseTime(source),
                calculateInitialSuccessRate(source)
        );
    }

    private String generateProxyId(ProxyIp proxy) {
        return String.format("%s:%d-%s",
                proxy.getIp(),
                proxy.getPort(),
                proxy.getProtocol().name());
    }

    private double calculateInitialResponseTime(ProxyIp proxy) {
        return 0;
    }

    private double calculateInitialSuccessRate(ProxyIp proxy) {
        return 0;
    }
}
