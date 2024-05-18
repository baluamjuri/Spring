import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MultiadjusterClient {
	
	@Autowired
	private EmployeeProperties properties;

    @Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
    
	public String getEmployee(String id) {
		HttpHeaders httpHeaders = buildHeaders();
		String url = buildUrl(id);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(httpHeaders);
		log.info("Calling employee service:{}", url);
		log.info("Headers: {}", httpHeaders.toString());
		
    	try {
			ResponseEntity<String> jsonResponse = oAuth2RestTemplate
					.exchange(url, HttpMethod.GET, requestEntity, String.class);
			log.info("Employee API response: %s", jsonResponse);
			return jsonResponse.getBody();
		}catch(DefaultRestTemplateException ex) {
			//Handle here or globally
		}
	}
	
	@Override
    public String saveEmployee(Employee employee) {
	HttpHeaders httpHeaders = buildHeaders();
		String url = properties.getPostUrl();
        HttpEntity<Employee> requestEntity = new HttpEntity<>(Employee, headers);
        ResponseEntity<String> response;
        try {
            log.info("BEFORE POST: {},Request: {}", url, new Gson().toJson(requestEntity));
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.info("AFTER POST: {}, Response: {}", url, response);
        } catch (DefaultRestTemplateException exception) {
            //Handle here or globally
        }
        return response.toString();
    }
	
	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("header1", properties.getAppId());
		headers.set("header2", properties.getClientId());
		return headers;
	}
	
	private String buildUrl(String id) {
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(properties.getUrl()).buildAndExpand(Map.of("id", id));
		return uriComponents.toUriString();
	}


}

========================================================================================

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.ResponseErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class Oauth2Configuration {
	
	@Value("${oauth2.client.accessTokenUri}")
	private String accessTokenUri;
	@Value("${oauth2.client.clientId}")
	private String clientId;
	@Value("${oauth2.client.clientSecret}")
	private String clientSecret;
	@Value("${oauth2.client.grantType}")
	private String grantType;//client_credentials
	
	public OAuth2ProtectedResourceDetails oauthResource() {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setAccessTokenUri(accessTokenUri);
		resourceDetails.setClientId(clientId);
		resourceDetails.setClientSecret(clientSecret);
		resourceDetails.setGrantType(grantType);
		return resourceDetails;
	}
	
	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(ResponseErrorHandler defaultResponseErrorHandler) {
		 OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(
		            oauthResource(), new DefaultOAuth2ClientContext());
		        oAuth2RestTemplate.setInterceptors(
		            Collections.singletonList(new MiddleWareRequestInterceptor()));
		        oAuth2RestTemplate.setErrorHandler(defaultResponseErrorHandler); 
		        return oAuth2RestTemplate;
	}
	
	@Bean
	public ResponseErrorHandler defaultResponseErrorHandler() {
		return new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus.Series series = response.getStatusCode().series();
				return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				log.error("Response Error: {} {} ", response.getStatusCode(), response.getStatusText());
				
				if(HttpStatus.BAD_GATEWAY.equals(response.getStatusCode())||HttpStatus.GATEWAY_TIMEOUT.equals(response.getStatusCode())) {
					throw new DefaultRestTemplateException(response.getStatusCode(), response.getStatusCode().toString());
				}
				
				String statusText = response.getStatusText() == null ? "Unexpected error" : response.getStatusText();
				
				if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
					InputStream body;
					
					try {
						body = response.getBody();
					}catch(IOException ie) {//if there is no body
						throw new DefaultRestTemplateException(response.getStatusCode(), statusText);	
					}
					
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(body))) {
						String errorMessage = reader.lines().collect(Collectors.joining(""));
						throw new DefaultRestTemplateException(response.getStatusCode(), errorMessage);
					}
				} else {
			throw new DefaultRestTemplateException(response.getStatusCode(), statusText);
				}
			}
		};
	}
}

===============================================================================
import org.springframework.http.HttpStatus;

public class DefaultRestTemplateException extends RuntimeException{

	private static final long serialVersionUID = -3939465564348L;

	private HttpStatus errorCode;
    private String message;

	public DefaultRestTemplateException(HttpStatus errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
	public HttpStatus getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
