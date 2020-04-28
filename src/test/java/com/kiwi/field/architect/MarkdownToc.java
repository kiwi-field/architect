package com.kiwi.field.architect;

import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;
import org.junit.jupiter.api.Test;

/**
 * @Description MarkdownToc 自动生成目录工具
 * @Date 2020/4/28 16:50
 * @Author dengxiaoyu
 */
public class MarkdownToc {

    /**
     * 为readme文件生成目录
     */
    @Test
    public void commonInterfaceTest() {
        AtxMarkdownToc.newInstance().genTocFile("README.md");
    }
}
