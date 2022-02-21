package utils

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class EncryptionUtils (private val encryptionSecret: String) {

    private val ALGORITHM = "HmacSHA256"

    fun encrypt(item: String) : String? {

        var encryptedString: String? = null

        val bytesToEncrypt = item.toByteArray(Charsets.UTF_8)
        val mac = Mac.getInstance(ALGORITHM)
        val secretKeySpec = SecretKeySpec(encryptionSecret.toByteArray(Charsets.UTF_8),ALGORITHM)

        mac.init(secretKeySpec)

        try {
            encryptedString = Base64.getEncoder().encodeToString(mac.doFinal(bytesToEncrypt))
        } catch (i: IllegalStateException) {
            return encryptedString
        }

        return encryptedString
    }

}
