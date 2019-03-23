package com.csye6225.spring2019.util;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

@Log4j2
public class SNSUtil {
    private static AmazonSNS snsClient;

    static {
//        snsClient = AmazonSNSClient.builder().withCredentials(new ProfileCredentialsProvider()).build();

        snsClient = AmazonSNSClient.builder().withCredentials(new InstanceProfileCredentialsProvider(false)).build();
    }

    /**
     * publish notification
     * @param topicName
     * @param message
     */
    public static boolean publishNotification(String topicName,String message){
        if(Strings.isEmpty(topicName) || Strings.isEmpty(message)) {
            log.warn("No topic name or message found ");
            return false;
        }
        try {
            String topicArn = findTopicArnByName(topicName);
            PublishRequest publishRequest = new PublishRequest(topicArn, message);
            PublishResult publishResponse = snsClient.publish(publishRequest);
            log.info("publish a notification : "+publishResponse.getMessageId());
        }catch (Exception e){
            log.error("Error occurred when publish a notification ",e);
            return false;
        }
        return true;
    }


    private static String findTopicArnByName(String topicName){

        CreateTopicResult createRes = snsClient.createTopic(topicName);
        if(createRes == null ){
            log.error("Unexpected error when creating topic with name :" + topicName);
            return "";
        }
        return createRes.getTopicArn();

    }



}
