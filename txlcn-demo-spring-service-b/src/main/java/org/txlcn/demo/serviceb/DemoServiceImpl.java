package org.txlcn.demo.serviceb;

import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.txlcn.demo.common.db.domain.Demo;

import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    private final DemoMapper demoMapper;
    private ConcurrentHashMap<String, Set<Long>> ids = new ConcurrentHashMap<>();

    @Autowired
    public DemoServiceImpl(DemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }

    @Override
    @TccTransaction(confirmMethod = "confirmRpc", cancelMethod = "cancelRpc",propagation = DTXPropagation.SUPPORTS)
    @Transactional
    public String rpc(String value) {


        Demo demo = new Demo();
        Random random = new Random();
        random.ints(101, 200);
        demo.setId(Long.valueOf(random.nextInt(200)));
        demo.setGroupId(TracingContext.tracing().groupId());
        demo.setDemoField(value + "-service-b");
        demo.setAppName(Transactions.getApplicationId());
        demo.setCreateTime(new Date());
        demoMapper.save(demo);

        ids.put(TracingContext.tracing().groupId(), Sets.newHashSet(demo.getId()));
        ids.get(TracingContext.tracing().groupId()).add(demo.getId());
        return "ok-service-b";
    }

    public void confirmRpc(String value) {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            log.info("tcc-confirm-{}-{}" + TracingContext.tracing().groupId(), id);
            System.out.print(" bbbb -- tcc-confirm " + TracingContext.tracing().groupId() + "id===" + id);
            ids.get(TracingContext.tracing().groupId()).remove(id);
        });
    }

    public void cancelRpc(String value) {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            log.info("tcc-cancel-{}-{}", TracingContext.tracing().groupId(), id);
            System.out.print(" bbbb -- tcc-cancel " + TracingContext.tracing().groupId() + "id===" + id);
            demoMapper.deleteByKId(id);
        });
    }
}
