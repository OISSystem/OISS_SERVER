package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchema
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorSchema.monitorSchema

class MonitorSchemaRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorSchemaRepositoryCustom {

    override fun findByMonitorId(monitorId: Long): MonitorSchema? {
        return queryFactory
            .select(monitorSchema)
            .from(monitorSchema)
            .where(monitorSchema.monitorId.eq(monitorId))
            .fetchOne()
    }
}
