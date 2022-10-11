package com.asvsdfer.new_master_code_kotlin.logic.model

data class LoginModel(val phone: String, val device: String, val mobileType: Int, val state: Int,
                      val crtTime: String, val updTime: String, val ip: String, val userLevel: Int)
