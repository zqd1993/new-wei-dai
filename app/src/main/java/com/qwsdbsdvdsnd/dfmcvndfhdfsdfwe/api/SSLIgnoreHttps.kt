package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class SSLIgnoreHttps {
    companion object {
        @JvmStatic
        fun getSSLContext(): SSLContext? {
            val ssLContext: SSLContext
            val trustManagers: Array<TrustManager>
            val x509TrustManager: X509TrustManager
            try {
                x509TrustManager = object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(arg0: Array<X509Certificate>, arg1: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(arg0: Array<X509Certificate>, arg1: String) {
                    }
                }
                trustManagers = arrayOf(x509TrustManager) //生成trustmanager数组
                ssLContext =
                    SSLContext.getInstance("TLS") //得到sslcontext实例。SSL TSL 是一种https使用的安全传输协议
                ssLContext.init(
                    null,
                    trustManagers,
                    SecureRandom()
                ) //初始化sslcontext 信任所有证书 （官方不推荐使用）
                return ssLContext
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }
            return null
        }

        //跳过Hostname，直接返回true
        var doNotVerifier =
            HostnameVerifier { hostname, session -> true }
    }
}