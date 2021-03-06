/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ed.notify.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.ac.ed.notify.TestApplication;
import uk.ac.ed.notify.entity.Notification;
import uk.ac.ed.notify.entity.NotificationUser;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class Office365JsonServiceTest {

    @Autowired 
    Office365JsonService office365JsonService;

    
    @Test
    public void testParseNotificationJson() throws Exception {
    	
    	String notificationJson = "<script type=\"application/ld+json\">{\"@type\":\"Notification\",\"publisherId\":\"euclid\",\"publisherNotificationId\":\"6\",\"publisherKey\":\"004TFE5E177023ABE05642144F00F4CC\",\"topic\":\"Development testing\",\"title\":\"Test test test\",\"body\":\"<h4>Good day</h4>\",\"url\":\"http://www.ed.ac.uk\",\"notificationUsers\":[{\"user\":{\"uun\":\"donald\"}},{\"user\":{\"uun\":\"bambi\"}},{\"user\":{\"uun\":\"gozer\"}}],\"startDate\":\"2016-05-27T08:30\",\"endDate\":\"2016-11-27T09:30\",\"action\":\"insert\"}</script>";
    	
        Notification parsedNotification = office365JsonService.parseNotification(notificationJson);
        
        List<NotificationUser> users = parsedNotification.getNotificationUsers();
        assertThat(users, hasSize(3));
        
        List<String> uuns = new ArrayList<String>();
        for(int i = 0; i < users.size(); i++){
        	uuns.add(users.get(i).getId().getUun());
        }
        
        assertThat(uuns, hasItems("gozer","donald","bambi"));
        
        assertThat(parsedNotification.getPublisherId(), is("euclid"));
        assertThat(parsedNotification.getPublisherNotificationId(), is("6"));
        assertThat(parsedNotification.getPublisherKey(), is("004TFE5E177023ABE05642144F00F4CC"));
        assertThat(parsedNotification.getTopic(), is("Development testing"));
        assertThat(parsedNotification.getTitle(), is("Test test test"));
        assertThat(parsedNotification.getBody(), is("Good day"));
        assertThat(parsedNotification.getTitle(), is("Test test test"));
        assertThat(parsedNotification.getUrl(), is("http://www.ed.ac.uk"));
        assertThat(parsedNotification.getStartDate(), is(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse("2016-05-27T08:30")));
        assertThat(parsedNotification.getEndDate(), is(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse("2016-11-27T09:30")));
       
    }
    
    
    @Test
    public void testParseSingleNotification() throws ParseException {
        String unreadEmailJson = "{\"@odata.context\":\"https://outlook.office365.com/api/v1.0/$metadata#Users('scotapps%40scotapps.onmicrosoft.com')/Folders('inbox')/Messages\",\"value\":[{\"@odata.id\":\"https://outlook.office365.com/api/v1.0/Users('scotapps@scotapps.onmicrosoft.com')/Messages('AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAItUOGAAA=')\",\"@odata.etag\":\"W/\\\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAItlsJ\\\"\",\"Id\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAItUOGAAA=\",\"DateTimeCreated\":\"2015-11-13T17:23:41Z\",\"DateTimeLastModified\":\"2015-11-13T17:23:41Z\",\"ChangeKey\":\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAItlsJ\",\"Categories\":[],\"DateTimeReceived\":\"2015-11-13T17:23:41Z\",\"DateTimeSent\":\"2015-11-13T17:23:39Z\",\"HasAttachments\":false,\"Subject\":\"office 365 notify\",\"Body\":{\"ContentType\":\"HTML\",\"Content\":\"<html>\\r\\n<head>\\r\\n<meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=utf-8\\\">\\r\\n<meta content=\\\"text/html; charset=iso-8859-1\\\">\\r\\n<style type=\\\"text/css\\\" style=\\\"display:none\\\">\\r\\n<!--\\r\\np\\r\\n\\t{margin-top:0;\\r\\n\\tmargin-bottom:0}\\r\\n-->\\r\\n</style>\\r\\n</head>\\r\\n<body dir=\\\"ltr\\\">\\r\\n<div id=\\\"divtagdefaultwrapper\\\" style=\\\"font-size:12pt; color:#000000; background-color:#FFFFFF; font-family:Calibri,Arial,Helvetica,sans-serif\\\">\\r\\n<p><span id=\\\"ms-rterangepaste-start\\\"></span>&lt;script type=&quot;application/ld&#43;json&quot;&gt;<br>\\r\\n{<br>\\r\\n&quot;@type&quot;: &quot;Notification&quot;, &quot;publisherId&quot;: &quot;learn&quot;, &quot;publisherNotificationId&quot;: &quot;112911&quot;, &quot;publisherKey&quot;: &quot;005AFE5E177048ABE05400144F00F4CC&quot;, &quot;topic&quot;: &quot;Learn Assessment&quot;, &quot;title&quot;: &quot;Richard Test&quot;,&quot;body&quot;: &quot;example body7&quot;, &quot;url&quot;: &quot;http://www.ed.ac.uk&quot;, &quot;uun&quot;: &quot;rgood&quot;,\\r\\n &quot;startDate&quot;: &quot;2013-05-15T08:30&quot;, &quot;endDate&quot;: &quot;2016-05-20T08:30&quot;, &quot;action&quot;: &quot;insert&quot;<br>\\r\\n}<br>\\r\\n&lt;/script&gt;<span id=\\\"ms-rterangepaste-end\\\"></span><br>\\r\\n</p>\\r\\n</div>\\r\\n</body>\\r\\n</html>\\r\\n\"},\"BodyPreview\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"learn\\\", \\\"publisherNotificationId\\\": \\\"112911\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"Learn Assessment\\\", \\\"title\\\": \\\"Richard Test\\\",\\\"body\\\": \\\"example body7\\\",\",\"Importance\":\"Normal\",\"ParentFolderId\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAuAAAAAAAQSU6klR_CS7f2u_Zotqz3AQCK45XT6t02QqdGHEQt1wAjAAAAAAEMAAA=\",\"Sender\":{\"EmailAddress\":{\"Name\":\"Jing Pang\",\"Address\":\"scotapps@scotapps.onmicrosoft.com\"}},\"From\":{\"EmailAddress\":{\"Name\":\"Jing Pang\",\"Address\":\"scotapps@scotapps.onmicrosoft.com\"}},\"ToRecipients\":[{\"EmailAddress\":{\"Name\":\"Jing Pang\",\"Address\":\"scotapps@scotapps.onmicrosoft.com\"}}],\"CcRecipients\":[],\"BccRecipients\":[],\"ReplyTo\":[],\"ConversationId\":\"AAQkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAQABNicJfVt1xDvvgl2r7msa8=\",\"IsDeliveryReceiptRequested\":false,\"IsReadReceiptRequested\":false,\"IsRead\":false,\"IsDraft\":false,\"WebLink\":\"https://outlook.office365.com/owa/?ItemID=AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR%2BCS7f2u%2BZotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAItUOGAAA%3D&exvsurl=1&viewmodel=ReadMessageItem\"}]}";

        Hashtable<String, Notification> idAndNotificationPairTable = office365JsonService.parseTableOfNotification(unreadEmailJson);
        
        int expectedTotal = 1;
        int actualTotal = idAndNotificationPairTable.size();
        
        assertEquals(expectedTotal,actualTotal); 
    }
    

    @Test
    public void testParseNotification() throws ParseException {
        String unreadEmailJson = "{\"@odata.context\":\"https://outlook.office365.com/api/v1.0/$metadata#Users('scotapps%40scotapps.onmicrosoft.com')/Folders('inbox')/Messages\",\"value\":[{\"@odata.id\":\"https://outlook.office365.com/api/v1.0/Users('scotapps@scotapps.onmicrosoft.com')/Messages('AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF8AAA=')\",\"@odata.etag\":\"W/\\\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAHN\\\"\",\"Id\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF8AAA=\",\"ChangeKey\":\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAHN\",\"Categories\":[],\"DateTimeCreated\":\"2015-11-02T14:43:49Z\",\"DateTimeLastModified\":\"2015-11-02T14:43:49Z\",\"HasAttachments\":false,\"Subject\":\"FW: notification create 3\",\"Body\":{\"ContentType\":\"Text\",\"Content\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \\\"url\\\": \\\"http://www.ed.ac.uk\\\", \\\"uun\\\": \\\"rgood\\\", \\\"startDate\\\": \\\"2013-05-15T08:30\\\", \\\"endDate\\\": \\\"2013-05-20T08:30\\\", \\\"action\\\": \\\"insert\\\"\\r\\n}\\r\\n</script>\\r\\n\\r\\n-- \\r\\nThe University of Edinburgh is a charitable body, registered in\\r\\nScotland, with registration number SC005336.\\r\\n\\r\\n\"},\"BodyPreview\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \",\"Importance\":\"Normal\",\"ParentFolderId\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAuAAAAAAAQSU6klR_CS7f2u_Zotqz3AQCK45XT6t02QqdGHEQt1wAjAAAAAAEMAAA=\",\"Sender\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"From\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"ToRecipients\":[{\"EmailAddress\":{\"Address\":\"scotapps@scotapps.onmicrosoft.com\",\"Name\":\"Jing Pang\"}}],\"CcRecipients\":[],\"BccRecipients\":[],\"ReplyTo\":[],\"ConversationId\":\"AAQkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAQAMXUR2km1U3Rsz7oOLisO80=\",\"IsDeliveryReceiptRequested\":null,\"IsReadReceiptRequested\":false,\"IsRead\":false,\"IsDraft\":false,\"DateTimeReceived\":\"2015-11-02T14:43:49Z\",\"DateTimeSent\":\"2015-11-02T14:43:04Z\",\"WebLink\":\"https://outlook.office365.com/owa/?ItemID=AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR%2BCS7f2u%2BZotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF8AAA%3D&exvsurl=1&viewmodel=ReadMessageItem\"},{\"@odata.id\":\"https://outlook.office365.com/api/v1.0/Users('scotapps@scotapps.onmicrosoft.com')/Messages('AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF7AAA=')\",\"@odata.etag\":\"W/\\\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAGk\\\"\",\"Id\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF7AAA=\",\"ChangeKey\":\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAGk\",\"Categories\":[],\"DateTimeCreated\":\"2015-10-28T10:28:15Z\",\"DateTimeLastModified\":\"2015-10-28T10:28:15Z\",\"HasAttachments\":false,\"Subject\":\"FW: notification create 3\",\"Body\":{\"ContentType\":\"Text\",\"Content\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \\\"url\\\": \\\"http://www.ed.ac.uk\\\", \\\"uun\\\": \\\"rgood\\\", \\\"startDate\\\": \\\"2013-05-15T08:30\\\", \\\"endDate\\\": \\\"2013-05-20T08:30\\\", \\\"action\\\": \\\"insert\\\"\\r\\n}\\r\\n</script>\\r\\n\\r\\n-- \\r\\nThe University of Edinburgh is a charitable body, registered in\\r\\nScotland, with registration number SC005336.\\r\\n\\r\\n\"},\"BodyPreview\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \",\"Importance\":\"Normal\",\"ParentFolderId\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAuAAAAAAAQSU6klR_CS7f2u_Zotqz3AQCK45XT6t02QqdGHEQt1wAjAAAAAAEMAAA=\",\"Sender\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"From\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"ToRecipients\":[{\"EmailAddress\":{\"Address\":\"scotapps@scotapps.onmicrosoft.com\",\"Name\":\"Jing Pang\"}}],\"CcRecipients\":[],\"BccRecipients\":[],\"ReplyTo\":[],\"ConversationId\":\"AAQkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAQAMXUR2km1U3Rsz7oOLisO80=\",\"IsDeliveryReceiptRequested\":null,\"IsReadReceiptRequested\":false,\"IsRead\":false,\"IsDraft\":false,\"DateTimeReceived\":\"2015-10-28T10:28:15Z\",\"DateTimeSent\":\"2015-10-28T10:27:42Z\",\"WebLink\":\"https://outlook.office365.com/owa/?ItemID=AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR%2BCS7f2u%2BZotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF7AAA%3D&exvsurl=1&viewmodel=ReadMessageItem\"},{\"@odata.id\":\"https://outlook.office365.com/api/v1.0/Users('scotapps@scotapps.onmicrosoft.com')/Messages('AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF6AAA=')\",\"@odata.etag\":\"W/\\\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAGj\\\"\",\"Id\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF6AAA=\",\"ChangeKey\":\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAGj\",\"Categories\":[],\"DateTimeCreated\":\"2015-10-27T18:10:18Z\",\"DateTimeLastModified\":\"2015-10-27T18:10:18Z\",\"HasAttachments\":false,\"Subject\":\"FW: notification create 2\",\"Body\":{\"ContentType\":\"Text\",\"Content\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \\\"url\\\": \\\"http://www.ed.ac.uk\\\", \\\"uun\\\": \\\"rgood\\\", \\\"startDate\\\": \\\"2013-05-15T08:30\\\", \\\"endDate\\\": \\\"2013-05-20T08:30\\\", \\\"action\\\": \\\"insert\\\"\\r\\n}\\r\\n</script>\\r\\n\\r\\n-- \\r\\nThe University of Edinburgh is a charitable body, registered in\\r\\nScotland, with registration number SC005336.\\r\\n\\r\\n\"},\"BodyPreview\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \",\"Importance\":\"Normal\",\"ParentFolderId\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAuAAAAAAAQSU6klR_CS7f2u_Zotqz3AQCK45XT6t02QqdGHEQt1wAjAAAAAAEMAAA=\",\"Sender\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"From\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"ToRecipients\":[{\"EmailAddress\":{\"Address\":\"scotapps@scotapps.onmicrosoft.com\",\"Name\":\"Jing Pang\"}}],\"CcRecipients\":[],\"BccRecipients\":[],\"ReplyTo\":[],\"ConversationId\":\"AAQkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAQAGkxmT8Ec0qCiv84W5uOTpg=\",\"IsDeliveryReceiptRequested\":null,\"IsReadReceiptRequested\":false,\"IsRead\":false,\"IsDraft\":false,\"DateTimeReceived\":\"2015-10-27T18:10:18Z\",\"DateTimeSent\":\"2015-10-27T18:10:15Z\",\"WebLink\":\"https://outlook.office365.com/owa/?ItemID=AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR%2BCS7f2u%2BZotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF6AAA%3D&exvsurl=1&viewmodel=ReadMessageItem\"},{\"@odata.id\":\"https://outlook.office365.com/api/v1.0/Users('scotapps@scotapps.onmicrosoft.com')/Messages('AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAFvAAA=')\",\"@odata.etag\":\"W/\\\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAGW\\\"\",\"Id\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAFvAAA=\",\"ChangeKey\":\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAGW\",\"Categories\":[],\"DateTimeCreated\":\"2015-10-27T16:57:39Z\",\"DateTimeLastModified\":\"2015-10-27T16:57:39Z\",\"HasAttachments\":false,\"Subject\":\"notification create\",\"Body\":{\"ContentType\":\"Text\",\"Content\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \\\"url\\\": \\\"http://www.ed.ac.uk\\\", \\\"uun\\\": \\\"rgood\\\", \\\"startDate\\\": \\\"2013-05-15T08:30\\\", \\\"endDate\\\": \\\"2013-05-20T08:30\\\", \\\"action\\\": \\\"insert\\\"\\r\\n}\\r\\n</script>\\r\\n\\r\\n-- \\r\\nThe University of Edinburgh is a charitable body, registered in\\r\\nScotland, with registration number SC005336.\\r\\n\\r\\n\"},\"BodyPreview\":\"<script type=\\\"application/ld+json\\\">\\r\\n{\\r\\n\\\"@type\\\": \\\"Notification\\\", \\\"publisherId\\\": \\\"12345\\\", \\\"publisherNotificationId\\\": \\\"12345\\\", \\\"publisherKey\\\": \\\"005AFE5E177048ABE05400144F00F4CC\\\", \\\"topic\\\": \\\"example category\\\", \\\"title\\\": \\\"example title\\\",\\\"body\\\": \\\"example body\\\", \",\"Importance\":\"Normal\",\"ParentFolderId\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAuAAAAAAAQSU6klR_CS7f2u_Zotqz3AQCK45XT6t02QqdGHEQt1wAjAAAAAAEMAAA=\",\"Sender\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"From\":{\"EmailAddress\":{\"Address\":\"Hui.Sun@ed.ac.uk\",\"Name\":\"SUN Michael\"}},\"ToRecipients\":[{\"EmailAddress\":{\"Address\":\"scotapps@scotapps.onmicrosoft.com\",\"Name\":\"Jing Pang\"}}],\"CcRecipients\":[],\"BccRecipients\":[],\"ReplyTo\":[],\"ConversationId\":\"AAQkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQAQALdlbgbqBUVBs51nLUMwiWg=\",\"IsDeliveryReceiptRequested\":null,\"IsReadReceiptRequested\":false,\"IsRead\":false,\"IsDraft\":false,\"DateTimeReceived\":\"2015-10-27T16:57:39Z\",\"DateTimeSent\":\"2015-10-27T16:57:25Z\",\"WebLink\":\"https://outlook.office365.com/owa/?ItemID=AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR%2BCS7f2u%2BZotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAFvAAA%3D&exvsurl=1&viewmodel=ReadMessageItem\"}]}";
        Hashtable<String, Notification> idAndNotificationPairTable = office365JsonService.parseTableOfNotification(unreadEmailJson);
        
        int expectedTotal = 4;
        int actualTotal = idAndNotificationPairTable.size();

        assertEquals(expectedTotal,actualTotal); 
    }

    
    @Test
    public void testParseOffice365NewEmailCallbackEmailId() throws ParseException {
        String newEmailCallbackJson = "{\"value\":[{\"@odata.type\":\"#Microsoft.OutlookServices.Notification\",\"Id\":null,\"subscriptionId\":\"MjQwMzczMUYtODUwQi00QUYyLThEQzMtMDJEQzY0ODk0RjVFXzQ1MTU4OEJFLTczQzQtNDBFOS1BN0E1LUYyOTdENkEzM0NBMQ==\",\"subscriptionExpirationDateTime\":\"2015-11-02T18:40:00Z\",\"sequenceNumber\":1,\"changeType\":\"Created\",\"resource\":\"https://outlook.office.com/api/beta/Users('scotapps@scotapps.onmicrosoft.com')/Messages('AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF8AAA=')\",\"ResourceData\":{\"@odata.type\":\"#Microsoft.OutlookServices.Message\",\"@odata.id\":\"https://outlook.office.com/api/beta/Users('scotapps@scotapps.onmicrosoft.com')/Messages('AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF8AAA=')\",\"@odata.etag\":\"W/\\\"CQAAABYAAACK45XT6t02QqdGHEQt1wAjAAAAAAHN\\\"\",\"Id\":\"AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF8AAA=\"}}]}";
        
        String expected = "AAMkADQ1MTU4OGJlLTczYzQtNDBlOS1hN2E1LWYyOTdkNmEzM2NhMQBGAAAAAAAQSU6klR_CS7f2u_Zotqz3BwCK45XT6t02QqdGHEQt1wAjAAAAAAEMAACK45XT6t02QqdGHEQt1wAjAAAAAAF8AAA=";
        String actual = office365JsonService.parseOffice365NewEmailCallbackEmailId(newEmailCallbackJson);
        
        assertEquals(expected,actual);
    }


    @Test
    public void testParseOffice365NewSubscriptionCallbackSubscriptionId() throws ParseException {
        String newSubscriptionCallbackJson = "{\"@odata.type\":\"#Microsoft.OutlookServices.Notification\",\"Id\":\"MjQwMzczMUYtODUwQi00QUYyLThEQzMtMDJEQzY0ODk0RjVFXzQ1MTU4OEJFLTczQzQtNDBFOS1BN0E1LUYyOTdENkEzM0NBMQ==\",\"SubscriptionId\":\"MjQwMzczMUYtODUwQi00QUYyLThEQzMtMDJEQzY0ODk0RjVFXzQ1MTU4OEJFLTczQzQtNDBFOS1BN0E1LUYyOTdENkEzM0NBMQ==\",\"SubscriptionExpirationDateTime\":\"2015-11-02T18:40:00Z\",\"sequenceNumber\":0,\"changeType\":\"Acknowledgment\"}";
        
        String expected = "MjQwMzczMUYtODUwQi00QUYyLThEQzMtMDJEQzY0ODk0RjVFXzQ1MTU4OEJFLTczQzQtNDBFOS1BN0E1LUYyOTdENkEzM0NBMQ==";
        String actual = office365JsonService.parseOffice365NewSubscriptionCallbackSubscriptionId(newSubscriptionCallbackJson).getSubscriptionId();
        
        assertEquals(expected,actual);
    }  
  
}
