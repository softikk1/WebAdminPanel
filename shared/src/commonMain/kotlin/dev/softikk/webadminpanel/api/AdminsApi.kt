package dev.softikk.webadminpanel.api

import dev.softikk.webadminpanel.datastore.AuthDataStore
import dev.softikk.webadminpanel.dto.admins.CreateAdminReceiveDto
import dev.softikk.webadminpanel.dto.admins.GetAdminRespondDto
import dev.softikk.webadminpanel.dto.admins.GetAdminsRespondDto
import dev.softikk.webadminpanel.dto.admins.UpdateAdminReceiveDto
import dev.softikk.webadminpanel.models.AdminModel
import dev.softikk.webadminpanel.models.AuthDataStoreModel
import dev.softikk.webadminpanel.models.SiteModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlin.uuid.Uuid

class AdminsApi(private val client: HttpClient, private val authDataStore: AuthDataStore) {
    suspend fun createAdmin(email: String, name: String, password: String, sites: List<SiteModel>) {
        client.post("/admin") {
            contentType(ContentType.Application.Json)
            setBody<CreateAdminReceiveDto>(
                CreateAdminReceiveDto(
                    email = email, name = name, password = password, sites = sites
                )
            )
        }
    }

    suspend fun getAdmins(): List<AdminModel> {
        val receive = client.get("/admins")
        return receive.body<GetAdminsRespondDto>().admins
    }

    suspend fun getAdmin(adminId: Uuid): AdminModel {
        val receive = client.get("/admins/$adminId")
        return receive.body<GetAdminRespondDto>().admin
    }

    suspend fun updateAdmin(
        adminId: Uuid, email: String, name: String, password: String, sites: List<SiteModel>
    ) {
        val receive = client.put("/admins/$adminId") {
            contentType(ContentType.Application.Json)
            setBody<UpdateAdminReceiveDto>(
                UpdateAdminReceiveDto(
                    name = name, email = email, password = password, sites = sites
                )
            )
        }
        if (receive.status == HttpStatusCode.OK) {
            println("До обновления")
            println(email)
            authDataStore.setAuthModel(AuthDataStoreModel(
                email = email,
                password = password
            ))
            println("Обновилось")
        }
    }
}