package cla.poc.bob.pageflow;

import cla.poc.bob.pageflow.builder.PageFlowBuilder;

public class Example {
    public PageFlowBuilder getBuilder() {
        return new PageFlowBuilder()
                .when_draw("start way").zh_CN("打开方式")
                    .in_tool_bar_show_icon("editor")
                    .can_connect_to("request").only_once().by_the("nothing")
                    .can_connect_to("page").only_once().by_the("shortcut")
                    .when_display().as_a_block()
                        .draw_it_by("start way")
                        .title_by("name")
                        .brief_by("comments")
                        .image_by("preview()")
                    .when_selected().show_properties()
                        .string("name")
                        .string("comments")
                .when_draw("request").zh_CN("请求")
                    .in_tool_bar_show_icon("request")
                    .can_connect_to("page").many_times().by_the("branch")
                    .can_connect_to("event").many_times().by_the("invoke type")
                    .when_display().as_a_block()
                        .draw_it_by("request node")
                        .title_by("signature()")
                    .when_selected().show_properties()
                        .string("name")
                        .string("comments")
                        .list_of_param()
                .when_draw("branch").zh_CN("分支")
                    .when_display().as_a_link()
                        .draw_it_by("link")
                        .title_by("name")
                    .when_selected().show_properties()
                        .string("name")
                        .string("comments")
                .when_draw("invoke type").zh_CN("调用方式")
                    .when_display().as_a_link()
                    .draw_it_by("link")
                    .title_by("name")
                    .when_selected().show_properties()
                        .string("name").select_from("同步调用","同步触发","同步预约","延迟调用","延迟触发","延迟预约")
                        .string("comments")
                ;
    }
}
