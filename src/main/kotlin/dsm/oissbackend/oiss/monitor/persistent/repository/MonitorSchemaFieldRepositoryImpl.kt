package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchemaField
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitorSchemaField.monitorSchemaField

class MonitorSchemaFieldRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): MonitorSchemaFieldRepositoryCustom {

    override fun findAllByMonitorId(monitorId: Long): List<MonitorSchemaField> {
        return queryFactory
            .select(monitorSchemaField)
            .from(monitorSchemaField)
            .where(monitorSchemaField.schema.monitorId.eq(monitorId))
            .orderBy(monitorSchemaField.id.asc())
            .fetch()
    }

    override fun deleteAllByMonitorId(monitorId: Long): Long {
        return queryFactory
            .delete(monitorSchemaField)
            .where(monitorSchemaField.schema.monitorId.eq(monitorId))
            .execute()
    }
}
