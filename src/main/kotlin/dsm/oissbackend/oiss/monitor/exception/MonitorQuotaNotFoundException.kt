package dsm.oissbackend.oiss.monitor.exception

import dsm.oissbackend.common.error.exeption.OissException
import dsm.oissbackend.oiss.monitor.exception.errorcode.MonitorErrorCode

object MonitorQuotaNotFoundException : OissException(MonitorErrorCode.QUOTA_NOT_FOUND)
