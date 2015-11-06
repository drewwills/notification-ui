package uk.ac.ed.notify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.*;
import uk.ac.ed.notify.entity.JsonNotification;

import javax.servlet.ServletException;
import java.security.Principal;

/**
 * Created by rgood on 28/10/2015.
 */
@RestController
public class NotificationController {


    @Value("${spring.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${spring.oauth2.client.accessTokenUri}")
    private String tokenUrl;

    @Value("${spring.oauth2.client.clientId}")
    private String clientId;

    @Value("${zuul.routes.resource.url}")
    private String notificationMsUrl;

    public NotificationController() {
        restTemplate = new OAuth2RestTemplate(resource());
    }

    private OAuth2RestTemplate restTemplate;

    protected OAuth2ProtectedResourceDetails resource() {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri("https://dev.oauth.ws-apps.is.ed.ac.uk:443/oauth/token");
        resource.setClientSecret("s1llycrash3s");
        resource.setClientId("notification-ui");
        return resource;
    }


    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/notification/{notification-id}", method = RequestMethod.GET)
    public
    @ResponseBody
    JsonNotification getNotification(@PathVariable("notification-id") String notificationId) throws ServletException {
        ResponseEntity<JsonNotification> response = restTemplate.getForEntity(notificationMsUrl + "/" + notificationId, JsonNotification.class);

        return response.getBody();
    }

    @RequestMapping(value = "/notification/publisher/{publisher-id}", method = RequestMethod.GET)
    public
    @ResponseBody
    JsonNotification[] getPublisherNotifications(@PathVariable("publisher-id") String publisherId) throws ServletException {

        ResponseEntity<JsonNotification[]> response = restTemplate.getForEntity(notificationMsUrl + "/publisher/" + publisherId, JsonNotification[].class);
        return response.getBody();
    }


    @RequestMapping(value="/notification/", method=RequestMethod.POST)
    public @ResponseBody
    JsonNotification setNotification(@RequestBody String notification) throws ServletException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request= new HttpEntity(notification, headers);
        ResponseEntity<JsonNotification> response = restTemplate.exchange(notificationMsUrl + "/", HttpMethod.POST, request, JsonNotification.class);
        return response.getBody();

    }

    @RequestMapping(value="/notification/{notification-id}",method=RequestMethod.PUT)
    public void updateNotification(@PathVariable("notification-id") String notificationId, @RequestBody String notification) throws ServletException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request= new HttpEntity(notification, headers);
        ResponseEntity<JsonNotification> response = restTemplate.exchange(notificationMsUrl + "/"+notificationId, HttpMethod.PUT, request, JsonNotification.class);
    }

    @RequestMapping(value="/notification/{notification-id}",method=RequestMethod.DELETE)
    public void deleteNotification(@PathVariable("notification-id") String notificationId) throws ServletException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request= new HttpEntity("", headers);
        ResponseEntity<String> response = restTemplate.exchange(notificationMsUrl + "/"+notificationId, HttpMethod.DELETE, request, String.class);
    }

}
