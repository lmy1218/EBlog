<#--导入公共部分-->
<#include "/inc/layout.ftl" />

<#--使用-->
<@layout "搜索 - ${q}" >
    <#--头部菜单-->
    <#include "/inc/header-menu.ftl" />

    <#--中间-->
    <div class="layui-container">
        <div class="layui-row layui-col-space15">
            <#--左边-->
            <div class="layui-col-md8">
                <div class="fly-panel">
                    <div class="fly-panel-title fly-filter">
                        <a>您正在搜索关键字 “ ${q} ” - 共有${pageData.total}条记录</a>
                        <a href="#signin" class="layui-hide-sm layui-show-xs-block fly-right" id="LAY_goSignin" style="color: #FF5722;">去签到</a>
                    </div>
                    <ul class="fly-list">
                        <#list pageData.records as post >
                            <@plisting post></@plisting>
                        </#list>
                    </ul>
                    <#--分页-->
                    <@paging pageData></@paging>
                </div>
            </div>

            <#--右边-->
            <#include "/inc/right.ftl" />
        </div>
    </div>
</@layout>

