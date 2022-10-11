package com.asvsdfer.new_master_code_kotlin.logic.model

import com.google.gson.annotations.SerializedName

data class ConfigModel(@SerializedName("COMPANY_NAME") val companyName: String, @SerializedName("IS_SELECT_LOGIN") val isSelectLogin: String,
                       @SerializedName("IS_CODE_LOGIN") val isCodeLogin: String, @SerializedName("APP_MAIL") val appMail: String,
                       @SerializedName("APP_NAME") val appName: String, @SerializedName("IS_ONEBUTTON_LOGIN") val isOneButtonLogin: String,
                       @SerializedName("COMPANY_ADDRESS") val companyAddress: String, @SerializedName("DOMAIN_NAME") val domainName: String,
                       @SerializedName("VIDEOTAPE") val videoTape: String)
