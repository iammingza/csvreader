package com.csvReader.csvreader

import com.opencsv.bean.CsvBindByName

data class Tenor(
    @CsvBindByName(column = "Loan Product")
    val loanProduct: String? = null,
    @CsvBindByName(column = "Risk Category")
    val riskCategory: String? = null,
    @CsvBindByName(column = "Tenor")
    val tenor: String? = null,
    @CsvBindByName(column = "Int Rate")
    val intRate: String? = null,
    @CsvBindByName(column = "Eff Rate")
    val effRate: String? = null,
    @CsvBindByName(column = "Subsidy Rate")
    val subsidyRate: String? = null,
    @CsvBindByName(column = "Start Date")
    val startDate: String? = null,
    @CsvBindByName(column = "End Date")
    val endDate: String? = null
)