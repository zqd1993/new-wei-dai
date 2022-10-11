package com.asvsdfer.new_master_code_kotlin.logic.model

data class ProductModel(val id: Long, val productName: String, val amountType: Int, val productState: Int, val productSort: Int,
                        val productPrice: Int, val minAmount: Int, val maxAmount: Int, val passingRate: Int, val des: String,
                        val tag: String, val productLogo: String, val url: String, val crtTime: String, val upTime: String, val updTime: String)
