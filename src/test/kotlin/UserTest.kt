import com.web0zz.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class UserRouteTests {
    @Test
    fun testGetUser() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/user/123").apply {
                assertEquals(
                    """No customer with id 123""",
                    response.content
                )
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }
}