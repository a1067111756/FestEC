package com.ali.festec.generators;

import com.ali.latte.annitations.EntryGenerator;
import com.ali.latte.wechat.template.WXEntryTemplate;

/**
 * Created by 澄鱼 on 2018/4/23.
 */

@EntryGenerator(
        packageName = "com.ali.festec",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
