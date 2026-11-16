package dsm.oissbackend.oiss.monitor.persistent.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import dsm.oissbackend.oiss.monitor.persistent.entity.QMonitor.monitor
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MonitorStatus

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

    override fun findAllByFilter(
        offset: Long,
        limit: Long,
        status: MonitorStatus?,
        provider: String?,
        keyword: String?,
    ): List<Monitor> {
        return queryFactory
            .select(monitor)
            .from(monitor)
            .where(
                statusEq(status),
                providerEq(provider),
                nameContains(keyword),
            )
            .orderBy(monitor.id.desc())
            .offset(offset)
            .limit(limit)
            .fetch()
    }

    // TODO: 체크 결과 기반 상태(HEALTHY, WARNING, DOWN 등) 저장 후 정확한 필터로 교체
    //  현재는 PAUSED = 비활성, 그 외 상태 = 활성 모니터로만 구분
    private fun statusEq(status: MonitorStatus?): BooleanExpression? = when (status) {
        null -> null
        MonitorStatus.PAUSED -> monitor.enabled.isFalse
        else -> monitor.enabled.isTrue
    }

    private fun providerEq(provider: String?): BooleanExpression? =
        provider?.takeIf { it.isNotBlank() }?.let { monitor.provider.eq(it) }

    private fun nameContains(keyword: String?): BooleanExpression? =
        keyword?.takeIf { it.isNotBlank() }?.let { monitor.name.containsIgnoreCase(it) }
}
