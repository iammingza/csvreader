package com.csvReader.csvreader

import com.csvReader.csvreader.Main.Companion.MERCHANT
import com.csvReader.csvreader.Main.Companion.TENOR
import com.csvReader.csvreader.Main.Companion.headerSet
import com.opencsv.CSVReader
import com.opencsv.bean.CsvToBeanBuilder
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import java.io.FileReader
import java.io.StringReader

class Main {
    companion object {
        const val MERCHANT = "Org Id (Optional),Merchant Id,Merchant Name,Limit Merchant,Limit Merchant Group"
        const val TENOR = "Loan Product,Risk Category,Tenor,Int Rate,Eff Rate,Subsidy Rate,Start Date,End Date"
        val headerSet = setOf(MERCHANT, TENOR)
    }
}

fun main(args: Array<String>) {
    val csvReader = CSVReader(FileReader("/Users/nattapon.jee/Downloads/csvreader/src/main/resources/test.csv"))
    csvReader.use {
        var line = it.readNext();

        val detailsHashMap = HashMap<String, String>()
        var pairOfObject = Pair(StringUtils.EMPTY, StringBuilder())

        //read by line to collect group of data to hashmap
        while (line != null) {
            //skip empty line
            if (arrayToString(line) == "") {
                line = it.readNext()
                continue
            }

            //put group of object to hashmap
            pairOfObject = groupingObject(line, pairOfObject, detailsHashMap)

            line = it.readNext()
        }

        //handle last headers put to hashmap
        detailsHashMap[pairOfObject.first] = pairOfObject.second.toString()

        val merchantList = mappingField(detailsHashMap[MERCHANT], Merchant::class.java)
        val tenorList = mappingField(detailsHashMap[TENOR], Tenor::class.java)

        println(merchantList.toString())
        println(tenorList.toString())
    }
}

private fun arrayToString(value: Array<String>): String {
    return ArrayUtils.toString(value).replace("\\{|\\}".toRegex(), "")
}

private fun groupingObject(
    row: Array<String>,
    pairOfObject: Pair<String, StringBuilder>,
    detailsHashMap: HashMap<String, String>
): Pair<String, StringBuilder> {
    // Check if line == header will put the details to hashmap
    if (headerSet.contains(arrayToString(row))) {
        // ignore first header put to hashmap
        if (StringUtils.isNotEmpty(pairOfObject.first)) {
            detailsHashMap[pairOfObject.first] = pairOfObject.second.toString()
        }
        // append new header
        return Pair(arrayToString(row), StringBuilder().append(arrayToString(row)).append("\n"))
    }

    // if line != header will append the details
    return Pair(pairOfObject.first, pairOfObject.second.append(arrayToString(row)).append("\n"))
}

private fun <T> mappingField(details: String?, classType: Class<T>): List<T>? {
    return if (details != null) {
        csvToBean(details, classType)
    } else listOf()
}

private fun <T> csvToBean(value: String, classType: Class<T>): List<T> {
    return CsvToBeanBuilder<T>(CSVReader(StringReader(value))).withType(classType).build().parse()
}