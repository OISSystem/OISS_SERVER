package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorErrorPattern
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorErrorPattern.monitorErrorPattern

class MonitorErrorPatternRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorErrorPatternRepositoryCustom {

    override fun findAllByMonitorId(monitorId: Long): List<MonitorErrorPattern> {
        return queryFactory
            .select(monitorErrorPattern)
            .from(monitorErrorPattern)
            .where(monitorErrorPattern.monitor.id.eq(monitorId))
            .orderBy(monitorErrorPattern.id.asc())
            .fetch()
    }

    override fun deleteAllByMonitorId(monitorId: Long): Long {
        return queryFactory
            .delete(monitorErrorPattern)
            .where(monitorErrorPattern.monitor.id.eq(monitorId))
            .execute()
    }
}
