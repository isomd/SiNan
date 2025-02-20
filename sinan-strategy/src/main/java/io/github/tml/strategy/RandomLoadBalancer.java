package io.github.tml.strategy;

import io.github.tml.domain.Proxy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("random")
public class RandomLoadBalancer extends AbstractLoadBalancer{

    private final Random random = new Random();

    @Override
    public Proxy doSelect(List<Proxy> proxies) {
        int len = proxies.size();
        Proxy proxy = null;
        int totalWeight = 0;
        boolean sameWeight = true;
        for(int i = 0; i < len; i++){
            int currentWeight = proxies.get(i).getWeight();
            totalWeight += currentWeight;
            if(sameWeight && i>0 && currentWeight != proxies.get(i-1).getWeight()){
                sameWeight = false;
            }
        }
        if(!sameWeight && totalWeight > 0){
            int offset = random.nextInt(totalWeight);
            for(int i = 0; i < len; i++){
                offset -= proxies.get(i).getWeight();
                if(offset < 0){
                    proxy = proxies.get(i);
                    break;
                }
            }
        }else{
            proxy = proxies.get(random.nextInt(len));
        }
        return proxy;
    }
}
