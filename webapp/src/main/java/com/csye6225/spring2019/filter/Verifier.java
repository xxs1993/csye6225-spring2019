package com.csye6225.spring2019.filter;

import com.csye6225.spring2019.entity.Account;
import com.google.common.base.Splitter;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.List;

@Log4j2
public class Verifier {
    public static Account isVerified(String auth) throws IOException {
        if(StringUtils.isEmpty(auth)) return null;
        String basic = "Basic";
        if(!auth.contains(basic)) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        String accpString = new String(decoder.decodeBuffer(auth.substring(basic.length()).trim()));
        List<String> list = Splitter.on(":").trimResults().splitToList(accpString);
        if(list.size() !=2){
            return null;
        }
        Account account = new Account();
        account.setEmailAddress(list.get(0));
        account.setPassword(list.get(1));
        return account;
    }

}
