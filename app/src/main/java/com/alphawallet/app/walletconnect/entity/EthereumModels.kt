package com.alphawallet.app.walletconnect.entity

import androidx.annotation.Keep

//TODO: Integrate the EthereumMessage here

@Keep
data class WCEthereumSignMessage(
        val raw: List<String>,
        val type: WCSignType
) {
    enum class WCSignType {
        MESSAGE, PERSONAL_MESSAGE, TYPED_MESSAGE
    }

    val data
        get() = when (type) {
            WCSignType.MESSAGE -> raw[1]
            WCSignType.TYPED_MESSAGE -> raw[1]
            WCSignType.PERSONAL_MESSAGE -> raw[0]
        }
}

@Keep
data class WCEthereumTransaction(
        val from: String,
        val to: String?,
        val nonce: String?,
        val gasPrice: String?,
        val gasLimit: String?,
        val value: String?,
        val data: String
)