package br.com.homefasion_api_test_restassure.resources;

import br.com.homefasion_api_test_restassure.categories.NegativeTest;
import br.com.homefasion_api_test_restassure.categories.PositiveTest;
import br.com.homefasion_api_test_restassure.categories.SmokeTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static br.com.homefasion_api_test_restassure.conf.ConfiguracaoPrincipal.*;
import static br.com.homefasion_api_test_restassure.shared.ClienteEndPoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;


public class ClienteTest {

    @Test
    @Category({PositiveTest.class})
    public void deveVerificarSeClienteNoAr(){
        given()
                .log().all()
                .auth().basic(USUARIO_TESTE_LOGIN, SENHA_TESTE_LOGIN)
                .when()
                .request(Method.GET, GET_CLIENTE_NO_AR)
                .then()
                .statusCode(200)
                .body(is("Servidor no ar"));
    }

    @Test
    @Category({NegativeTest.class})
    public void naoDeveAcessarSemAutenticacao(){
        Response response = RestAssured.request(Method.GET, GET_CLIENTE_NO_AR);

        assertEquals(401, response.getStatusCode());
    }

    @Test
    @Category({PositiveTest.class, SmokeTest.class})
    public void deveListarTodosOsClientes(){
        given()
                .log().all()
                .auth().basic(USUARIO_TESTE_LOGIN, SENHA_TESTE_LOGIN)
                .when()
                .request(Method.GET, GET_CLIENTE)
                .then()
                .statusCode(200)
                .body("content.id", contains(43, 44, 45, 46, 47, 48, 49, 50, 51, 52));
    }

    @Test
    @Category({PositiveTest.class, SmokeTest.class})
    public void deveListarClienteEspecifico(){
        given()
                .log().all()
                .auth().basic(USUARIO_TESTE_LOGIN, SENHA_TESTE_LOGIN)
                .when()
                .request(Method.GET, GET_CLIENTE_ESPECIFICO)
                .then()
                .statusCode(200)
                .body("id", is(241))
                .body("nome", is("JPA Teste 1"))
                .body("dataNascimento", is("15/09/2019"))
                .body("cpf", is("07725791485"))
                .body("telefone1", is(12345678));
    }

    @Test
    @Category({PositiveTest.class})
    public void deveListarClientesPorUsuario(){
        given()
                .log().all()
                .auth().basic(USUARIO_TESTE_LOGIN, SENHA_TESTE_LOGIN)
                .when()
                .request(Method.GET, GET_CLIENTES_POR_USUARIO)
                .then()
                .statusCode(200)
                .body("nome[1]", is("DRA. ISIS NOGUEIRA"));
    }

    @Test
    @Category({PositiveTest.class})
    public void deveListarClientesPorNomeUsuario(){
        given()
                .log().all()
                .auth().basic(USUARIO_TESTE_LOGIN, SENHA_TESTE_LOGIN)
                .when()
                .request(Method.GET, GET_CLIENTES_POR_NOME_USUARIO)
                .then()
                .statusCode(200)
                .body("$", hasSize(1));
    }

}
