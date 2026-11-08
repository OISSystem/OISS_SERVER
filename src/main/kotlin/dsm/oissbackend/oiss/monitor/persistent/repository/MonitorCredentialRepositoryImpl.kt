package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorCredential
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorCredential.monitorCredential

class MonitorCredentialRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorCredentialRepositoryCustom {

    override fun findByMonitorId(monitorId: Long): MonitorCredential? {
        return queryFactory
            .select(monitorCredential)
            .from(monitorCredential)
            .where(monitorCredential.monitorId.eq(monitorId))
            .fetchOne()
    }
}
