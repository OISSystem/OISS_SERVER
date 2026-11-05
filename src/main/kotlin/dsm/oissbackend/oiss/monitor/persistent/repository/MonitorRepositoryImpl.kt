package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitor.monitor

class MonitorRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorRepositoryCustom {

    override fun findByMonitorId(monitorId: Long): Monitor? {
        return queryFactory
            .select(monitor)
            .from(monitor)
            .where(monitor.id.eq(monitorId))
            .fetchOne()
    }
}