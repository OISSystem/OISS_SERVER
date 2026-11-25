package dsm.oissbackend.common.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Component
class CryptoUtil(
    @Value("\${oiss.util.crypto.secret}")
    val secret: String
) {

    private val key: SecretKey = createKey(secret)
    private val secureRandom = SecureRandom()

    @Throws(Exception::class)
    fun encrypt(data: String): String {
        val iv = ByteArray(IV_LENGTH_BYTES).also(secureRandom::nextBytes)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(TAG_LENGTH_BITS, iv))
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(iv + encryptedBytes)
    }

    @Throws(java.lang.Exception::class)
    fun decrypt(encryptedData: String?): String {
        val decodedBytes = Base64.getDecoder().decode(encryptedData)
        require(decodedBytes.size >= IV_LENGTH_BYTES + TAG_LENGTH_BITS / 8) {
            "암호문 길이가 올바르지 않습니다."
        }
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(
            Cipher.DECRYPT_MODE,
            key,
            GCMParameterSpec(TAG_LENGTH_BITS, decodedBytes, 0, IV_LENGTH_BYTES)
        )
        val decryptedBytes = cipher.doFinal(decodedBytes, IV_LENGTH_BYTES, decodedBytes.size - IV_LENGTH_BYTES)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY_LENGTH_BYTES = 32
        private const val IV_LENGTH_BYTES = 12
        private const val TAG_LENGTH_BITS = 128

        // 키가 잘못되면 첫 암호화 시점이 아니라 애플리케이션 시작 시점에 실패하도록 생성자에서 검증한다.
        private fun createKey(secret: String): SecretKey {
            val keyBytes = try {
                Base64.getDecoder().decode(secret.trim())
            } catch (e: IllegalArgumentException) {
                throw IllegalStateException(
                    "oiss.util.crypto.secret은 Base64로 인코딩된 값이어야 합니다. (openssl rand -base64 32)", e
                )
            }
            check(keyBytes.size == KEY_LENGTH_BYTES) {
                "oiss.util.crypto.secret은 디코딩했을 때 ${KEY_LENGTH_BYTES}바이트여야 합니다. (현재 ${keyBytes.size}바이트)"
            }
            return SecretKeySpec(keyBytes, "AES")
        }
    }

    fun encryptAES256(
        text: String,
        initializationVector: String, // 초기화 벡터 16byte = 16char
    ): String {
        if (secret.length != 32 || initializationVector.length != 16) {
            try {
                throw RuntimeException("encryptionKey length must be 32 and initializationVector length must be 16")
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val ivParamSpec = IvParameterSpec(initializationVector.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec)
        val encrypted = cipher.doFinal(text.toByteArray(StandardCharsets.UTF_8))
        return Base64.getEncoder().encodeToString(encrypted)
    }
}
