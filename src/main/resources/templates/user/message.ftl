<#--导入公共部分-->
<#include "/inc/layout.ftl" />

<#--使用-->
<@layout "消息中心" >
    <div class="layui-container fly-marginTop fly-user-main">
        <#--左侧导航栏-->
        <@usercenterleft  level=3 ></@usercenterleft>

        <div class="site-tree-mobile layui-hide">
            <i class="layui-icon">&#xe602;</i>
        </div>
        <div class="site-mobile-shade"></div>

        <div class="site-tree-mobile layui-hide">
            <i class="layui-icon">&#xe602;</i>
        </div>
        <div class="site-mobile-shade"></div>


        <div class="fly-panel fly-panel-user" pad20>
            <div class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
                <button class="layui-btn layui-btn-danger" id="LAY_delallmsg">清空全部消息</button>
                <div id="LAY_minemsg" style="margin-top: 10px;">
                    <!--<div class="fly-none">您暂时没有最新消息</div>-->
                    <ul class="mine-msg">
                        <#list messages.records as mess>
                            <li data-id="${mess.id}">
                                <blockquote class="layui-elem-quote">
                                    <#if mess.type == 0>
                                        系统消息：${mess.content}
                                    </#if>
                                    <#if mess.type == 1>
                                        ${mess.fromUserName} 评论了你的文章 < <a
                                            href="/post/${mess.postId}">${mess.postTitle}</a>>，内容是 (${mess.content})
                                    </#if>
                                    <#if mess.type == 2>
                                        ${mess.fromUserName} 回复了你的评论 (${mess.content})，文章是 <a
                                            href="/post/${mess.postId}">${mess.postTitle}</a>
                                    </#if>

                                </blockquote>
                                <p><span>${timeAgo(mess.created)}</span><a href="javascript:;"
                                                                           class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a>
                                </p>
                            </li>
                        </#list>
                        <#--分页-->
                        <@paging messages></@paging>
                    </ul>
                </div>
            </div>
        </div>

    </div>

    <script>
        layui.cache.page = 'user';
    </script>
</@layout>