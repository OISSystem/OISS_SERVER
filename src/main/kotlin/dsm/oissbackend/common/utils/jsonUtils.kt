package dsm.oissbackend.common.utils

import org.json.JSONObject
import org.springframework.stereotype.Component
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper



@Component
class jsonUtils {

    fun jsonToMap(json: String): MutableMap<String?, Any?>? {
        val objectMapper = ObjectMapper()
        val typeReference: TypeReference<MutableMap<String?, Any?>?> =
            object : TypeReference<MutableMap<String?, Any?>?>() {}

        return objectMapper.readValue(json, typeReference)
    }

    fun mapToJson(map: Map<String, String>): String {
        return JSONObject(map).toString()
    }
}