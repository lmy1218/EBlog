<#--导入公共部分-->
<#include "/inc/layout.ftl" />

<#--使用-->
<@layout "首页" >

<div class="fly-home fly-panel" style="background-image: url();">
  <img src="${user.avatar}" alt="${user.usernmae}">
  <i class="iconfont icon-renzheng" title="Fly社区认证"></i>
  <h1>
    ${user.usernmae}
    <#if user.gender == 0>
      <i class="iconfont icon-nan"></i>
    <#else>
      <i class="iconfont icon-nv"></i>
    </#if>
    <i class="layui-badge fly-badge-vip">VIP3</i>
  </h1>

  <p class="fly-home-info">
    <i class="iconfont icon-kiss" title="飞吻"></i><span style="color: #FF7200;">I Love You!</span>
    <i class="iconfont icon-shijian"></i><span>${user.created?string("yyyy-MM-dd")} 加入</span>
    <i class="iconfont icon-chengshi"></i><span>${user.city!"来自地球"}</span>
  </p>

  <p class="fly-home-sign">（${user.sign!"这个人好像什么都没有留下！"}）</p>

  <div class="fly-sns" data-user="">
    <a href="javascript:;" class="layui-btn layui-btn-primary fly-imActive" data-type="addFriend">加为好友</a>
    <a href="javascript:;" class="layui-btn layui-btn-normal fly-imActive" data-type="chat">发起会话</a>
  </div>

</div>

<div class="layui-container">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md6 fly-home-jie">
      <div class="fly-panel">
        <h3 class="fly-panel-title">${user.username} 最近的提问</h3>
        <ul class="jie-row">
          <#list posts as post>
            <li>
              <#if post.recommend>
                <span class="fly-jing">精</span>
              </#if>
              <a href="/post/${post.id}" class="jie-title">${post.title}</a>
              <i>${timeAgo(post.created)}</i>
              <em class="layui-hide-xs">${post.viewCount}阅/${post.commentCount}答</em>
            </li>
          </#list>
          <#if !posts>
            <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><i style="font-size:14px;">没有发表任何求解</i></div>
          </#if>

        </ul>

      </div>
    </div>
    
    <div class="layui-col-md6 fly-home-da">
      <div class="fly-panel">
        <h3 class="fly-panel-title"><@shiro.principal property="username" /> 最近的回答</h3>
        <ul class="home-jieda">
            <#list commentInfo as info>
              <li>
                <p>
                  <span>${timeAgo(info.created)}</span>
                  在<a href="/post/${info.postId}" target="_blank">${info.title}</a>中${info.action}：
                </p>
                <div class="home-dacontent">
                  ${info.content}
                </div>
              </li>
            </#list>
          <#if !commentInfo>
            <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><span>没有回答任何问题</span></div>
          </#if>


        </ul>
      </div>
    </div>
  </div>
</div>

<script>
  layui.cache.page = 'user';
</script>
</@layout>