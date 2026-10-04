package dev.softikk.webadminpanel.api

import dev.softikk.webadminpanel.api.dto.sites.CreateSiteReceiveDto
import dev.softikk.webadminpanel.api.dto.sites.GetSiteRespondDto
import dev.softikk.webadminpanel.api.dto.sites.GetSitesRespondDto
import dev.softikk.webadminpanel.api.dto.sites.SearchSitesRespondDto
import dev.softikk.webadminpanel.api.dto.sites.UpdateSiteReceiveDto
import dev.softikk.webadminpanel.models.SchemaModel
import dev.softikk.webadminpanel.models.SiteModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlin.uuid.Uuid

class SitesApi(private val client: HttpClient) {
    // Эта функция для удаленного сайта клиента
    suspend fun getSiteByHost(host: String): SiteModel {
        val receive = client.get("/site") {
            contentType(ContentType.Application.Json)
            parameter("host", host)
        }
        return receive.body<GetSiteRespondDto>().site
    }

    suspend fun createSite(
        name: String, host: String, description: String, elements: List<SchemaModel>
    ) {
        client.post("/site") {
            contentType(ContentType.Application.Json)
            setBody<CreateSiteReceiveDto>(
                CreateSiteReceiveDto(
                    name = name, host = host, description = description, elements = elements
                )
            )
        }
    }

    suspend fun getSites(): List<SiteModel> {
        val receive = client.get("/sites")
        return when (receive.status) {
            HttpStatusCode.OK -> receive.body<GetSitesRespondDto>().sites
            else -> {
                emptyList()
            }
        }
    }

    suspend fun getSite(siteId: Uuid): SiteModel {
        val receive = client.get("/sites/$siteId")
        return receive.body<GetSiteRespondDto>().site
    }

    suspend fun updateSite(
        siteId: Uuid, name: String, host: String, description: String, elements: List<SchemaModel>
    ) {
        client.put("/sites/$siteId") {
            contentType(ContentType.Application.Json)
            setBody(
                UpdateSiteReceiveDto(
                    siteName = name, host = host, description = description, elements = elements
                )
            )
        }
    }

    suspend fun deleteSite(
        siteId: Uuid
    ) {
        client.delete("/sites/$siteId")
    }

    suspend fun searchSites(search: String): List<SiteModel> {
        return client.post("/sites/search") {
            setBody(search)
        }.body<SearchSitesRespondDto>().sites
    }
}