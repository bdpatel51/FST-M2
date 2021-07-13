package API_Project;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class restAssuredProject 
{
	
	RequestSpecification requestSpec;	
	ResponseSpecification responseSpec;
	String sshKey;
	int sshKeyId;
	
	@BeforeClass
	public void beforeClass()
	{
		//Create request specification
		requestSpec = new RequestSpecBuilder()
				//Set content type
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token ghp_NDi54Pig50987875tghmk2xqvR1ITO7T")
				//Set base URL
				.setBaseUri("https://api.github.com")
				//Build request specification
				.build();		
		
		sshKey= "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAtf55768tyfdfikhiU5EWIoShklrBZ3aUBv4O bdpatel51@gmail.com";
	}
	@Test (priority= 1)
	public void addSSHKeys()
	{
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
		Response response = given().spec(requestSpec)     
				            .body(reqBody)                
			               	.when().post("/user/keys"); 
		
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		sshKeyId = response.then().extract().path("id");
		response.then().statusCode(201);		
	}
	
	@Test (priority= 2)
	public void getSSHKeys()
	{
		Response response = given().spec(requestSpec)           
				.when().get("/user/keys");                      
	
		String resBody= response.getBody().asPrettyString();
		System.out.println(resBody);
		response.then().statusCode(200);
	}
	
	@Test(priority= 3)
	public void deleteSSHKeys()
	{
		Response response = given().spec(requestSpec)                                   
				.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}");    
		
		String resBody= response.getBody().asPrettyString();
		System.out.println(resBody);
		response.then().statusCode(204);
	}
}