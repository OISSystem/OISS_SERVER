package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorHeader
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorHeader.monitorHeader

class MonitorHeaderRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorHeaderRepositoryCustom {

    override fun findAllByMonitorId(monitorId: Long): List<MonitorHeader> {
        return queryFactory
            .select(monitorHeader)
            .from(monitorHeader)
            .where(monitorHeader.monitor.id.eq(monitorId))
            .orderBy(monitorHeader.sortOrder.asc())
            .fetch()
    }

    override fun deleteAllByMonitorId(monitorId: Long): Long {
        return queryFactory
            .delete(monitorHeader)
            .where(monitorHeader.monitor.id.eq(monitorId))
            .execute()
    }
}
