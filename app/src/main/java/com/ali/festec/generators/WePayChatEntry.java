package com.ali.festec.generators;


import com.ali.latte.annitations.PayEntryGenerator;
import com.ali.latte.wechat.template.WPayEntryTemplate;


/**
 * Created by 澄鱼 on 2018/4/23.
 */
@PayEntryGenerator(
        packageName = "com.ali.festec",
        entryTemplate = WPayEntryTemplate.class
)
public interface WePayChatEntry {
}
