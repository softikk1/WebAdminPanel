package dev.softikk.webadminpanel.api

import dev.softikk.webadminpanel.dto.sites.CreateSiteReceiveDto
import dev.softikk.webadminpanel.dto.sites.GetSiteRespondDto
import dev.softikk.webadminpanel.dto.sites.GetSitesRespondDto
import dev.softikk.webadminpanel.dto.sites.UpdateSiteReceiveDto
import dev.softikk.webadminpanel.models.ElementModel
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
        name: String, host: String, description: String, elements: List<ElementModel>
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
        siteId: Uuid, name: String, host: String, description: String, elements: List<ElementModel>
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

    suspend fun deleteSchema(
        siteId: Uuid, schemaId: Uuid, onSuccess: () -> Unit
    ) {
        val receive = client.delete("/sites/$siteId/schemas/$schemaId")
        if (receive.status == HttpStatusCode.OK) {
            onSuccess()
        }
    }
}