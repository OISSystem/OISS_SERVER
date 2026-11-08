package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorJsonPathRule
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorJsonPathRule.monitorJsonPathRule

class MonitorJsonPathRuleRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorJsonPathRuleRepositoryCustom {

    override fun findAllByMonitorId(monitorId: Long): List<MonitorJsonPathRule> {
        return queryFactory
            .select(monitorJsonPathRule)
            .from(monitorJsonPathRule)
            .where(monitorJsonPathRule.monitor.id.eq(monitorId))
            .orderBy(monitorJsonPathRule.sortOrder.asc())
            .fetch()
    }

    override fun deleteAllByMonitorId(monitorId: Long): Long {
        return queryFactory
            .delete(monitorJsonPathRule)
            .where(monitorJsonPathRule.monitor.id.eq(monitorId))
            .execute()
    }
}
