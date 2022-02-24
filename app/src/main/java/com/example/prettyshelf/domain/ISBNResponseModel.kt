package com.example.prettyshelf.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ISBNResponse(
    val publishers: List<String>?,
    @Json(name = "number_of_pages")
    val numberOfPages: Long?,
    val weight: String?,
    @Json(name = "physical_format")
    val physicalFormat: String?,
    val key: String?,
    val authors: List<Type>?,
    val subjects: List<String>?,
    val languages: List<Type>?,
    val title: String,
    val identifiers: Identifiers?,
    @Json(name = "isbn_13")
    val isbn13: List<String>?,
    @Json(name = "isbn_10")
    val isbn10: List<String>?,
    @Json(name = "publish_date")
    val publishDate: String?,
    @Json(name = "oclc_numbers")
    val oclcNumbers: List<String>?,
    val works: List<Type>?,
    val type: Type?,
    @Json(name = "physical_dimensions")
    val physicalDimensions: String?,
    val covers: List<Long>?,
    val ocaid: String?,
    val lccn: List<String>?,
    @Json(name = "lc_classifications")
    val lcClassifications: List<String>?,
    @Json(name = "source_records")
    val sourceRecords: List<String>?,
    @Json(name = "latest_revision")
    val latestRevision: Long?,
    val revision: Long?,
)

@JsonClass(generateAdapter = true)
data class Type(
    val key: String
)

@JsonClass(generateAdapter = true)
data class Identifiers(
    val librarything: List<String>,
    val goodreads: List<String>
)
