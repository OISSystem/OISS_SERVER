package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuotaPattern
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorQuotaPattern.monitorQuotaPattern

class MonitorQuotaPatternRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorQuotaPatternRepositoryCustom {

    override fun findAllByMonitorId(monitorId: Long): List<MonitorQuotaPattern> {
        return queryFactory
            .select(monitorQuotaPattern)
            .from(monitorQuotaPattern)
            .where(monitorQuotaPattern.monitor.id.eq(monitorId))
            .fetch()
    }

    override fun deleteAllByMonitorId(monitorId: Long): Long {
        return queryFactory
            .delete(monitorQuotaPattern)
            .where(monitorQuotaPattern.monitor.id.eq(monitorId))
            .execute()
    }
}
