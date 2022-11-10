package com.csvReader.csvreader

import com.opencsv.bean.CsvBindByName

data class Merchant(
    @CsvBindByName(column = "Org Id (Optional)")
    val orgId: String? = null,
    @CsvBindByName(column = "Merchant Id")
    val merchantId: String? = null,
    @CsvBindByName(column = "Merchant Name")
    val merchantName: String? = null,
    @CsvBindByName(column = "Limit Merchant")
    val limitMerchant: String? = null,
    @CsvBindByName(column = "Limit Merchant Group")
    val limitMerchantGroup: String? = null,
)