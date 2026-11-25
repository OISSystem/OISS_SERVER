package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorJsonPathRule
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorJsonPathRuleRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MonitorJsonPathRuleFacade(
    private val monitorJsonPathRuleRepository: MonitorJsonPathRuleRepository,
) {
    fun save(monitorJsonPathRule: MonitorJsonPathRule): MonitorJsonPathRule {
        return monitorJsonPathRuleRepository.save(monitorJsonPathRule)
    }

    fun saveAll(monitorJsonPathRules: List<MonitorJsonPathRule>): List<MonitorJsonPathRule> {
        return monitorJsonPathRuleRepository.saveAll(monitorJsonPathRules)
    }

    fun findAllByMonitorId(monitorId: Long): List<MonitorJsonPathRule> {
        return monitorJsonPathRuleRepository.findAllByMonitorId(monitorId)
    }

    @Transactional
    fun deleteAllByMonitorId(monitorId: Long) {
        monitorJsonPathRuleRepository.deleteAllByMonitorId(monitorId)
    }
}
