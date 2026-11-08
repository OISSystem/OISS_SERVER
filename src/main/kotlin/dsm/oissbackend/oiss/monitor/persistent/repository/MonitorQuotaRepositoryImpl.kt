package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuota
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorQuota.monitorQuota

class MonitorQuotaRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorQuotaRepositoryCustom {

    override fun findByMonitorId(monitorId: Long): MonitorQuota? {
        return queryFactory
            .select(monitorQuota)
            .from(monitorQuota)
            .where(monitorQuota.monitorId.eq(monitorId))
            .fetchOne()
    }
}
