package dsm.oissbackend.common.utils.cryptography

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class CryptoUtil(
    @Value("\${oiss.util.crypto.secret}")
    private val AES_SECRET_KEY: String
) {

    fun generateKey(): SecretKey {
        return SecretKeySpec(AES_SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
    }

    @Throws(Exception::class)
    fun encrypt(data: String): String {
        val key: SecretKey = generateKey()
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes: ByteArray? = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    @Throws(java.lang.Exception::class)
    fun decrypt(encryptedData: String?): String {
        val key: SecretKey = generateKey()
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes = Base64.getDecoder().decode(encryptedData)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}